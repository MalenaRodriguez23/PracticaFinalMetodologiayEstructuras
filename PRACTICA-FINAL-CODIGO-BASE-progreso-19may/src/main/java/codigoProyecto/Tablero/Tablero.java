package codigoProyecto.Tablero;


import java.util.Random;

public class Tablero {
    private Casilla[][] casillas;
    int ancho;
    int alto;


    public Tablero(int ancho, int alto){
        this.ancho = ancho;
        this.alto = alto;
        this.casillas = new Casilla[ancho][alto];
        inicializarTablero();
    }
    private void inicializarTablero(){
        Random random = new Random();
        for (int x = 0; x<ancho; x++){
            for (int y = 0; y < alto; y++){
                Terreno terreno;
                int tipo = random.nextInt(10);
                if (tipo < 1){
                    terreno = new TerrenoAgua();
                } else if ( tipo < 2){
                    terreno = new TerrenoFuego();
                } else {
                    terreno = null;
                }
                casillas[x][y] = new Casilla(x,y,terreno);
            }
        }
    }



}
