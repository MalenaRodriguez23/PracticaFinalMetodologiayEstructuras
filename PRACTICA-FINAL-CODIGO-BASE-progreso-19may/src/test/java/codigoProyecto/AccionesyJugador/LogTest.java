package codigoProyecto.AccionesyJugador;

import codigoProyecto.GrafoTablero.Iterador;
import codigoProyecto.Juego.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {
    private Log log;

    @BeforeEach
    void setUp() {
        log = new Log();
    }

    @Test
    void testConstructorInicializaCorrectamente() {
        assertEquals(1, log.getTurnoActual());
    }

    @Test
    void testAgregarYObtenerAccionesComoArray() {
        log.agregar("Ataque", "Éxito");
        log.agregar("Defensa", "Fallo");

        String[] acciones = log.obtenerAccionesComoArray();
        assertEquals(2, acciones.length);
        assertTrue(acciones[0].contains("Ataque"));
        assertTrue(acciones[1].contains("Defensa"));
    }

    @Test
    void testGetIterador() {
        log.agregar("Mover", "Realizado");
        Iterador<String> iterador = log.getIterador();

        assertTrue(iterador.hasNext());
        String accion = iterador.next();
        assertTrue(accion.contains("Mover"));
        assertFalse(iterador.hasNext());
    }

    @Test
    void testSiguienteTurno() {
        log.siguienteTurno();
        assertEquals(2, log.getTurnoActual());
    }

    @Test
    void testObtenerCompleto() {
        log.agregar("Atacar", "Éxito");
        log.agregar("Curar", "Éxito");
        String completo = log.obtenerCompleto();
        assertTrue(completo.contains("Atacar"));
        assertTrue(completo.contains("Curar"));
    }

    @Test
    void testRegistrar() {
        log.registrar("Ciencias", "Especial", "Activa escudo");
        String[] acciones = log.obtenerAccionesComoArray();
        assertEquals(1, acciones.length);
        assertEquals("[Ciencias,Especial,Activa escudo] -> Finalizado. Turno: 1", acciones[0]);
    }

    @Test
    void testAgregarLineaYAgregarLineaDesdeString() {
        log.agregarLinea("Inicio del juego");
        log.agregarLineaDesdeString("Fin del turno");

    }
}

