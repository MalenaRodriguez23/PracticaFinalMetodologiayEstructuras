package codigoProyecto.AccionesyJugador;

import codigoProyecto.Juego.EntradaLog;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntradaLogTest {
    @Test
    void testSalida() {
        EntradaLog entrada = new EntradaLog("Mover", "Éxito", 3);
        String salida = entrada.salida();
        assertEquals("Turno 3 - Mover - Éxito", salida);
    }
}

