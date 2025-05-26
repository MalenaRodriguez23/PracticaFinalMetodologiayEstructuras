package codigoProyecto.GrafoTablero;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrafoTest {

    private Grafo grafo;

    @BeforeEach
    void setUp() {
        grafo = new Grafo();
    }

    @Test
    void testConstructor() {
        assertNotNull(grafo);
        assertNull(grafo.obtenerNodo(0, 0), "El grafo debería empezar vacío");
    }

    @Test
    void testAgregarNodo() {
        Nodo nodo = grafo.agregarNodo(1, 2);

        assertNotNull(nodo);
        assertEquals(1, nodo.getFila());
        assertEquals(2, nodo.getColumna());
        assertSame(nodo, grafo.obtenerNodo(1, 2));
    }

    @Test
    void testAgregarMultiplesNodos() {
        Nodo nodo1 = grafo.agregarNodo(0, 0);
        Nodo nodo2 = grafo.agregarNodo(0, 1);
        Nodo nodo3 = grafo.agregarNodo(1, 0);

        assertSame(nodo1, grafo.obtenerNodo(0, 0));
        assertSame(nodo2, grafo.obtenerNodo(0, 1));
        assertSame(nodo3, grafo.obtenerNodo(1, 0));
    }

    @Test
    void testObtenerNodoInexistente() {
        assertNull(grafo.obtenerNodo(99, 99));
    }

    @Test
    void testConectarNodos() {
        Nodo nodo1 = grafo.agregarNodo(0, 0);
        Nodo nodo2 = grafo.agregarNodo(0, 1);

        grafo.conectarNodos(nodo1, nodo2);

    }

    @Test
    void testConectarNodosNull() {
        Nodo nodoReal = grafo.agregarNodo(0, 0);

        assertDoesNotThrow(() -> grafo.conectarNodos(null, nodoReal));
        assertDoesNotThrow(() -> grafo.conectarNodos(nodoReal, null));
        assertDoesNotThrow(() -> grafo.conectarNodos(null, null));
    }

    @Test
    void testConectarNodosRepetidos() {
        Nodo nodo1 = grafo.agregarNodo(0, 0);
        Nodo nodo2 = grafo.agregarNodo(0, 1);

        grafo.conectarNodos(nodo1, nodo2);
        grafo.conectarNodos(nodo1, nodo2);



    }

}