package codigoProyecto.GrafoTablero;

import java.util.HashMap;
import java.util.Map;

public class Grafo {
    private final Map<String, Nodo> nodos;

    public Grafo(){
        this.nodos = new HashMap<>();
    }

    public Nodo agregarNodo(int fila, int columna){
        String clave = fila + "," + columna;
        Nodo nodo = new Nodo(fila, columna);
        nodo.setCosto(nodo.getCosto());
        nodos.put(clave, nodo);
        return nodo;
    }

    public Nodo obtenerNodo(int fila, int columna) {
        return nodos.get(fila + "," + columna);
    }

    public void conectarNodos(Nodo nodo1, Nodo nodo2) {
        if (nodo1 != null && nodo2 != null) {
            nodo1.agregarAdyacente(nodo2);
            nodo2.agregarAdyacente(nodo1);
        }
    }
}
