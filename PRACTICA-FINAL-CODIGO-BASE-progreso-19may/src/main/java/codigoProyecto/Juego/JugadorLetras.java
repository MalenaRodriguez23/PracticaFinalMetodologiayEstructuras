package codigoProyecto.Juego;

import codigoProyecto.Personajes.Filólogo;
import codigoProyecto.Personajes.Filósofo;
import codigoProyecto.Personajes.Historiador;
import codigoProyecto.Personajes.Poeta;

public class JugadorLetras extends Jugador {

    public JugadorLetras(String nombre) {
        super(nombre, "Letras");
    }

    @Override
    public void añadirPersonajes() {

        Historiador historiador = new Historiador();
        Poeta poeta = new Poeta();
        Filósofo filósofo = new Filósofo();
        Filólogo filólogo = new Filólogo();

        getListaPersonajes().add(historiador);
        getListaPersonajes().add(poeta);
        getListaPersonajes().add(filólogo);
        getListaPersonajes().add(filósofo);
    }
}