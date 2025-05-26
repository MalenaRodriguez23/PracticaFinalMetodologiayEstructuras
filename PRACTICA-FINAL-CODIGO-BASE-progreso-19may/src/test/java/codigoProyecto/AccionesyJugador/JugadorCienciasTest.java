package codigoProyecto.AccionesyJugador;

import codigoProyecto.GrafoTablero.ListaEnlazada;
import codigoProyecto.Juego.JugadorCiencias;
import codigoProyecto.Personajes.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JugadorCienciasTest {

    @Test
    void testConstructorYPersonajes() {
        JugadorCiencias jugador = new JugadorCiencias("Isaac");

        assertEquals("Isaac", jugador.getNombre());
        assertEquals("Ciencias", jugador.getFaccion());

        ListaEnlazada<Facciones> lista = jugador.getListaPersonajes();
        assertNotNull(lista);
        assertEquals(4, lista.getNumElementos());

        assertTrue(lista.obtener(0) instanceof Matemático);
        assertTrue(lista.obtener(1) instanceof Ingeniero);
        assertTrue(lista.obtener(2) instanceof Físico);
        assertTrue(lista.obtener(3) instanceof Químico);
    }
}

