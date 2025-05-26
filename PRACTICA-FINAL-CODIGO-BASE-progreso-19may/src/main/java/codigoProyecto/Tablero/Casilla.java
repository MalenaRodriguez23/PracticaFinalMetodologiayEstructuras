package codigoProyecto.Tablero;

import codigoProyecto.Personajes.Facciones;

import java.util.Random;

public class Casilla {
    private int x;
    private int y;
    private Terreno terreno;

    public Casilla(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Casilla(int x, int y, Terreno terreno){
        this.x = x;
        this.y = y;
        this.terreno = terreno;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }



    public void setY(int y){
        this.y = y;
    }

    public Terreno getTerreno(){
        return terreno;
    }



}
