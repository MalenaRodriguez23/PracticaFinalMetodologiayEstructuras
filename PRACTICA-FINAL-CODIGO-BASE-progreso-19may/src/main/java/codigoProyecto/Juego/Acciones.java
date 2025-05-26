package codigoProyecto.Juego;

import codigoProyecto.AcciónInvalidaException;
import codigoProyecto.AtaqueInvalidoException;
import codigoProyecto.GrafoTablero.Grafo;
import codigoProyecto.GrafoTablero.Iterador;
import codigoProyecto.GrafoTablero.ListaEnlazada;
import codigoProyecto.GrafoTablero.Nodo;
import codigoProyecto.MovimientoInvalidoException;
import codigoProyecto.Personajes.*;
import codigoProyecto.Tablero.Casilla;

import codigoFX.javafx.conquista.TableroController;

import java.util.Random;


public class Acciones {
    static Jugador jugador;
    private static Log log = new Log();
    private static Nodo posicionActual;
    private static Grafo grafo;

    private static final ListaEnlazada<String> refuerzosLetras = new ListaEnlazada<>();
    private static final ListaEnlazada<String> refuerzosCiencias = new ListaEnlazada<>();
    private static int contadorTurnos = 0;

    public static void setGrafo(Grafo grafo){
        Acciones.grafo = grafo;
    }

    public static void setLog(Log log){
        Acciones.log = log;
    }

    public static void atacar(Facciones personaje, Nodo nodoObjetivo) {
        try {
            // Validaciones básicas
            if (personaje == null || nodoObjetivo == null) {
                throw new AtaqueInvalidoException("Objetivo o atacante inválido");
            }

            Facciones objetivo = nodoObjetivo.getPersonaje();
            if (objetivo == null) {
                throw new AtaqueInvalidoException("No hay personaje en la casilla objetivo");
            }
            // Verificar si son del mismo equipo (usando el jugador actual)
            Jugador jugadorAtacante = obtenerJugadorDueño(personaje);
            Jugador jugadorObjetivo = obtenerJugadorDueño(objetivo);

            if (jugadorAtacante != null && jugadorObjetivo != null &&
                    jugadorAtacante.equals(jugadorObjetivo)) {
                throw new AtaqueInvalidoException("No puedes atacar a tus aliados");
            }


            // Validar rango de ataque
            if (!estaEnRangoDeAtaque(personaje, nodoObjetivo)) {
                throw new AtaqueInvalidoException("El objetivo está fuera del rango de ataque");
            }

            // Cálculo de daño
            int dañoBase = Math.max(1, personaje.getAtaque());
            int defensaObjetivo = objetivo.getDefensa();
            int dañoFinal = Math.max(1, dañoBase - defensaObjetivo); // Mínimo 1 de daño
            int hpInicial = objetivo.getHp();
            int hpFinal = hpInicial - dañoFinal;

            // Registro del ataque
            String mensajeAtaque = String.format(
                    "%s [ATK:%d] ataca a %s [HP:%d DEF:%d] → Daño: %d (HP %d → %d)",
                    personaje.getNombre(),
                    personaje.getAtaque(),
                    objetivo.getNombre(),
                    hpInicial,
                    defensaObjetivo,
                    dañoFinal,
                    hpInicial,
                    hpFinal
            );

            // Mostrar en los sistemas de registro
            System.out.println(mensajeAtaque);
            log.registrar(jugador.getFaccion(), "Ataque", mensajeAtaque);
            TableroController.getInstance().agregarLog(mensajeAtaque);

            // Aplicar daño
            objetivo.setHp(hpFinal);

            // Manejo de muerte del personaje
            if (hpFinal <= 0) {
                String mensajeMuerte = String.format("%s ha sido derrotado!", objetivo.getNombre());
                System.out.println(mensajeMuerte);
                log.registrar(jugador.getFaccion(), "Personaje Derrotado", mensajeMuerte);
                TableroController.getInstance().agregarLog(mensajeMuerte);
                // Eliminar personaje del nodo
                nodoObjetivo.setPersonaje(null);

                // Eliminar de la lista del jugador
                if (jugadorObjetivo != null) {
                    jugadorObjetivo.getListaPersonajes().delete(objetivo);
                }

                // Verificar fin de juego
                TableroController.getInstance().verificarFinJuego();
                TableroController.getInstance().refrescarTablero();
            }

        } catch (AtaqueInvalidoException e) {
            String error = "Error en ataque: " + e.getMessage();
            TableroController.getInstance().agregarLog(error);
            System.err.println(error);
        }
    }



