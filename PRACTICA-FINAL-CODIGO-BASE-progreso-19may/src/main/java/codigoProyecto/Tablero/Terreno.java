package codigoProyecto.Tablero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public abstract class Terreno {
    private String nombre;
    protected int modificadorDefensa;
    protected String rutaImagen;

    protected Terreno(String nombre, int modificadorDefensa){
        this.nombre = nombre;
        this.modificadorDefensa = modificadorDefensa;
    }


    public String getRutaImagen() {
        return rutaImagen;
    }

    public ImageView getImagen(){
        if (rutaImagen == null || rutaImagen.isEmpty()) {
            System.err.println("No se ha definido la ruta de imagen para " + nombre);
            return null;
        }

        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(rutaImagen)));
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            imageView.setPreserveRatio(true);
            return imageView;
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + rutaImagen + " -> " + e.getMessage());
            return null;
        }
    }

    public int getModificadorDefensa(){
        return modificadorDefensa;
    }

    public String getNombre(){
        return nombre;
    }
}
