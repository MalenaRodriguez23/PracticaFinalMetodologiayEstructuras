module es.uah.matcomp.mp.javafx.conquista {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires com.google.gson;
    requires com.google.errorprone.annotations;

    opens codigoFX.javafx.conquista to javafx.fxml, com.google.gson;
    opens codigoProyecto.Juego to com.google.gson;
    opens codigoProyecto.GrafoTablero to com.google.gson;
    opens codigoProyecto.Personajes to com.google.gson;
    opens codigoProyecto.Tablero to com.google.gson;
    opens codigoProyecto.GrafoGuardado to com.google.gson;


    exports codigoFX.javafx.conquista;

}
