package codigoFX.javafx.conquista;

import codigoProyecto.GrafoGuardado.GestorGuardado;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ParameterController implements Initializable{
    @FXML
    private TextField textfieldNombre;
    @FXML
    private TextField textfieldPersonaje;

    private ParameterDataModelProperties model;
    private Stage scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("Inicialización en ejecución del controlador de parámetros\n");

        if (model != null) {
            this.updateGUIwithModel();
        }
    }


    @FXML
    protected void onBotonReiniciarClick() {
        model.rollback();
    }
    @FXML
    protected void onBotonCerrarClick() {
        scene.close();
    }

    @FXML
    protected void onBotonContinuarClick() {
        try {
            model.syncToOriginal();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("tablero-view.fxml"));
            Parent root = loader.load();
            TableroController controller = loader.getController();

            controller.setJugador(this.model.syncToOriginal());

            Stage stage = new Stage();
            stage.setTitle("Nombre del juego");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void updateGUIwithModel() {
        textfieldNombre.textProperty().bindBidirectional(model.nombreProperty());
        textfieldPersonaje.textProperty().bindBidirectional(model.personajeProperty());
    }

    public void loadUserData(ParameterDataModelProperties parametrosData) {
        this.model = parametrosData;
        this.updateGUIwithModel();
    }

    public void setStage(Stage s){
        this.scene = s;
    }
    @FXML
    private void onBotonGuardarClick(){
        model.commit();
    }

}
