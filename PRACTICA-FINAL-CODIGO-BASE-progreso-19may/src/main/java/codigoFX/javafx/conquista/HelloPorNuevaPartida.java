package codigoFX.javafx.conquista;

import codigoProyecto.GrafoGuardado.GestorGuardado;
import codigoProyecto.GrafoGuardado.PartidaDTOParaGuardar;
import codigoProyecto.Juego.Acciones;
import codigoProyecto.Juego.Jugador;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HelloPorNuevaPartida {
    protected StringProperty texto = new SimpleStringProperty("No Hay Nada");

    @FXML
    private Button botonCargarPartida;

    private ParameterDataModel parametrosData = new ParameterDataModel("Isabel", "Ciencias");
    private ParameterDataModelProperties modeloParaGUICompartido = new ParameterDataModelProperties(parametrosData);


    @FXML
    protected void onMiBotonUsuarioNuevoClick() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("masparametros-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 590, 440);
            stage.setTitle("Características");
            stage.setScene(scene);
            ParameterController p = fxmlLoader.getController();
            p.loadUserData(this.modeloParaGUICompartido); //Carga los datos del modelo en algo.
            p.setStage(stage);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCargarPartidaClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona archivo de partida");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos JSON (*.json)", "*.json"));

        File archivoSeleccionado = fileChooser.showOpenDialog(null);

        if (archivoSeleccionado != null) {
            String rutaArchivo = archivoSeleccionado.getAbsolutePath();

            PartidaDTOParaGuardar partida = GestorGuardado.cargarPartida(rutaArchivo);
            if (partida != null) {

                try {
                    Jugador jugadorCargado = new Jugador(partida.getNombreJugador(), partida.getPersonajeJugadorHumano());
                    jugadorCargado.setListaPersonajes(partida.getPersonajesJugador());

                    parametrosData.setJugador(jugadorCargado);
                    Acciones.setJugador(jugadorCargado); // Por si usas Acciones también

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("tablero-view.fxml"));
                    Parent root = loader.load();

                    TableroController tableroController = loader.getController();
                    tableroController.inicializarDesdePartidaCargada(
                            partida.getTableroReconstruido(),
                            partida.getLogAcciones(),
                            jugadorCargado
                    );
                    System.out.println("Jugador que paso al controlador: " + parametrosData.getJugador());

                    Stage stageActual = (Stage) botonCargarPartida.getScene().getWindow();
                    stageActual.setScene(new Scene(root));
                    stageActual.setTitle("Partida cargada");
                    stageActual.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error al cargar la vista del tablero.");
                }
            } else {
                System.out.println("No se pudo cargar la partida");
            }
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }

}
