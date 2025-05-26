package codigoProyecto.Tablero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CasillaTest {

    @Test
    void getX() {
        Casilla casilla = new Casilla(1,2);
        assertEquals(1, casilla.getX());
    }

    @Test
    void getY() {
        Casilla casilla = new Casilla(1,2);
        assertEquals(2, casilla.getY());

    }

    @Test
    void setY() {
        Casilla casilla = new Casilla(1,2);
        casilla.setY(5);
        assertEquals(5,casilla.getY());
    }

    @Test
    void getTerreno() {
        TerrenoAgua agua = new TerrenoAgua();
        Casilla casilla = new Casilla(1,2,agua);
        assertEquals(agua, casilla.getTerreno());
    }
}