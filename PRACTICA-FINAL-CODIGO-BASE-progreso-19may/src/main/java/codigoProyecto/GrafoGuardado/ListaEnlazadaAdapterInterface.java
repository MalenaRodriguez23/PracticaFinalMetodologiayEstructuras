package codigoProyecto.GrafoGuardado;

import codigoProyecto.GrafoTablero.ListaEnlazada;
import com.google.gson.*;

import java.lang.reflect.Type;

public interface ListaEnlazadaAdapterInterface<T> extends JsonSerializer<ListaEnlazada<T>> {
    @Override
    JsonElement serialize(ListaEnlazada<T> src, Type typeOfSrc, JsonSerializationContext context);

    ListaEnlazada<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException;
}
