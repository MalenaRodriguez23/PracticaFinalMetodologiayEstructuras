package codigoProyecto.GrafoGuardado;

import codigoProyecto.GrafoTablero.Iterador;
import codigoProyecto.GrafoTablero.ListaEnlazada;
import com.google.gson.*;
import java.lang.reflect.Type;

public class ListaEnlazadaAdapter<T> implements ListaEnlazadaAdapterInterface<T> {
    private final Class<T> clazz;

    public ListaEnlazadaAdapter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public JsonElement serialize(ListaEnlazada<T> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray array = new JsonArray();
        Iterador<T> iter = src.getIterador();
        while (iter.hasNext()) {
            array.add(context.serialize(iter.next()));
        }
        return array;
    }

    @Override
    public ListaEnlazada<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        JsonArray array = json.getAsJsonArray();
        for (JsonElement elem : array) {
            T obj = context.deserialize(elem, clazz);
            lista.add(obj);
        }
        return lista;
    }
}
