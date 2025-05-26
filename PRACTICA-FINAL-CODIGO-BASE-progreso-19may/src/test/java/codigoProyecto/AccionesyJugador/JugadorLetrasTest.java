package codigoProyecto.AccionesyJugador;

import codigoProyecto.GrafoTablero.ListaEnlazada;
import codigoProyecto.Juego.JugadorLetras;
import codigoProyecto.Personajes.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JugadorLetrasTest {

    @Test
    void testConstructorYPersonajes() {
        JugadorLetras jugador = new JugadorLetras("Laura");

        assertEquals("Laura", jugador.getNombre());
        assertEquals("Letras", jugador.getFaccion());

        ListaEnlazada<Facciones> lista = jugador.getListaPersonajes();
        assertNotNull(lista);
        assertEquals(4, lista.getNumElementos());

        assertTrue(lista.obtener(0) instanceof Historiador);
        assertTrue(lista.obtener(1) instanceof Poeta);
        assertTrue(lista.obtener(2) instanceof Filólogo);
        assertTrue(lista.obtener(3) instanceof Filósofo);
    }
}
