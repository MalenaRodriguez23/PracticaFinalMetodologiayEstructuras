package codigoProyecto.GrafoGuardado;

import codigoProyecto.GrafoTablero.ListaEnlazada;
import codigoProyecto.Personajes.Facciones;
import codigoProyecto.Tablero.Terreno;


public class NodoDTO {
    public int fila;
    public int columna;
    public Facciones personaje;
    public String rutaImagenPersonaje;
    public Terreno terreno;
    public int costo;
    public ListaEnlazada<int[]> adyacentes = new ListaEnlazada<>();  // Aquí guardamos solo coordenadas simples

    public NodoDTO(int fila, int columna, Facciones personaje, String rutaImagenPresonaje, Terreno terreno, int costo) {
        this.fila = fila;
        this.columna = columna;
        this.personaje = personaje;
        this.rutaImagenPersonaje = rutaImagenPersonaje;
        this.terreno = terreno;
        this.costo = costo;
        this.adyacentes = new ListaEnlazada<>();
    }

    public NodoDTO() {
    }

    public void agregarAdyacente(int fila, int columna) {
        int[] posicion = new int[]{fila, columna};
        this.adyacentes.add(posicion);
    }
}

