package codigoFX.javafx.conquista;

import codigoProyecto.GrafoGuardado.*;
import codigoProyecto.GrafoTablero.*;
import codigoProyecto.Juego.*;
import codigoProyecto.Personajes.*;
import codigoProyecto.Tablero.TerrenoAgua;
import codigoProyecto.Tablero.TerrenoFuego;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class TableroController {

    @FXML
    private VBox configuracionPanel, menuOpcionesVBox;

    @FXML
    private Grafo grafo;

    private ParameterDataModel jugador;

    @FXML
    private TextField filasInput;

    @FXML
    private TextField columnasInput;

    @FXML
    private GridPane gridPane;

    private Nodo nodoSeleccionado;
    private boolean turnoJugador = true;
    private Jugador jugadorIA;
    private Jugador jugadorActual;
    private boolean juegoTerminado =false;
    ListaEnlazada<Nodo> nodos = new ListaEnlazada<Nodo>();

    private ParameterDataModelProperties modeloParaGUICompartido;
    private boolean partidaCargada = false;


    @FXML
    public void initialize() {
        if (!partidaCargada){
            configuracionPanel.setVisible(true);
            configuracionPanel.setManaged(true);

            gridPane.setVisible(false);
            gridPane.setManaged(false);
        } else {
            configuracionPanel.setVisible(false);
            configuracionPanel.setManaged(false);

            gridPane.setVisible(true);
            gridPane.setManaged(true);
        }
    }

    @FXML
    public void crearTablero() {
        if (filasInput.getText().isEmpty() || columnasInput.getText().isEmpty()) {
            mostrarAlerta("Error", "Debes ingresar el número de filas y columnas.");
            return;
        }
        if (partidaCargada){
            return;
        }

        try {
            int filas = Integer.parseInt(filasInput.getText());
            int columnas = Integer.parseInt(columnasInput.getText());

            if (filas <= 0 || columnas <= 0) {
                mostrarAlerta("Error", "Las filas y columnas deben ser mayores que cero.");
                return;
            } else if (filas > 20 || columnas > 20){
                mostrarAlerta("Error", "Las filas y las columnas no pueden ser mayores que 20");
            }
            grafo = new Grafo();
            gridPane.getChildren().clear();
            crearTableroConGrafo(filas, columnas);

            configuracionPanel.setVisible(false);
            configuracionPanel.setManaged(false);

            gridPane.setVisible(true);
            gridPane.setManaged(true);



        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Debes ingresar valores numéricos válidos.");
        }
    }

    private void crearTableroConGrafo(int filas, int columnas) {
        JugadorLetras jugadorHumanoLetras = new JugadorLetras(jugador.getNombre());
        JugadorCiencias jugadorIACiencias = new JugadorCiencias("IA");
        JugadorCiencias jugadorHumanoCiencias = new JugadorCiencias(jugador.getNombre());
        JugadorLetras jugadorIALetras = new JugadorLetras("IA");

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                Nodo nodo = grafo.agregarNodo(fila, columna);
                Button boton = new Button(nodo.toString());
                boton.setPrefSize(60, 60);
                nodos.add(nodo);

                Facciones personaje = null;
                //Colocar personajes iniciales
                if (jugador.getPersonaje().equalsIgnoreCase("letras")) {
                    if (fila == filas - 1 && columna == 0) {
                        personaje = getPersonajeEnPosicion(jugadorHumanoLetras.getListaPersonajes(), 0);
                        jugadorIA = jugadorIACiencias;
                    } else if (fila == filas - 1 && columna == columnas - 1) {
                        personaje = getPersonajeEnPosicion(jugadorHumanoLetras.getListaPersonajes(), 1);
                        jugadorIA= jugadorIACiencias;
                    } else if (fila == 0 && columna == 0) {
                        personaje = getPersonajeEnPosicion(jugadorIACiencias.getListaPersonajes(), 2);
                        jugadorIA = jugadorIACiencias;
                    } else if (fila == 0 && columna == columnas - 1) {
                        personaje = getPersonajeEnPosicion(jugadorIACiencias.getListaPersonajes(), 3);
                        jugadorIA = jugadorIACiencias;
                    }
                } else if (jugador.getPersonaje().equalsIgnoreCase("ciencias")) {
                    if (fila == filas - 1 && columna == 0) {
                        personaje = getPersonajeEnPosicion(jugadorHumanoCiencias.getListaPersonajes(), 0);
                        jugadorIA = jugadorIALetras;
                    } else if (fila == filas - 1 && columna == columnas - 1) {
                        personaje = getPersonajeEnPosicion(jugadorHumanoCiencias.getListaPersonajes(), 1);
                        jugadorIA = jugadorIALetras;
                    } else if (fila == 0 && columna == 0) {
                        personaje = getPersonajeEnPosicion(jugadorIALetras.getListaPersonajes(), 2);
                        jugadorIA = jugadorIALetras;
                    } else if (fila == 0 && columna == columnas - 1) {
                        personaje = getPersonajeEnPosicion(jugadorIALetras.getListaPersonajes(), 3);
                        jugadorIA = jugadorIALetras;
                    }
                }
                //Asignar la imagen de los personaje
                if (personaje != null) {
                    nodo.setPersonaje(personaje);
                    System.out.println("Asignado personaje: " + personaje.getClass().getSimpleName() +
                            " en (" + fila + "," + columna + ")");

                    ImageView imagen = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(personaje.getRutaImagen()))));
                    imagen.setFitWidth(40);
                    imagen.setFitHeight(40);
                    boton.setGraphic(imagen);
                }

                boton.setOnAction(event -> seleccionarCasilla(nodo, boton));
                gridPane.add(boton, columna, fila);

            }
        }

        asignarTerrenoAleatorio(nodos, 20,10);
        System.out.println("Terrenos asignados");


        Iterador<Nodo> iterador = nodos.getIterador();
        while (iterador.hasNext()){
            Nodo nodo = iterador.next();

            if (nodo.getTerreno() != null && nodo.getPersonaje() == null) {
                Button boton = getButtonFromGridPane(gridPane, nodo.getColumna(), nodo.getFila());
                if (boton != null) {
                    try {
                        ImageView imagen = nodo.getTerreno().getImagen();
                        imagen.setFitWidth(40);
                        imagen.setFitHeight(40);
                        boton.setGraphic(imagen);
                    } catch (Exception e) {
                        System.out.println("No se pudo cargar la imagen de la cualidad: " + nodo.getTerreno().getRutaImagen());
                    }
                }
            }
        }

        if (jugador.getPersonaje().toLowerCase().equals("letras")){
            Acciones.setJugador(jugadorHumanoLetras);
            jugadorIA = jugadorIACiencias;
        } else if (jugador.getPersonaje().toLowerCase().equals("ciencias")){
            Acciones.setJugador(jugadorHumanoCiencias);
            jugadorIA = jugadorIALetras;
        }

        conectarNodosGrafo(filas, columnas);
        Acciones.setGrafo(grafo);
        if (jugador.getPersonaje().equalsIgnoreCase("ciencias")){
            Acciones.setJugador(jugadorHumanoCiencias);
        } else {
            Acciones.setJugador(jugadorHumanoLetras);
        }

    }

    private Facciones getPersonajeEnPosicion(Lista<Facciones> lista, int posicion) {
        Iterador<Facciones> iter = lista.getIterador();
        int contador = 0;
        while (iter.hasNext()) {
            Facciones p = iter.next();
            if (contador == posicion) {
                return p;
            }
            contador++;
        }
        return null;
    }

    private void asignarTerrenoAleatorio(ListaEnlazada<Nodo> nodos, int porcentajeAgua, int porcentajeFuego){
        int total = nodos.getNumElementos();

        ListaEnlazada<Nodo> nodosSinTerreno = new ListaEnlazada<>();
        Iterador<Nodo> iteradorNodosSinTerreno = nodosSinTerreno.getIterador();
        while (iteradorNodosSinTerreno.hasNext()){
            Nodo nodo = iteradorNodosSinTerreno.next();
            if (nodo.getTerreno() == null){
                nodosSinTerreno.add(nodo);
            }
        }

        int disponibles = nodosSinTerreno.getNumElementos();
        int cantidadNodosDeFuego = (total * porcentajeFuego) / 100;
        int cantidadNodosDeAgua = (total * porcentajeAgua) / 100;

        if (cantidadNodosDeAgua + cantidadNodosDeFuego > disponibles){
            System.out.println("Error: la cantidad de casillas libres es más pequeña que la cantidad de terrenos a implementar");
        }
        Nodo[] arreglo = new Nodo[total];
        Iterador<Nodo> iterador = nodos.getIterador();
        int i = 0;
        while(iterador.hasNext()){
            arreglo[i++] = iterador.next();
        }

        for (int j = total - 1; j > 0; j --){
            int k = (int) (Math.random() * (j + 1));
            Nodo temp = arreglo[j];
            arreglo[j] = arreglo[k];
            arreglo[k] = temp;
        }

        for (i = 0; i < cantidadNodosDeAgua; i++) {
            arreglo[i].setTerreno(new TerrenoAgua());

        } System.out.println("Terrenos de agua colocados");
        for (i = cantidadNodosDeAgua; i < cantidadNodosDeFuego + cantidadNodosDeAgua; i++){
            arreglo[i].setTerreno(new TerrenoFuego());
        } System.out.println("Terrenos de fuego colocados");
    }


    private static TableroController instance;

    public TableroController() {
        instance = this;
    }

    public static TableroController getInstance() {
        return instance;
    }

    @FXML
    private TextArea logTextArea;
    public void agregarLog(String mensaje) {
        Platform.runLater(() -> {
            if (logTextArea != null) {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                logTextArea.appendText("[" + timestamp + "] " + mensaje + "\n");

                logTextArea.setScrollTop(Double.MAX_VALUE);
            }
        });
    }


    private void seleccionarCasilla(Nodo nodo, Button boton) {
        if (juegoTerminado) return;
        if (!turnoJugador) {
            mostrarAlerta("Turno", "Es el turno del oponente");
            return;
        }

        if (nodo != null && nodo.tienePersonaje() && nodo.getPersonaje().getHp()>0) {
            System.out.println("JugadorActual: " + Acciones.getJugador());
            if (Acciones.getJugador().getListaPersonajes().isInList(nodo.getPersonaje())) {
                this.nodoSeleccionado = nodo;
                Acciones.setPosicion(nodo); // Establece la posición actual
                mostrarMenuOpciones(boton);
            } else {
                mostrarAlerta("Error", "Este personaje no es tuyo");
            }
        } else {
            this.nodoSeleccionado = null;
            ocultarMenuOpciones();
        }
    }



    private void mostrarMensajeTurno() {
        String mensaje = turnoJugador ? "Tu turno" : "Turno del oponente";
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Turno");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.show();
        });
    }

    private void realizarTurnoIA() {
        if (juegoTerminado) return;
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            try {
                if (juegoTerminado|| verificarFinJuego()){
                    return;
                }
                // 1. Verificar si puede usar habilidad general (20% de probabilidad)
                if (!jugadorIA.isHabilidadGeneralUsada() && new Random().nextInt(5) == 0) {
                    Acciones.usarHabilidadGeneral(jugadorIA);
                    jugadorIA.setHabilidadGeneralUsada(true);
                    refrescarTablero();
                    cambiarTurno();
                    return; // Terminar el turno después de una acción
                }

                // 2. Obtener todos los personajes de IA vivos
                ListaEnlazada<Nodo> personajesIA = new ListaEnlazada<>();
                int filas = Integer.parseInt(filasInput.getText());
                int columnas = Integer.parseInt(columnasInput.getText());

                for (int fila = 0; fila < filas; fila++) {
                    for (int col = 0; col < columnas; col++) {
                        Nodo nodo = grafo.obtenerNodo(fila, col);
                        if (nodo != null && nodo.tienePersonaje() &&
                                !Acciones.getJugador().getListaPersonajes().isInList(nodo.getPersonaje())) {
                            personajesIA.add(nodo);
                        }
                    }
                }

                // 3. Seleccionar un personaje aleatorio para actuar
                if (personajesIA.getNumElementos() > 0) {
                    Nodo nodoIA = personajesIA.obtener(new Random().nextInt(personajesIA.getNumElementos()));
                    Facciones personajeIA = nodoIA.getPersonaje();
                    boolean accionRealizada = false;

                    // 4. Decidir si usar habilidad especial (30% de probabilidad)
                    if (!personajeIA.isHabilidadEspecialUsada() && new Random().nextInt(3) == 0) {
                        Acciones.setPosicion(nodoIA);
                        Acciones.usarHabilidadEspecialIndividual(personajeIA);
                        personajeIA.setHabilidadEspecialUsada(true);
                        accionRealizada = true;
                    }

                    // 5. Si no usó habilidad, decidir acción (50% atacar si es posible, sino mover)
                    if (!accionRealizada) {
                        if (new Random().nextBoolean()) {
                            // Intentar atacar
                            Iterador<Nodo> iterAdy = nodoIA.getAdyacentes().getIterador();
                            while (iterAdy.hasNext() && !accionRealizada) {
                                Nodo adyacente = iterAdy.next();
                                if (adyacente.tienePersonaje() &&
                                        Acciones.getJugador().getListaPersonajes().isInList(adyacente.getPersonaje())) {
                                    Acciones.setPosicion(nodoIA);
                                    Acciones.atacar(personajeIA, adyacente);
                                    accionRealizada = true;
                                }
                            }
                        }

                        // 6. Si no atacó, intentar moverse
                        if (!accionRealizada) {
                            ListaEnlazada<Nodo> movimientosPosibles = new ListaEnlazada<>();
                            Iterador<Nodo> iterMov = nodoIA.getAdyacentes().getIterador();

                            while (iterMov.hasNext()) {
                                Nodo adyacente = iterMov.next();
                                if (!adyacente.tienePersonaje()) {
                                    movimientosPosibles.add(adyacente);
                                }
                            }

                            if (movimientosPosibles.getNumElementos() > 0) {
                                Nodo destino = movimientosPosibles.obtener(
                                        new Random().nextInt(movimientosPosibles.getNumElementos()));
                                Acciones.moverse(nodoIA, destino);
                                accionRealizada = true;
                            }
                        }
                    }

                    if (accionRealizada) {
                        refrescarTablero();
                    }
                }

                cambiarTurno();

            } catch (Exception e) {
                System.err.println("Error en turno IA: " + e.getMessage());
                cambiarTurno();
            }
        });
        pause.play();
    }


    private ListaEnlazada<Facciones> obtenerPersonajesIA() {
        ListaEnlazada<Facciones> personajesIA = new ListaEnlazada<>();
        Iterador<Facciones> iter = jugadorIA.getListaPersonajes().getIterador();

        while (iter.hasNext()) {
            Facciones personaje = iter.next();
            if (personaje.getHp() > 0) { // Solo personajes vivos
                personajesIA.add(personaje);
            }
        }
        return personajesIA;
    }


    private void conectarNodosGrafo(int filas, int columnas) {
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                Nodo nodoActual = grafo.obtenerNodo(fila, columna);

                // Conectar a los nodos vecinos (arriba, abajo, izquierda, derecha)
                if (fila > 0) grafo.conectarNodos(nodoActual, grafo.obtenerNodo(fila - 1, columna)); // Arriba
                if (fila < filas - 1) grafo.conectarNodos(nodoActual, grafo.obtenerNodo(fila + 1, columna)); // Abajo
                if (columna > 0) grafo.conectarNodos(nodoActual, grafo.obtenerNodo(fila, columna - 1)); // Izquierda
                if (columna < columnas - 1) grafo.conectarNodos(nodoActual, grafo.obtenerNodo(fila, columna + 1)); // Derecha
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setJugador(ParameterDataModel jugador){
        this.jugador = jugador;
        Acciones.setJugador(convertirJugadorAJugador(jugador));
    }

    private void mostrarMenuOpciones(Button boton) {
        menuOpcionesVBox.setVisible(true);
        menuOpcionesVBox.setManaged(true);

        menuOpcionesVBox.setLayoutX(boton.getLayoutX() + boton.getWidth() + 10);
        menuOpcionesVBox.setLayoutY(boton.getLayoutY());
    }

    private void ocultarMenuOpciones() {
        menuOpcionesVBox.setVisible(false);
        menuOpcionesVBox.setManaged(false);
        nodoSeleccionado = null;
    }


    @FXML
    public void moverPersonajeAccion(){
        if (nodoSeleccionado != null && turnoJugador){
            try {
                Nodo destino = seleccionarDestino();
                if (destino == null) {
                    return; // No se seleccionó destino válido
                }

                // Validar movimiento
                if (!Acciones.estaEnRangoDeMovimiento(nodoSeleccionado, destino)) {
                    mostrarAlerta("Error", "El destino está fuera del rango de movimiento");
                    return;
                }

                if (destino.tienePersonaje()) {
                    mostrarAlerta("Error", "La casilla destino está ocupada");
                    return;
                }

                Acciones.moverse(nodoSeleccionado, destino);
                if (destino.getTerreno() != null){
                    destino.getPersonaje().setDefensa(destino.getPersonaje().getDefensa() + destino.getTerreno().getModificadorDefensa());
                    System.out.println("El terreno del tablero ha modificado la defensa del personaje");
                }
                destino.getPersonaje().setRangoMovimiento(destino.getPersonaje().getRangoMovimiento() + destino.getCosto());
                System.out.println("El rango de movimiento del personaje ha sido modificado por el costo del nodo. Nuevo rango" + destino.getPersonaje().getRangoMovimiento());
                refrescarTablero();
                cambiarTurno();
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al mover el personaje: " + e.getMessage());
            }
        }
        ocultarMenuOpciones();
    }

    @FXML
    public void atacarPersonajeAccion() {
        if (nodoSeleccionado != null && turnoJugador) {
            Nodo nodoDestino = seleccionarDestino();

            if (!nodoDestino.tienePersonaje()) {
                mostrarAlerta("Error", "No hay personaje en la casilla objetivo");
                return;
            }

            if (Acciones.getJugador().getListaPersonajes().isInList(nodoDestino.getPersonaje())) {
                mostrarAlerta("Error", "No puedes atacar a tus aliados");
                return;
            }

            if (!Acciones.estaEnRangoDeAtaque(nodoSeleccionado.getPersonaje(), nodoDestino)) {
                mostrarAlerta("Error", "El objetivo está fuera del rango de ataque");
                return;
            }

            Acciones.atacar(nodoSeleccionado.getPersonaje(), nodoDestino);
            refrescarTablero();
            cambiarTurno();
        }
        ocultarMenuOpciones();
    }


    @FXML
    public void habilidadEspecialPersonaje() {
        if (nodoSeleccionado != null && turnoJugador){
            Facciones personaje = nodoSeleccionado.getPersonaje();

            if (personaje == null) {
                mostrarAlerta("Error", "No hay personaje en esta casilla");
                return;
            }

            if (personaje.isHabilidadEspecialUsada()){
                mostrarAlerta("Habilidad ya usada", "Este personaje ya usó la habilidad especial");
                return;
            }

            // Establecer posición actual antes de usar habilidad
            Acciones.setPosicion(nodoSeleccionado);
            Acciones.usarHabilidadEspecialIndividual(personaje);
            personaje.setHabilidadEspecialUsada(true);
            refrescarTablero();
            cambiarTurno();
        }
        ocultarMenuOpciones();
    }

    @FXML
    public void habilidadGeneralPersonaje() {
        if (nodoSeleccionado != null && turnoJugador) {
            Facciones personaje = nodoSeleccionado.getPersonaje();

            if (personaje == null){
                mostrarAlerta("Error", "Esta casilla no tiene personaje");
                return;
            }

            if (Acciones.getJugador().isHabilidadGeneralUsada()) {
                mostrarAlerta("Habilidad ya usada", "Ya has usado la habilidad general en este juego");
                return;
            }

            Acciones.usarHabilidadGeneral(Acciones.getJugador());
            Acciones.getJugador().setHabilidadGeneralUsada(true);
            refrescarTablero();
            cambiarTurno();
        }
        ocultarMenuOpciones();
    }

    private Nodo seleccionarDestino(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Seleccionar destino");
        dialog.setHeaderText("Introduce las coordenadas de destino (fila,columna):");
        dialog.setContentText("Formato: fila,columna");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                String[] coordenadas = result.get().split(",");
                int fila = Integer.parseInt(coordenadas[0].trim());
                int columna = Integer.parseInt(coordenadas[1].trim());
                return grafo.obtenerNodo(fila, columna); // Obtener el nodo existente en lugar de crear uno nuevo
            } catch (Exception e) {
                mostrarAlerta("Error", "Las coordenadas no son válidas.");
            }
        }
        return null;
    }

    public Jugador convertirJugadorAJugador(ParameterDataModel jugador){
        return new Jugador(jugador.getNombre(), jugador.getPersonaje());
    }

    public void refrescarTablero() {
        int filas = Integer.parseInt(filasInput.getText());
        int columnas = Integer.parseInt(columnasInput.getText());

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                Nodo nodo = grafo.obtenerNodo(fila, columna);
                if (nodo == null) continue;

                Button boton = getButtonFromGridPane(gridPane, columna, fila);
                if (boton == null) continue;

                boton.setStyle("");
                boton.getStyleClass().removeAll("casilla-ataque", "casilla-movimiento");

                if (nodo.tienePersonaje()) {
                    Facciones personaje = nodo.getPersonaje();

                    StackPane stackPane = new StackPane();

                    ImageView imagen = new ImageView(new Image(
                            Objects.requireNonNull(getClass().getResourceAsStream(personaje.getRutaImagen()))));
                    imagen.setFitWidth(40);
                    imagen.setFitHeight(40);

                    Text textoHP = new Text(String.valueOf(personaje.getHp()));
                    textoHP.setFill(Color.BLACK);
                    textoHP.setStroke(Color.BLACK);
                    textoHP.setStrokeWidth(1);
                    textoHP.setFont(Font.font("Arial", FontWeight.BOLD, 12));

                    stackPane.getChildren().addAll(imagen, textoHP);
                    StackPane.setAlignment(textoHP, Pos.BOTTOM_CENTER);

                    Tooltip tooltip = new Tooltip();
                    tooltip.setText(String.format(
                            "%s\nHP: %d\nATK: %d\nDEF: %d\nMov: %d\nRango: %d",
                            personaje.getNombre(),
                            personaje.getHp(),
                            personaje.getAtaque(),
                            personaje.getDefensa(),
                            personaje.getRangoMovimiento(),
                            personaje.getRangoAtaque()
                    ));
                    Tooltip.install(boton, tooltip);

                    if (Acciones.getJugador().getListaPersonajes().isInList(personaje)) {
                        boton.getStyleClass().add("casilla-aliado");
                    } else {
                        boton.getStyleClass().add("casilla-enemigo");
                    }

                    boton.setGraphic(stackPane);
                } else {
                    boton.getStyleClass().add("casilla-vacia");

                    Tooltip.uninstall(boton, boton.getTooltip());

                    if (nodo.getTerreno() != null && nodo.getTerreno().getImagen() != null) {
                        try {
                            ImageView imagenTerreno = nodo.getTerreno().getImagen();
                            imagenTerreno.setFitWidth(40);
                            imagenTerreno.setFitHeight(40);
                            boton.setGraphic(imagenTerreno);
                        } catch (Exception e) {
                            System.out.println("Error cargando imagen de cualidad: " + nodo.getTerreno());
                            boton.setGraphic(null);
                        }
                    } else {
                        boton.setGraphic(null);
                    }
                }
            }
        }
    }

    private Button getButtonFromGridPane(GridPane gridPane, int col, int row) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) != null) {
                if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                    if (node instanceof Button) {
                        return (Button) node;
                    }
                }
            }
        }
        return null;
    }
    private void cambiarTurno() {
        if (juegoTerminado) {
            return;
        }

        if (verificarFinJuego()) {
            return;
        }

        turnoJugador = !turnoJugador;

        if (!juegoTerminado) {
            mostrarMensajeTurno();
        }

        if (!turnoJugador && !juegoTerminado) {
            realizarTurnoIA();
        }

        siguienteTurno();
    }


    public Jugador getJugadorIA() {
        return this.jugadorIA;
    }
    public boolean verificarFinJuego() {
        if (juegoTerminado) return true;
        boolean jugadorTienePersonajes = false;
        boolean iaTienePersonajes = false;

        int filas = Integer.parseInt(filasInput.getText());
        int columnas = Integer.parseInt(columnasInput.getText());

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                Nodo nodo = grafo.obtenerNodo(fila, columna);
                if (nodo != null && nodo.tienePersonaje()) {
                    // Verificar a qué facción pertenece el personaje
                    if (Acciones.getJugador().getListaPersonajes().isInList(nodo.getPersonaje())) {
                        jugadorTienePersonajes = true;
                    } else if (jugadorIA.getListaPersonajes().isInList(nodo.getPersonaje())) {
                        iaTienePersonajes = true;
                    }
                }
            }
        }
        if (!jugadorTienePersonajes || !iaTienePersonajes) {
            juegoTerminado = true;
            boolean finalJugadorTienePersonajes = jugadorTienePersonajes;
            boolean finalIaTienePersonajes = iaTienePersonajes;
            Platform.runLater(() -> {
                String mensaje;
                if (!finalJugadorTienePersonajes && !finalIaTienePersonajes) {
                    mensaje = "¡Empate! No quedan personajes en el tablero";
                } else if (!finalJugadorTienePersonajes) {
                    mensaje = "¡Has perdido! Tus personajes han sido eliminados";
                } else {
                    mensaje = "¡Has ganado! Todos los enemigos han sido eliminados";
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fin del Juego");
                alert.setHeaderText(null);
                alert.setContentText(mensaje);



                // Mostrar alerta y manejar la respuesta
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    volverAPantallaInicial();
                }
            });
            return true;
        }
        return false;


    }
    private void volverAPantallaInicial() {
        try {
            // Cargar la pantalla inicial
            FXMLLoader loader = new FXMLLoader(getClass().getResource("parameters-view.fxml"));
            Parent root = loader.load();

            // Obtener la escena actual
            Scene currentScene = gridPane.getScene();

            // Obtener el stage actual
            Stage stage = (Stage) currentScene.getWindow();

            // Cambiar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la pantalla inicial");
        }
    }

    @FXML
    public void siguienteTurno(){
        Acciones.manejarTurnos();
        refrescarTablero();
    }

    @FXML
    protected void onBotonGuardarClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar partida");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos JSON (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("partida_guardada.json");

        File archivoGuardar = fileChooser.showSaveDialog(null);

        if (archivoGuardar != null) {
            ParameterDataModelProperties modeloParaGuardar = crearModeloDesdeTablero();
            GestorGuardado.guardarPartida(modeloParaGuardar.getOriginal().getPartida(), archivoGuardar.getAbsolutePath());
        } else {
            System.out.println("Guardado cancelado por el usuario.");
        }
    }

    public void inicializarDesdeModelo(ParameterDataModelProperties modelo) {
        this.modeloParaGUICompartido = modelo;
        this.jugador = modelo.getOriginal(); // También asignas el jugador original
    }

    public ParameterDataModelProperties exportarPartida(ListaEnlazada<Nodo> nodos, String personajeJugador, Log logAcciones){
        PartidaDTO partida = new PartidaDTO();
        partida.nodos = new ListaEnlazada<>();
        partida.personajeJugadorHumano = personajeJugador;
        partida.logAcciones = logAcciones;

        Iterador<Nodo> iterador = nodos.getIterador();
        while (iterador.hasNext()) {
            Nodo nodo = iterador.next();
            NodoDTO dto = new NodoDTO(
                    nodo.getFila(),
                    nodo.getColumna(),
                    nodo.getPersonaje(),
                    nodo.getPersonaje() != null ? nodo.getPersonaje().getRutaImagen() : null,
                    nodo.getTerreno(),
                    nodo.getCosto()
            );

            // Agregar coordenadas de adyacentes, no nodos completos
            Iterador<Nodo> itAdyacentes = nodo.getAdyacentes().getIterador();
            while(itAdyacentes.hasNext()) {
                Nodo adyacente = itAdyacentes.next();
                dto.agregarAdyacente(adyacente.getFila(), adyacente.getColumna());
            }

            // Agregar el DTO a la lista SOLO una vez por nodo
            partida.nodos.add(dto);
        }

        return new ParameterDataModelProperties(new ParameterDataModel(jugador.getNombre(), personajeJugador, prepararParaGuardar(partida)));
    }


    public PartidaDTOParaGuardar prepararParaGuardar(PartidaDTO original){
        PartidaDTOParaGuardar dto = new PartidaDTOParaGuardar();
        dto.setPersonajeJugadorHumano(original.getPersonajeJugadorHumano());
        dto.setNodos(new ListaEnlazada<>());

        // Copiar nodos al DTO para guardar
        Iterador<NodoDTO> itNodos = original.getNodos().getIterador();
        while (itNodos.hasNext()) {
            dto.getNodos().add(itNodos.next());
        }

        // Aquí asumo que obtenerAccionesComoArray() devuelve algo serializable,
        // como un String[] o lista simple de Strings
        dto.setLogAcciones(original.getLogAcciones());
        dto.setNombreJugador(original.getJugadorActual());

        return dto;
    }



    private ParameterDataModelProperties crearModeloDesdeTablero(){
        ParameterDataModelProperties modelo = exportarPartida(this.nodos, jugador.getPersonaje().getClass().getSimpleName(), Acciones.getLog());
            return modelo;
    }



    public void inicializarDesdePartidaCargada(ListaEnlazada<Nodo> tablero, String[] logEventos, Jugador jugador) {
        this.partidaCargada = true;
        this.nodos = tablero;

        Acciones.setJugador(jugador);

        mostrarTableroCargado(tablero);
    }

    public void mostrarTableroCargado(ListaEnlazada<Nodo> tablero) {
        gridPane.getChildren().clear();
        nodos = new ListaEnlazada<>();

        Iterador<Nodo> iterador = tablero.getIterador();
        while (iterador.hasNext()) {
            Nodo nodo = iterador.next();
            nodos.add(nodo);

            Button boton = new Button();
            boton.setPrefSize(60, 60);

            if (nodo.getPersonaje() != null) {
                ImageView imagen = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(nodo.getPersonaje().getRutaImagen()))));
                imagen.setFitWidth(40);
                imagen.setFitHeight(40);
                boton.setGraphic(imagen);
            } else if (nodo.getTerreno() != null) {
                ImageView imagenTerreno = nodo.getTerreno().getImagen();
                imagenTerreno.setFitWidth(40);
                imagenTerreno.setFitHeight(40);
                boton.setGraphic(imagenTerreno);
            }
            Label costoLabel = new Label("C: " + nodo.getCosto());
            costoLabel.setStyle("-fx-font-size: 10; -fx-text-fill: black;");

            String hpTexto = (nodo.getPersonaje() != null) ? "HP: " + nodo.getPersonaje().getHp() : "";
            Label hpLabel = new Label(hpTexto);
            hpLabel.setStyle("-fx-font-size: 10; -fx-text-fill: #000000;");

            StackPane stack = new StackPane();

            stack.getChildren().addAll(boton);

            VBox etiquetas = new VBox(costoLabel, hpLabel);
            etiquetas.setAlignment(Pos.TOP_LEFT);
            etiquetas.setMouseTransparent(true);
            stack.getChildren().add(etiquetas);

            int fila = nodo.getFila();
            int columna = nodo.getColumna();

            boton.setOnAction(event -> seleccionarCasilla(nodo, boton));
            gridPane.add(boton, columna, fila);
        }

        gridPane.setVisible(true);
        gridPane.setManaged(true);
        configuracionPanel.setVisible(false);
        configuracionPanel.setManaged(false);
    }


}

