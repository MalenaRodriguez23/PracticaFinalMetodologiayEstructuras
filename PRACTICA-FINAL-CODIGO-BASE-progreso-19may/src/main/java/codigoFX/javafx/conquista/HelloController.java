package codigoFX.javafx.conquista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloController {

    public static void abrirTablero(ParameterDataModelProperties modeloParaGUICompartido) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloController.class.getResource("/codigoFX/javafx/conquista/tablero-view.fxml"));
            Parent root = loader.load();

            TableroController tableroController = loader.getController();
            tableroController.inicializarDesdeModelo(modeloParaGUICompartido);

            Stage stage = new Stage();
            stage.setTitle("Tablero de Juego");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al abrir el tablero: " + e.getMessage());
        }
    }


    @FXML
    protected void onMiBotonNuevaVentanaParametrosClick() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("parameters-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 560, 320);
            stage.setTitle("Menú principal");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}