package codigoFX.javafx.conquista;

import codigoProyecto.GrafoGuardado.PartidaDTOParaGuardar;
import javafx.beans.property.*;

public class ParameterDataModelProperties {
    protected ParameterDataModel original;
    private StringProperty personaje;
    private StringProperty nombre;


    public ParameterDataModelProperties(ParameterDataModel modelo) {
        this.original = modelo;

        this.nombre = new SimpleStringProperty();
        this.personaje = new SimpleStringProperty();

        if (modelo != null) {
            rollback();
        } else {
            System.out.println("Error: El modelo es null");
        }


    }

    public void commit() {
        original.setPersonaje(personaje.get());
        original.setNombre(nombre.get());
    }

    public void rollback() {
        this.nombre.set(original.getNombre());
        this.personaje.set(original.getPersonaje());
    }

    public ParameterDataModel getOriginal() {
        return original;
    }

    public void setOriginal(ParameterDataModel original) {
        this.original = original;
        rollback();
    }

    public Property<String> personajeProperty() {
        return personaje;
    }

    public Property<String> nombreProperty() {
        return nombre;
    }


    public StringProperty getNombre() {
        return nombre;
    }

    public ParameterDataModel syncToOriginal(){
        original.setNombre(nombre.get());
        original.setPersonaje(personaje.get());
        return original;
    }


}

