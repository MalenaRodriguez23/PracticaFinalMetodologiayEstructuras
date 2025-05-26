package codigoProyecto.Juego;

import codigoProyecto.GrafoTablero.Iterador;
import codigoProyecto.GrafoTablero.ListaEnlazada;

public class Log {
    protected ListaEnlazada<EntradaLog> entradas;
    private int turnoActual;

    public Log(){
        this.entradas = new ListaEnlazada<EntradaLog>();
        this.turnoActual = 1;
    }

    public String[] obtenerAccionesComoArray() {
        String[] acciones = new String[entradas.getNumElementos()];
        int i = 0;
        Iterador<EntradaLog> iter = entradas.getIterador();
        while(iter.hasNext()) {
            EntradaLog entrada = iter.next();
            acciones[i++] = entrada.salida();
        }
        return acciones;
    }


    public Iterador<String> getIterador() {
        return new Iterador<String>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < entradas.getNumElementos();
            }
            @Override
            public String next() {
                EntradaLog entrada = entradas.obtener(index);
                index++;
                return entrada.salida(); // o el método que devuelve la cadena de la entrada
            }

            @Override
            public void delete() {

            }
        };
    }

    public void agregar(String accion, String resultado){
        EntradaLog nuevaEntrada = new EntradaLog(accion, resultado, turnoActual);
        entradas.add(nuevaEntrada);
    }

    public int getTurnoActual(){
        return turnoActual;
    }

    public void siguienteTurno(){
        turnoActual++;
    }

    public String obtenerCompleto(){
        String logCompleto = "";
        int posicion = 0;
        while (posicion < entradas.getNumElementos()){
            EntradaLog entrada = entradas.obtener(posicion);
            String salida = entrada.salida();
            logCompleto = logCompleto + salida;
            posicion++;
        }
        return logCompleto;

    }

    public void registrar(String faccion, String tipoEvento, String mensaje){
        String accion = "[" + faccion + "," + tipoEvento + "," + mensaje +"]";
        EntradaLog nuevaEntrada = new EntradaLog(accion, "Finalizado", turnoActual);
        entradas.add(nuevaEntrada);
    }

    private ListaEnlazada<String> acciones = new ListaEnlazada<>();

    public void agregarLinea(String texto) {
        acciones.add(texto);
    }

    public void agregarLineaDesdeString(String linea) {
        this.agregarLinea(linea);
    }


}
