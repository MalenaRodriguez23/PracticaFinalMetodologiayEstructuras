package codigoProyecto.Juego;

import codigoProyecto.Personajes.Físico;
import codigoProyecto.Personajes.Ingeniero;
import codigoProyecto.Personajes.Matemático;
import codigoProyecto.Personajes.Químico;

public class JugadorCiencias extends Jugador {

    public JugadorCiencias(String nombre) {
        super(nombre, "Ciencias");
    }

    @Override
    public void añadirPersonajes() {

        Matemático matemático = new Matemático();
        Físico físico = new Físico();
        Ingeniero ingeniero = new Ingeniero();
        Químico químico = new Químico();

        getListaPersonajes().add(matemático);
        getListaPersonajes().add(ingeniero);
        getListaPersonajes().add(físico);
        getListaPersonajes().add(químico);
    }
}
