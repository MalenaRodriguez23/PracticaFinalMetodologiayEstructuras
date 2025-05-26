package codigoProyecto.AccionesyJugador;

import codigoProyecto.AtaqueInvalidoException;
import codigoProyecto.GrafoTablero.Nodo;
import codigoProyecto.Juego.Acciones;
import codigoProyecto.Personajes.Facciones;
import codigoProyecto.Personajes.Físico;
import codigoProyecto.Personajes.Químico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccionesTest {

    @Test
    public void testAtacarConObjetivoNuloLanzaExcepcion() {
        Facciones atacante = new Físico();
        Nodo nodoObjetivo = null;

        assertThrows(AtaqueInvalidoException.class, () -> {
            Acciones.atacar(atacante, nodoObjetivo);
        });
    }

    @Test
    public void testAtacarAEnemigoReduceVida() throws AtaqueInvalidoException {
        Facciones atacante = new Físico();
        Facciones enemigo = new Químico();

        Nodo nodoObjetivo = new Nodo(0, 1);
        nodoObjetivo.setPersonaje(enemigo);

        Acciones.atacar(atacante, nodoObjetivo);
    }
    @Test
    public void testAtacarAMismoEquipoLanzaExcepcion() {
        Facciones atacante = new Físico();
        Facciones aliado = new Físico();

        Nodo nodoObjetivo = new Nodo(0, 1);
        nodoObjetivo.setPersonaje(aliado);

        assertThrows(AtaqueInvalidoException.class, () -> {
            Acciones.atacar(atacante, nodoObjetivo);
        });
    }

    @Test
    public void testAtacarFueraDeRangoLanzaExcepcion() {
        Facciones atacante = new Físico();
        Facciones enemigo = new Químico();

        Nodo nodoObjetivo = new Nodo(5, 5);
        nodoObjetivo.setPersonaje(enemigo);

        assertThrows(AtaqueInvalidoException.class, () -> {
            Acciones.atacar(atacante, nodoObjetivo);
        });
    }

    @Test
    public void testAtaqueMataAlEnemigoLoEliminaDelNodo() throws AtaqueInvalidoException {
        Facciones atacante = new Físico();
        Facciones enemigo = new Químico(); // poca vida

        Nodo nodoObjetivo = new Nodo(0, 1);
        nodoObjetivo.setPersonaje(enemigo);

        Acciones.atacar(atacante, nodoObjetivo);

        assertNull(nodoObjetivo.getPersonaje(), "El enemigo debería haber sido eliminado del nodo.");
    }
}



