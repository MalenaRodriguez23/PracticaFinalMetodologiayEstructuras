package codigoProyecto.GrafoTablero;


public interface Lista<TipoElemento> {
    boolean add(TipoElemento elemento);
    boolean delete (TipoElemento elemento);
    Iterador<TipoElemento> getIterador();
    int getNumElementos();
}
