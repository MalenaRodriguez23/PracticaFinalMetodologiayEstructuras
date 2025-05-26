package codigoProyecto.AccionesyJugador;

import codigoProyecto.GrafoTablero.ListaEnlazada;
import codigoProyecto.Juego.Jugador;
import codigoProyecto.Personajes.Facciones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JugadorTest {

    private Jugador jugador;

    @BeforeEach
    void setUp() {
        jugador = new Jugador("Carlos", "Imperio");
    }

    @Test
    void testConstructorYGetters() {
        assertEquals("Carlos", jugador.getNombre());
        assertEquals("Imperio", jugador.getFaccion());
        assertNotNull(jugador.getListaPersonajes());
    }

    @Test
    void testSetHabilidadGeneralUsada() {
        assertFalse(jugador.isHabilidadGeneralUsada());
        jugador.setHabilidadGeneralUsada(true);
        assertTrue(jugador.isHabilidadGeneralUsada());
    }

    @Test
    void testSetListaPersonajes() {
        ListaEnlazada<Facciones> nuevaLista = new ListaEnlazada<>();
        jugador.setListaPersonajes(nuevaLista);
        assertEquals(nuevaLista, jugador.getListaPersonajes());
    }

}


