package codigoProyecto.GrafoTablero;

import codigoProyecto.Personajes.Facciones;
import codigoProyecto.Tablero.Terreno;
import codigoProyecto.Tablero.TerrenoAgua;
import codigoProyecto.Tablero.TerrenoFuego;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class NodoTest {
    private Nodo nodo;
    private Facciones personajeTest;



    @BeforeEach
    void setUp() {
        nodo = new Nodo(2, 3);
    }

    @Test
    void testConstructor() {
        assertEquals(2, nodo.getFila());
        assertEquals(3, nodo.getColumna());
        assertNull(nodo.getPersonaje());
        assertNull(nodo.getTerreno());
        assertTrue(nodo.getAdyacentes().getNumElementos() == 0);
    }

    @Test
    void testSetAndGetPersonaje() {
        nodo.setPersonaje(personajeTest);
        assertEquals(personajeTest, nodo.getPersonaje());
    }

    @Test
    void testTienePersonaje() {
        assertFalse(nodo.tienePersonaje());
        nodo.setPersonaje(personajeTest);
        assertFalse(nodo.tienePersonaje());
        nodo.setPersonaje(null);
        assertFalse(nodo.tienePersonaje());
    }

    @Test
    void testAgregarAdyacente() {
        Nodo adyacente = new Nodo(2, 4);
        nodo.agregarAdyacente(adyacente);

        assertEquals(1, nodo.getAdyacentes().getNumElementos());
        assertEquals(adyacente, nodo.getAdyacentes().getPrimero());
    }

    @Test
    void testSetTerrenoAgua() {
        Terreno terreno = new TerrenoAgua();
        nodo.setTerreno(terreno);

        assertNotNull(nodo.getTerreno());
        assertEquals("agua", nodo.getTerreno().getNombre().toLowerCase());
    }

    @Test
    void testSetTerrenoFuego() {
        Terreno terreno = new TerrenoFuego();
        nodo.setTerreno(terreno);

        assertNotNull(nodo.getTerreno());
        assertEquals("fuego", nodo.getTerreno().getNombre().toLowerCase());
    }

    @Test
    void testSetTerrenoInvalido() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Terreno terreno = new Terreno("agua",2) {
            @Override
            public String getNombre() { return "invalido"; }
        };
        nodo.setTerreno(terreno);

        assertTrue(outContent.toString().contains("El terreno indicado no tiene sentido"));
        System.setOut(System.out);
    }

    @Test
    void testCalcularDistancia() {
        Nodo otroNodo1 = new Nodo(2, 5);
        assertEquals(2, nodo.calcularDistancia(otroNodo1));

        Nodo otroNodo2 = new Nodo(4, 3);
        assertEquals(2, nodo.calcularDistancia(otroNodo2));

        Nodo otroNodo3 = new Nodo(5, 6);
        assertEquals(6, nodo.calcularDistancia(otroNodo3));
    }


}