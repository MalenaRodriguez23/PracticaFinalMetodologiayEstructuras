package codigoProyecto.GrafoTablero;

public class NodoLE<TipoElemento> {
    public TipoElemento dato;
    public NodoLE<TipoElemento> siguiente;
    public NodoLE(TipoElemento dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