    public static void moverse(Nodo actual, Nodo nodoDestino) {
        try {
            if (actual == null || nodoDestino == null) {
                throw new MovimientoInvalidoException("Nodos nulos");
            }
            if (actual.getPersonaje().getRangoMovimiento() + nodoDestino.getCosto() < 0){
                throw new MovimientoInvalidoException("El costo de la casilla es demasiado alto para tu rango de movimiento");
            }

            Facciones personaje = actual.getPersonaje();
            if (personaje == null) {
                throw new MovimientoInvalidoException("No hay personaje en el nodo origen");
            }

            if (nodoDestino.tienePersonaje()) {
                throw new MovimientoInvalidoException("Casilla destino ocupada");
            }

            // Validar rango de movimiento
            if (!estaEnRangoDeMovimiento(actual, nodoDestino)) {
                throw new MovimientoInvalidoException("El destino está fuera del rango de movimiento");
            }

            // Realizar el movimiento
            nodoDestino.setPersonaje(personaje);
            actual.setPersonaje(null);
            personaje.setPosicion(new Casilla(nodoDestino.getFila(), nodoDestino.getColumna()));
            TableroController.getInstance().verificarFinJuego();

        } catch (MovimientoInvalidoException e) {
            System.out.println("Error al moverse: " + e.getMessage());
            TableroController.getInstance().agregarLog("Error de movimiento: " + e.getMessage());
        }
    }

    public static boolean estaEnRangoDeMovimiento(Nodo actual, Nodo destino) {
        if (actual == null || destino == null || actual.getPersonaje() == null) return false;
        int distancia = actual.calcularDistancia(destino);
        return distancia <= actual.getPersonaje().getRangoMovimiento();
    }

    public static boolean estaEnRangoDeAtaque(Facciones personaje, Nodo destino) {
        if (posicionActual == null || destino == null || personaje == null) return false;
        int distancia = posicionActual.calcularDistancia(destino);
        return distancia <= personaje.getRangoAtaque();
    }

    public static Jugador getJugador() {
        return jugador;
    }

