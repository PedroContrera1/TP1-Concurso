package Entities;

import Exceptions.FechaInvalidaException;
import Exceptions.ParticipanteInvalidoException;

import java.time.LocalDate;
import java.util.Objects;

public class Inscripcion {
    private static final int PUNTOS_PRIMER_DIA = 10;

    private final Participante participante;
    private final LocalDate fechaInscripcion;

    public Inscripcion(Participante participante, LocalDate fechaInscripcion) {
        validarParticipante(participante);
        validarFecha(fechaInscripcion);
        this.participante = participante;
        this.fechaInscripcion = fechaInscripcion;
    }

    public Participante getParticipante() {
        return participante;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void otorgarPuntosSiCorresponde(LocalDate fechaInicioConcurso) {
        if (fechaInscripcion.equals(fechaInicioConcurso)) {
            participante.sumarPuntos(PUNTOS_PRIMER_DIA);
        }
    }

    private void validarParticipante(Participante participante) {
        if (participante == null) {
            throw new ParticipanteInvalidoException("El participante no debe ser nulo");
        }
    }

    private void validarFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new FechaInvalidaException("La fecha no puede ser nula");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inscripcion that)) return false;
        return Objects.equals(participante, that.participante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participante);
    }
}
