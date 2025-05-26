package codigoProyecto.GrafoTablero;

public class IteradorListaEnlazada<TipoElemento> implements Iterador<TipoElemento> {
    private NodoLE<TipoElemento> actual;
    private NodoLE<TipoElemento> previo;

    public IteradorListaEnlazada(NodoLE<TipoElemento> cabeza){
        this.actual = cabeza;
        this.previo = null;
    }
    @Override
    public boolean hasNext(){
        return actual != null;
    }
    @Override
    public TipoElemento next() {
        if (!hasNext()) {
            System.out.println("No hay más elementos");
        }
        TipoElemento elemento = actual.dato;
        previo = actual;
        actual = actual.siguiente;
        return elemento;
    }
    @Override
    public void delete(){
        if (previo == null) {
            System.out.println("No es posible");
            previo.siguiente = actual;
            previo = null;
        }
    }
}
