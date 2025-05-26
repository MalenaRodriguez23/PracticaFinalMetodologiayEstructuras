package codigoProyecto.GrafoTablero;

import codigoProyecto.GrafoTablero.ListaEnlazada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class ListaEnlazadaTest {
    private ListaEnlazada<String> lista;

    @BeforeEach
    void setUp() {
        lista = new ListaEnlazada<>();
    }

    @Test
    void testConstructor() {
        assertNull(lista.getCabeza());
        assertEquals(0, lista.getNumElementos());
    }

    @Test
    void testAddPrimerElemento() {
        assertTrue(lista.add("Primero"));
        assertEquals(1, lista.getNumElementos());
        assertEquals("Primero", lista.getPrimero());
        assertEquals("Primero", lista.getUltimo());
    }

    @Test
    void testAddMultiplesElementos() {
        lista.add("Primero");
        lista.add("Segundo");
        lista.add("Tercero");

        assertEquals(3, lista.getNumElementos());
        assertEquals("Primero", lista.getPrimero());
        assertEquals("Tercero", lista.getUltimo());
    }

    @Test
    void testDeleteExistente() {
        lista.add("A");
        lista.add("B");
        lista.add("C");

        assertTrue(lista.delete("B"));
        assertEquals(2, lista.getNumElementos());
        assertFalse(lista.isInList("B"));
    }

    @Test
    void testDeletePrimerElemento() {
        lista.add("A");
        lista.add("B");

        assertTrue(lista.delete("A"));
        assertEquals(1, lista.getNumElementos());
        assertEquals("B", lista.getPrimero());
    }

    @Test
    void testDeleteUltimoElemento() {
        lista.add("A");
        lista.add("B");

        assertTrue(lista.delete("B"));
        assertEquals(1, lista.getNumElementos());
        assertEquals("A", lista.getUltimo());
    }

    @Test
    void testDeleteNoExistente() {
        lista.add("A");

        assertFalse(lista.delete("B"));
        assertEquals(1, lista.getNumElementos());
    }

    @Test
    void testDeleteListaVacia() {
        assertFalse(lista.delete("A"));
        assertEquals(0, lista.getNumElementos());
    }

    @Test
    void testDeletePrimero() {
        lista.add("A");
        lista.add("B");

        lista.deletePrimero();

        assertEquals(1, lista.getNumElementos());
        assertEquals("B", lista.getPrimero());
    }



    @Test
    void testDeleteUltimo() {
        lista.add("A");
        lista.add("B");

        lista.deleteUltimo();

        assertEquals(1, lista.getNumElementos());
        assertEquals("A", lista.getUltimo());
    }



    @Test
    void testDeleteUltimoUnicoElemento() {
        lista.add("A");
        lista.deleteUltimo();

        assertEquals(0, lista.getNumElementos());
        assertNull(lista.getPrimero());
    }

    @Test
    void testGetPrimero() {
        assertNull(lista.getPrimero());

        lista.add("A");
        assertEquals("A", lista.getPrimero());
    }

    @Test
    void testGetUltimo() {
        assertNull(lista.getUltimo());

        lista.add("A");
        lista.add("B");
        assertEquals("B", lista.getUltimo());
    }

    @Test
    void testObtenerPorIndice() {
        lista.add("A");
        lista.add("B");
        lista.add("C");

        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
        assertNull(lista.obtener(-1));
        assertNull(lista.obtener(3));
    }

    @Test
    void testIsInList() {
        assertFalse(lista.isInList("A"));

        lista.add("A");
        lista.add("B");

        assertTrue(lista.isInList("A"));
        assertTrue(lista.isInList("B"));
        assertFalse(lista.isInList("C"));
    }

    @Test
    void testGetIterador() {
        assertNotNull(lista.getIterador());

        lista.add("A");
        lista.add("B");

        var iterador = lista.getIterador();
        assertTrue(iterador.hasNext());
        assertEquals("A", iterador.next());
    }
}
