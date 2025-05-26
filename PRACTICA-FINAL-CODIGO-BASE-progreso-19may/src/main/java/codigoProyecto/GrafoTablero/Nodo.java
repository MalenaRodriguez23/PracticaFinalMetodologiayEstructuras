package codigoProyecto.GrafoTablero;

import codigoProyecto.Personajes.Facciones;
import codigoProyecto.Tablero.Terreno;
import codigoProyecto.Tablero.TerrenoAgua;
import codigoProyecto.Tablero.TerrenoFuego;

import java.util.Random;

public class Nodo {
    private final int fila;
    private final int columna;
    private ListaEnlazada<Nodo> adyacentes;
    private Facciones personaje;
    private Terreno terreno;
    private int costo;
    private Nodo siguiente;

    public Nodo(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
        this.adyacentes = new ListaEnlazada<>();
        this.personaje = null;
        this.terreno = null;
        this.costo = 0;
    }

    public Facciones getPersonaje(){
        return personaje;
    }


    public int getCosto(){
        return costo;
    }

    public void setCosto(int costo){
        int min = -2;
        int max = 2;
        Random random = new Random();
        int randomNum = random.nextInt((max - min) + 1) + min;
        System.out.println("El costo del terreno ha sido generado");
        this.costo = randomNum;
    }

    public void setPersonaje(Facciones personaje){
        this.personaje = personaje;
    }

    public boolean tienePersonaje(){
        return personaje != null;
    }

    public int getFila(){
        return fila;
    }

    public int getColumna(){
        return columna;
    }

    public ListaEnlazada<Nodo> getAdyacentes(){
        return this.adyacentes;
    }

    public void agregarAdyacente(Nodo nodo){
        this.adyacentes.add(nodo);
    }

    public Terreno getTerreno(){
        return terreno;
    }

    public void setTerreno(Terreno terr){
        if (terr.getNombre().equalsIgnoreCase("agua")){
            this.terreno = new TerrenoAgua();
        } else if (terr.getNombre().equalsIgnoreCase("fuego")){
            this.terreno = new TerrenoFuego();
        } else {
            System.out.println("El terreno indicado no tiene sentido o es el predeterminado");
        }
    }

    public int calcularDistancia(Nodo otroNodo){
        return Math.abs(this.fila - otroNodo.getFila()) + Math.abs(this.columna - otroNodo.getColumna());
    }

    @Override
    public String toString(){
        return "" + costo;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente){
        this.siguiente = siguiente;
    }
}
