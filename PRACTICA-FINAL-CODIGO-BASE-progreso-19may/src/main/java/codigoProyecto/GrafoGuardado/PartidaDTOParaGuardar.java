package codigoProyecto.GrafoGuardado;

import codigoProyecto.GrafoTablero.Iterador;
import codigoProyecto.GrafoTablero.ListaEnlazada;
import codigoProyecto.GrafoTablero.Nodo;
import codigoProyecto.Juego.Log;
import codigoProyecto.Personajes.Facciones;

public class PartidaDTOParaGuardar {
    private ListaEnlazada<NodoDTO> nodos;
    private String personajeJugadorHumano;
    public String[] logAcciones;
    private String nombreJugador;
    private ListaEnlazada<Facciones> personajesJugador;

    private ListaEnlazada<Nodo> tableroReconstruido;

    public PartidaDTOParaGuardar() {
        nodos = new ListaEnlazada<>();
        logAcciones = new String[0];
    }

    public ListaEnlazada<NodoDTO> getNodos() {
        return nodos;
    }

    public void setNodos(ListaEnlazada<NodoDTO> nodos) {
        this.nodos = nodos;
    }

    public String getPersonajeJugadorHumano() {
        return personajeJugadorHumano;
    }

    public void setPersonajeJugadorHumano(String personajeJugadorHumano) {
        this.personajeJugadorHumano = personajeJugadorHumano;
    }

    public String[] getLogAcciones() {
        return logAcciones;
    }

    public void setLogAcciones(Log logAcciones) {
        this.logAcciones = logAcciones.obtenerAccionesComoArray();
    }

    public ListaEnlazada<Nodo> getTableroReconstruido() {
        return tableroReconstruido;
    }

    public Log reconstruirTableroYDevolverLog(PartidaDTOParaGuardar dto) {
        tableroReconstruido = new ListaEnlazada<>();

        // Primera pasada: crear nodos base
        Iterador<NodoDTO> iterador = dto.getNodos().getIterador();
        while (iterador.hasNext()) {
            NodoDTO dtoNodo = iterador.next();
            Nodo nodo = new Nodo(dtoNodo.fila, dtoNodo.columna);
            nodo.setCosto(dtoNodo.costo);

            if (dtoNodo.personaje != null) {
                nodo.setPersonaje(dtoNodo.personaje);
            }

            if (dtoNodo.terreno != null) {
                nodo.setTerreno(dtoNodo.terreno);
            }

            tableroReconstruido.add(nodo);
        }

        // Segunda pasada: agregar adyacencias a partir de int[] (fila, columna)
        for (int i = 0; i < tableroReconstruido.getNumElementos(); i++) {
            Nodo nodo = tableroReconstruido.obtener(i);
            NodoDTO dtoNodo = dto.getNodos().obtener(i);

            Iterador<int[]> itAdyacentes = dtoNodo.adyacentes.getIterador();
            while (itAdyacentes.hasNext()) {
                int[] coords = itAdyacentes.next();
                int fila = coords[0];
                int columna = coords[1];

                // Buscar nodo por fila y columna
                for (int j = 0; j < tableroReconstruido.getNumElementos(); j++) {
                    Nodo candidato = tableroReconstruido.obtener(j);
                    if (candidato.getFila() == fila && candidato.getColumna() == columna) {
                        nodo.agregarAdyacente(candidato);
                        break;
                    }
                }
            }
        }

        // Reconstrucción del log
        Log log = new Log();
        for (String accion : dto.getLogAcciones()) {
            log.agregarLineaDesdeString(accion);
        }
        return log;
    }

    public String getNombreJugador(){
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador){
        this.nombreJugador = nombreJugador;
    }

    public void setPersonajesJugador(ListaEnlazada<Facciones> personajesJugador){
        this.personajesJugador = personajesJugador;
    }

    public ListaEnlazada<Facciones> getPersonajesJugador(){
        return personajesJugador;
    }

}





