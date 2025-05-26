package codigoProyecto.Juego;

public class EntradaLog {
    private String accion;
    private String resultado;
    private int turno;
    public EntradaLog(String accion, String resultado, int turno){
        this.accion = accion;
        this.resultado = resultado;
        this.turno = turno;
    }
    public String salida(){
        return "Turno " + turno + " - " + accion + " - " + resultado;
    }
}
