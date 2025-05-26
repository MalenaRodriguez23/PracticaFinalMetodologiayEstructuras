package codigoProyecto.Personajes;

import codigoProyecto.Tablero.Casilla;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

public abstract class Facciones {
    protected int hp;
    protected int ataque;
    protected int defensa;
    protected int rangoMovimiento;
    protected int rangoAtaque;
    protected String nombre;
    private Casilla posicion;
    private boolean habilidadEspecialUsada = false;
    private boolean habilidadGeneralUsada = false;

    protected String rutaImagen;

    public String getRutaImagen() {
        return rutaImagen;
    }

    public Facciones(int hp, int ataque, int defensa, int movimiento, int rangoAtaque) {
        this.hp = hp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.rangoMovimiento = movimiento;
        this.rangoAtaque = rangoAtaque;
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

    public int getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public int getAtaque(){
        return ataque;
    }

    public void setAtaque(int ataque){
        this.ataque = ataque;
    }

    public int getDefensa(){
        return defensa;
    }

    public void setDefensa(int defensa){
        this.defensa = defensa;
    }

    public int getRangoMovimiento(){
        return rangoMovimiento;
    }

    public void setRangoMovimiento(int rangoMovimiento){
        this.rangoMovimiento = rangoMovimiento;
    }

    public int getRangoAtaque(){
        return rangoAtaque;
    }

    public void setRangoAtaque(int rangoAtaque){
        this.rangoAtaque = rangoAtaque;
    }

    public void setPosicion(Casilla posicion) {
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre != null ? nombre : this.getClass().getSimpleName();
    }
    public Casilla getPosicion() {
        return posicion;
    }

    public boolean isHabilidadEspecialUsada() {
        return habilidadEspecialUsada;
    }
    public boolean isHabilidadGeneralUsada() {
        return habilidadGeneralUsada;
    }


    public void setHabilidadEspecialUsada(boolean habilidadEspecialUsada){
        this.habilidadEspecialUsada = habilidadEspecialUsada;
    }
    public void setHabilidadGeneralUsada(boolean habilidadGeneralUsada){
        this.habilidadGeneralUsada = habilidadGeneralUsada;
    }
}
