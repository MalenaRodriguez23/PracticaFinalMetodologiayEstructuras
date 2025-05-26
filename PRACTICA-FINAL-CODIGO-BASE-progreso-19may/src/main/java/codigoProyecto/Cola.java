package codigoProyecto;

import codigoProyecto.GrafoTablero.ListaEnlazada;

public class Cola<TipoElemento> {
    private ListaEnlazada<TipoElemento> lista;
    private int max;
    private int tamano;

    public Cola (int tamanoMax){
        this.max = tamanoMax;
        this.tamano = 0;
        this.lista = new ListaEnlazada<TipoElemento>();
    }
    public int getTamano(){
        return tamano;
    }
    public int incTamano(){
        tamano++;
        return tamano;
    }
    public int decTamano(){
        tamano--;
        return tamano;
    }
    public void enqueue(TipoElemento elemento){
        if(getTamano() >= max ){
            System.out.println("códigoIntelliJ.Cola llena");
        }
        lista.add(elemento);
        incTamano();
    }
    public TipoElemento dequeue(){
        if (getTamano() == 0){
            System.out.println("códigoIntelliJ.Cola vacía");
        }
        TipoElemento element = lista.getPrimero();
        lista.deletePrimero();
        decTamano();
        return element;
    }
    public TipoElemento getPrimero(){
        if (lista.getNumElementos() == 0){
            throw new IllegalStateException("La cola está vacía");
        }
        return lista.getPrimero();
    }
    public TipoElemento getUltimo(){
        if (lista.getNumElementos() == 0 ){
            throw new IllegalStateException("La cola está vacía");
        }
        return lista.getUltimo();
    }
}
