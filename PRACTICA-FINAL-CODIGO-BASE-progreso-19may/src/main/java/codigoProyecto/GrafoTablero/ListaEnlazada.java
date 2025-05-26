package codigoProyecto.GrafoTablero;

public class ListaEnlazada<TipoElemento> implements Lista<TipoElemento> {
    private NodoLE<TipoElemento> cabeza;
    private int numElementos;

    public ListaEnlazada(){
        this.cabeza = null;
        this.numElementos = 0;
    }
    @Override
    public boolean add(TipoElemento elemento) {
        NodoLE<TipoElemento> nuevo = new NodoLE<>(elemento);
        if (cabeza == null){
            cabeza = nuevo;
        } else {
            NodoLE<TipoElemento> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        numElementos++;
        return true;
    }

    @Override
    public boolean delete(TipoElemento elemento) {
        if (cabeza == null) return false;
        if (cabeza.dato.equals(elemento)) {
            cabeza = cabeza.siguiente;
            numElementos--;
            return true;
        }
        NodoLE<TipoElemento> actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.dato.equals(elemento)) {
            actual = actual.siguiente;
        }
        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
            numElementos--;
            return true;
        }
        return false;

    }

    @Override
    public int getNumElementos() {
        return numElementos;
    }

    @Override
    public Iterador<TipoElemento> getIterador() {
        return new IteradorListaEnlazada(cabeza);
    }
    public void deletePrimero() {
        if (cabeza == null) {
            System.out.println("Vacía");
        }
        cabeza = cabeza.siguiente;
        numElementos--;
    }
    public void deleteUltimo() {
        if (cabeza == null) {
            System.out.println("Vacía");
        }
        if (cabeza.siguiente == null) {
            cabeza = null;
        } else {
            NodoLE<TipoElemento> temp = cabeza;
            while (temp.siguiente.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = null;
        }
        numElementos--;
    }
    public TipoElemento getPrimero(){
        if (cabeza==null){
            return null;
        } else {
            return cabeza.dato;
        }
    }
    public TipoElemento getUltimo(){
        if (cabeza==null){
            return null;
        } else {
            NodoLE<TipoElemento> temp = cabeza;
            while (temp.siguiente != null){
                temp = temp.siguiente;
            }
            return temp.dato;
        }
    }
    public TipoElemento obtener(int indice){
        if (indice < 0 || indice >= numElementos){
            return null;
        }
        NodoLE<TipoElemento> actual = cabeza;
        int contador = 0;
        while (contador < indice){
            actual = actual.siguiente;
            contador++;
        }
        return actual.dato;
    }

    public NodoLE<TipoElemento> getCabeza() {
        return cabeza;
    }

    public boolean isInList(TipoElemento elemento) {
        NodoLE<TipoElemento> actual = cabeza;
        while (actual != null) {
            if (actual.dato.equals(elemento)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }
}
