package Entities;

import Exceptions.FechaInvalidaException;
import Exceptions.ParticipanteInvalidoException;

import java.time.LocalDate;

public class Inscripcion {

    private final Participante participante;
    private final LocalDate fechaInscripcion;
    private static final int PUNTOS_PRIMER_DIA = 10;

    public Inscripcion(Participante participante, LocalDate fecha) {
        validarFecha(fecha);
        validarParticipante(participante);
        this.participante = participante;
        this.fechaInscripcion = fecha;
    }

    public void agregarPuntos(LocalDate fechaInicio) {
        if (this.fechaInscripcion.equals(fechaInicio)) {
            participante.sumarPuntos(PUNTOS_PRIMER_DIA);
        }
    }

    public Participante getParticipante() {
        return participante;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
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
}