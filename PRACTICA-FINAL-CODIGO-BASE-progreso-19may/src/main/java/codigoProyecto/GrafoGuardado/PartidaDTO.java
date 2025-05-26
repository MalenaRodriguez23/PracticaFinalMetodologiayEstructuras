package codigoProyecto.GrafoGuardado;

import codigoProyecto.GrafoTablero.Iterador;
import codigoProyecto.GrafoTablero.ListaEnlazada;
import codigoProyecto.GrafoTablero.Nodo;
import codigoProyecto.Juego.Log;

public class PartidaDTO {
    public ListaEnlazada<NodoDTO> nodos;
    public String personajeJugadorHumano;
    public Log logAcciones;
    public String JugadorActual;

    public PartidaDTO(){}

    public String getPersonajeJugadorHumano(){
        return personajeJugadorHumano;
    }

    public Log getLogAcciones(){
        return logAcciones;
    }

    public ListaEnlazada<NodoDTO> getNodos(){
        return nodos;
    }

    public String getJugadorActual() {
        return JugadorActual;
    }
}