    public static boolean estaVivo(Facciones personaje) {
        return personaje != null && personaje.getHp() > 0;
    }
    public static void activarHabilidadEspecialIndividual(Facciones personaje) {
        try {
            String mensaje = "";
            if (personaje.getNombre().toLowerCase().contains("matemático")) {
                personaje.setDefensa(personaje.getDefensa() + 10);
                mensaje = "El matemático ha activado un escudo de grafos";
            }
            if (personaje.getNombre().toLowerCase().contains("físico")) {
                personaje.setRangoAtaque(personaje.getRangoAtaque() + 2);
                mensaje ="El físico desactivó la gravedad para él aumentando su rango de ataque";
            }
            if (personaje.getNombre().toLowerCase().contains("químico")) {
                personaje.setHp(personaje.getHp() + 30);
                mensaje = "El químico se curó con un ácido naftalenacético";
            }
            if (personaje.getNombre().toLowerCase().contains("ingeniero")) {
                personaje.setRangoMovimiento(personaje.getRangoMovimiento() + 2);
                mensaje = "El ingeniero aumento su rango de movimiento al crear un coche eléctrico";
            }
            if (personaje.getNombre().toLowerCase().contains("historiador")) {
                personaje.setRangoMovimiento(personaje.getRangoMovimiento() + 2);
                mensaje = "El historiador aumentó su rango de movimiento gracias a una máquina del tiempo";
            }
            if (personaje.getNombre().toLowerCase().contains("poeta")) {
                int curacion = (int) (personaje.getHp() * 0.5);
                personaje.setHp(personaje.getHp() + curacion);
                mensaje ="El poeta se curó recitando la Rima IV de Gustavo Adolfo Becquer";
            }
            if (personaje.getNombre().toLowerCase().contains("filólogo")) {
                personaje.setDefensa(personaje.getDefensa() * 2);
                mensaje = "El filólogo creó un escudo de sintaxis";
            }
            if (personaje.getNombre().toLowerCase().contains("filósofo")) {
                personaje.setAtaque(personaje.getAtaque() + 15);
                mensaje = "El filósofo lanzó un ataque ético sobre la alegoría de la caverna de Platón aumentando su ataque";
            }
            if (!mensaje.isEmpty()) {
                System.out.println(mensaje);
                log.registrar(jugador.getFaccion(), "Habilidad Especial", mensaje);
                TableroController.getInstance().agregarLog(mensaje);
            }
        } catch (AcciónInvalidaException e){
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void usarHabilidadEspecialIndividual(Facciones personaje) {
        try {
            if (personaje == null) return;

            int hpAntes = personaje.getHp();
            int ataqueAntes = personaje.getAtaque();
            int defensaAntes = personaje.getDefensa();
            int rangoAntes = personaje.getRangoAtaque();

            activarHabilidadEspecialIndividual(personaje);

            // Registrar cambios
            String mensaje = String.format(
                    "%s usó habilidad especial:\nHP: %d→%d\nATK: %d→%d\nDEF: %d→%d\nRango: %d→%d",
                    personaje.getNombre(),
                    hpAntes, personaje.getHp(),
                    ataqueAntes, personaje.getAtaque(),
                    defensaAntes, personaje.getDefensa(),
                    rangoAntes, personaje.getRangoAtaque()
            );

            log.registrar(jugador.getFaccion(), "Habilidad Especial", mensaje);
            TableroController.getInstance().agregarLog(mensaje);

        } catch (Exception e) {
            System.err.println("Error al usar habilidad: " + e.getMessage());
        }
    }

    public static void usarHabilidadGeneral(Jugador jugador) {
        if (jugador == null) return;

        try {
            String faccion = jugador.getFaccion().toLowerCase();
            String mensaje = "";

            if (faccion.contains("ciencias")) {
                Iterador<Facciones> it = jugador.getListaPersonajes().getIterador();
                while (it.hasNext()) {
                    Facciones p = it.next();
                    p.setHp(p.getHp() + 20);
                }
                mensaje = "¡Habilidad general de Ciencias activada! Todos los personajes recuperaron 20 HP.";

            } else if (faccion.contains("letras")) {
                Iterador<Facciones> it = jugador.getListaPersonajes().getIterador();
                while (it.hasNext()) {
                    Facciones p = it.next();
                    p.setAtaque(p.getAtaque() + 10);
                }
                mensaje = "¡Habilidad general de Letras activada! Todos los personajes ganaron +10 ATK.";
            }

            jugador.setHabilidadGeneralUsada(true);
            log.registrar(jugador.getFaccion(), "Habilidad General", mensaje);
            TableroController.getInstance().agregarLog(mensaje);

        } catch (Exception e) {
            System.err.println("Error en habilidad general: " + e.getMessage());
        }
    }
    private static Jugador obtenerJugadorDueño(Facciones personaje) {
        if (personaje == null) return null;

        // Primero verificar si pertenece al jugador humano
        if (jugador != null && jugador.getListaPersonajes().isInList(personaje)) {
            return jugador;
        }

        // Luego verificar si pertenece a la IA
        Jugador jugadorIA = TableroController.getInstance().getJugadorIA();
        if (jugadorIA != null && jugadorIA.getListaPersonajes().isInList(personaje)) {
            return jugadorIA;
        }

        return null;
    }

    public static void setJugador(Jugador jug) {
        jugador = jug;
    }

    public static void setPosicion(Nodo posicion) {
        Acciones.posicionActual = posicion;
    }

    public static void manejarTurnos(){
        contadorTurnos++;

        if (contadorTurnos % 6 == 0){
            Facciones nuevoPersonajeJugador = generarNuevoPersonaje(jugador);
            boolean colocadoJugador = colocarPersonajeAdyacente(nuevoPersonajeJugador);
            if (colocadoJugador){
                System.out.println("¡Refuerzos han llegado!");
            }
        } else if (contadorTurnos % 10 == 0){
            Facciones nuevoPersonajeIA = generarNuevoPersonajeIA();
            boolean colocadoIA = colocarPersonajeAdyacenteIA(nuevoPersonajeIA);
            if (colocadoIA){
                System.out.println("¡Refuerzos han llegado!");
            }
        }
    }

    private static Facciones generarNuevoPersonaje(Jugador jugador){
        if (jugador.getFaccion().equalsIgnoreCase("ciencias")){
            return generarRefuerzoCiencias();
        } else {
            return generarRefuerzoLetras();
        }
    }

    private static Facciones generarNuevoPersonajeIA(){
        if (new Random().nextBoolean()){
            return generarRefuerzoCiencias();
        } else {
            return generarRefuerzoLetras();
        }
    }

    private static Facciones generarRefuerzoCiencias(){
        if (refuerzosCiencias.getNumElementos() == 0){
            if (new Random().nextBoolean()){
                refuerzosCiencias.add("Físico");
                refuerzosCiencias.add("Químico");
            } else {
                refuerzosCiencias.add("Químico");
                refuerzosCiencias.add("Físico");
            }
        }
        String refuerzo = refuerzosCiencias.getPrimero();
        refuerzosCiencias.deletePrimero();
        if (refuerzo.equals("Físico")){
            return new Físico();
        } else {
            return new Químico();
        }
    }

    private static Facciones generarRefuerzoLetras(){
        if (refuerzosLetras.getNumElementos() == 0){
            if (new Random().nextBoolean()){
                refuerzosLetras.add("Poeta");
                refuerzosLetras.add("Historiador");
            } else {
                refuerzosLetras.add("Historiador");
                refuerzosLetras.add("Poeta");
            }
        }
        String refuerzo = refuerzosLetras.getPrimero();
        refuerzosLetras.deletePrimero();
        if (refuerzo.equals("Poeta")){
            return new Poeta();
        } else {
            return new Historiador();
        }
    }

    public static boolean colocarPersonajeAdyacente(Facciones nuevoPersonaje){
        if (grafo == null || jugador == null){
            System.out.println("Error: El grafo o el jugador no están incializados");
            return false;
        }

        Iterador<Facciones> it = jugador.getListaPersonajes().getIterador();
        while (it.hasNext()) {
            Facciones personajeExistente = it.next();
            Casilla pos = personajeExistente.getPosicion();
            if (pos == null) continue;

            int fila = pos.getX();
            int columna = pos.getY();

            int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : direcciones){
                int nuevaFila = fila + dir[0];
                int nuevaColumna = columna + dir[1];

                Nodo nodoAdyacente = grafo.obtenerNodo(nuevaFila, nuevaColumna);

                if (nodoAdyacente != null && !nodoAdyacente.tienePersonaje()){
                    nodoAdyacente.setPersonaje(nuevoPersonaje);
                    nuevoPersonaje.setPosicion(new Casilla(nuevaFila, nuevaColumna));
                    jugador.getListaPersonajes().add(nuevoPersonaje);

                    System.out.println("Nuevo personaje" + nuevoPersonaje.getNombre() + "colocado en (" + nuevaFila + "," + nuevaColumna + ")");
                    return true;
                }
            }
        }
        System.out.println("No se encontró una casilla adyacente disponibe");
        return false;
    }

    public static boolean colocarPersonajeAdyacenteIA(Facciones nuevoPersonaje){
        return colocarPersonajeAdyacente(nuevoPersonaje);
    }

    public static Log getLog(){
        return log;
    }

}
