package codigoProyecto.GrafoTablero;

public interface Iterador<TipoElemento> {
    boolean hasNext();
    TipoElemento next();
    void delete();
}
