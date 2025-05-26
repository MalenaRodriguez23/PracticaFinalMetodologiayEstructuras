package codigoFX.javafx.conquista;

import codigoProyecto.GrafoGuardado.GrafoDTO;
import codigoProyecto.GrafoGuardado.PartidaDTOParaGuardar;
import codigoProyecto.Juego.Jugador;

public class ParameterDataModel {
    private String nombre;
    private String faccion;

    private Jugador jugador;
    private Jugador ia;
    private PartidaDTOParaGuardar partida;

    public ParameterDataModel(String nombre, String faccion, PartidaDTOParaGuardar partida){
        this.nombre = nombre;
        this.faccion = faccion;
        this.partida = partida;
    }

    public PartidaDTOParaGuardar getPartida() {
        return partida;
    }

    public ParameterDataModel(String nombre, String faccion){
        this.nombre = nombre;
        this.faccion = faccion;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPersonaje() { return faccion;}
    public void setPersonaje(String faccion) { this.faccion = faccion;}

    public Jugador getJugador() { return jugador; }
    public void setJugador(Jugador jugador) { this.jugador = jugador; }



}
