package Entities;

import Exceptions.NombreInvalidoException;
import Exceptions.ParticipanteInvalidoException;

import java.util.Objects;

public class Participante {
    private final String dni;
    private final String nombre;
    private int puntos;

    public Participante(String dni, String nombre) {
        validarDni(dni);
        validarNombre(nombre);
        this.dni = dni;
        this.nombre = nombre;
        this.puntos = 0;
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

    private void validarDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new ParticipanteInvalidoException("El DNI no puede estar vacío");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participante that)) return false;
        return Objects.equals(dni, that.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }
}