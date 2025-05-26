package codigoProyecto.GrafoGuardado;

import codigoProyecto.GrafoTablero.ListaEnlazada;


public class GrafoDTO {
    ListaEnlazada<NodoDTO> nodos;

    public GrafoDTO(ListaEnlazada<NodoDTO> nodos){
        this.nodos = nodos;
    }
}
