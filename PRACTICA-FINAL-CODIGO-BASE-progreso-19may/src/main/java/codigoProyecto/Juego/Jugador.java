package codigoProyecto.Juego;

import codigoProyecto.GrafoTablero.ListaEnlazada;
import codigoProyecto.Personajes.Facciones;


public class Jugador {
    private  String nombre;
    private  String faccion;
    private boolean habilidadGeneralUsada = false;
    public ListaEnlazada<Facciones> ListaPersonajes = new ListaEnlazada<>();

    public Jugador(String nombre, String faccion) {
        this.nombre = nombre;
        this.faccion = faccion;
        añadirPersonajes();
    }

    public void añadirPersonajes(){}

    public String getNombre() {
        return nombre;
    }

    public String getFaccion() {
        return faccion;
    }

    public ListaEnlazada<Facciones> getListaPersonajes() {
        return ListaPersonajes;
    }
    public boolean isHabilidadGeneralUsada() {
        return habilidadGeneralUsada;
    }

    public void setHabilidadGeneralUsada(boolean usado) {
        this.habilidadGeneralUsada = usado;
    }


    public void setListaPersonajes(ListaEnlazada<Facciones> personajesJugador) {
        this.ListaPersonajes = personajesJugador;
    }
}
