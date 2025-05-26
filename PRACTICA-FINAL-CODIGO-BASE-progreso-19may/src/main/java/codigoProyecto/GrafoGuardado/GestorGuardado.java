package codigoProyecto.GrafoGuardado;


import codigoProyecto.Juego.Acciones;
import codigoProyecto.Juego.Log;
import codigoProyecto.Personajes.*;
import codigoProyecto.Tablero.Terreno;
import codigoProyecto.Tablero.TerrenoAgua;
import codigoProyecto.Tablero.TerrenoFuego;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GestorGuardado {
    public static void guardarPartida(PartidaDTOParaGuardar dtoParaGuardar, String rutaArchivo) {
        Gson gson = crearGsonConAdaptadores();
        if (dtoParaGuardar == null) {
            System.err.println("Error: el objeto PartidaDTOParaGuardar es null.");
            return;
        }

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(dtoParaGuardar, writer);
            System.out.println("Partida guardada en: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar partida: " + e.getMessage());
        }
    }

    public static PartidaDTOParaGuardar cargarPartida(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Gson gson = crearGsonConAdaptadores();
            PartidaDTOParaGuardar dtoParaGuardar = gson.fromJson(reader, PartidaDTOParaGuardar.class);

            if (dtoParaGuardar == null) {
                System.out.println("Error: El JSON no contiene datos válidos");
                return null;
            }

            Log log = dtoParaGuardar.reconstruirTableroYDevolverLog(dtoParaGuardar);
            Acciones.setLog(log);

            return dtoParaGuardar;

        } catch (IOException e) {
            System.err.println("Error al cargar la partida: " + e.getMessage());
            return null;
        }
    }


    public static Gson crearGsonConAdaptadores() {
        RuntimeTypeAdapterFactory<Facciones> faccionesAdapter =
                RuntimeTypeAdapterFactory.of(Facciones.class, "type")
                        .registerSubtype(FacciónCiencias.class, "facción ciencias")
                        .registerSubtype(Matemático.class, "matemático")
                        .registerSubtype(Físico.class, "físico")
                        .registerSubtype(Químico.class, "químico")
                        .registerSubtype(Ingeniero.class, "ingerniero")
                        .registerSubtype(FacciónLetras.class, "facción letras")
                        .registerSubtype(Historiador.class, "historiador")
                        .registerSubtype(Filósofo.class, "filósofo")
                        .registerSubtype(Poeta.class, "poeta")
                        .registerSubtype(Filólogo.class, "filólogo");



        RuntimeTypeAdapterFactory<Terreno> terrenoAdapter =
                RuntimeTypeAdapterFactory.of(Terreno.class, "type")
                        .registerSubtype(TerrenoAgua.class, "agua")
                        .registerSubtype(TerrenoFuego.class, "fuego");

        return new GsonBuilder()
                .registerTypeAdapterFactory(faccionesAdapter)
                .registerTypeAdapterFactory(terrenoAdapter)
                .setPrettyPrinting()
                .create();
    }



}
