package Entities;

import Exceptions.NombreInvalidoException;
import Exceptions.ParticipanteInvalidoException;

import java.util.Objects;

public class Participante {
    private final String id;
    private final String nombre;
    private int puntos;

    public Participante(String id, String nombre) {
        validarId(id);
        validarNombre(nombre);
        this.id = id;
        this.nombre = nombre;
        this.puntos = 0;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NombreInvalidoException("El nombre no puede estar vacío");
        }
    }

    private void validarId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new ParticipanteInvalidoException("El id del participante no puede estar vacío");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participante that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
