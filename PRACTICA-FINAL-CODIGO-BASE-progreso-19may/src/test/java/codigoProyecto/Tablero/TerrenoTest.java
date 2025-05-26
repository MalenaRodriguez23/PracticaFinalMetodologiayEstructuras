package codigoProyecto.Tablero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerrenoTest {

    @Test
    void getRutaImagen() {
        TerrenoFuego fuego = new TerrenoFuego();
        assertEquals("/Imágenes/terrenoFuego.jpg", fuego.getRutaImagen());
        TerrenoAgua agua = new TerrenoAgua();
        assertEquals("/Imágenes/terrenoAgua.jpg", agua.getRutaImagen());
    }



    @Test
    void getModificadorDefensa() {
        TerrenoFuego fuego = new TerrenoFuego();
        assertEquals(-2,fuego.getModificadorDefensa());
        TerrenoAgua agua = new TerrenoAgua();
        assertEquals(-1,agua.getModificadorDefensa());
    }

    @Test
    void getNombre() {
        TerrenoFuego fuego = new TerrenoFuego();
        assertEquals("Fuego", fuego.getNombre());
        TerrenoAgua agua = new TerrenoAgua();
        assertEquals("Agua", agua.getNombre());
    }
}