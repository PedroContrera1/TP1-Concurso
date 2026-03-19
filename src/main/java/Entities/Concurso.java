package Entities;

import Exceptions.FechaInvalidaException;
import Exceptions.InscripcionFueraDeRangoException;
import Exceptions.InscripcionInvalidaException;
import Exceptions.NombreInvalidoException;
import Exceptions.ParticipanteDuplicadoException;
import Exceptions.ParticipanteInvalidoException;
import Exceptions.PeriodoInscripcionInvalidoException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Concurso {
    private final LocalDate fechaInscripcion;
    private final LocalDate fechaFin;
    private final String nombre;
    private final Set<Inscripcion> inscripciones;

    public Concurso(String nombre, LocalDate fechaInscripcion, LocalDate fechaFin) {
        validarNombre(nombre);
        validarFecha(fechaInscripcion);
        validarFecha(fechaFin);
        validarFechasInscripcion(fechaInscripcion, fechaFin);

        this.nombre = nombre;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaFin = fechaFin;
        this.inscripciones = new HashSet<>();
    }

    public void inscribir(Inscripcion inscripcion) {
        validarInscripcion(inscripcion);
        validarPeriodoInscripcion(inscripcion);

        if (inscripciones.contains(inscripcion)) {
            throw new ParticipanteDuplicadoException("El participante ya está inscripto en el concurso");
        }

        inscripciones.add(inscripcion);

        if (esInscriptoPrimerDia(inscripcion)) {
            inscripcion.agregarPuntos(fechaInscripcion);
        }
    }

    public boolean estaInscripto(Participante participante) {
        validarParticipante(participante);
        return inscripciones.stream()
                .anyMatch(inscripcion -> inscripcion.getParticipante().equals(participante));
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NombreInvalidoException("El nombre del concurso no puede ser nulo o vacío.");
        }
    }

    private void validarFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new FechaInvalidaException("La fecha no puede ser nula.");
        }
    }

    private void validarFechasInscripcion(LocalDate fechaInicio, LocalDate fechaFin) {
        if (!fechaInicio.isBefore(fechaFin)) {
            throw new PeriodoInscripcionInvalidoException(
                    "La fecha de inicio de inscripción debe ser anterior a la fecha de fin de inscripción."
            );
        }
    }

    private void validarPeriodoInscripcion(Inscripcion inscripcion) {
        if (inscripcion.getFechaInscripcion().isBefore(this.fechaInscripcion) ||
                inscripcion.getFechaInscripcion().isAfter(this.fechaFin)) {
            throw new InscripcionFueraDeRangoException(
                    "La inscripción no se encuentra dentro del período permitido."
            );
        }
    }

    private void validarInscripcion(Inscripcion inscripcion) {
        if (inscripcion == null) {
            throw new InscripcionInvalidaException("La inscripción no puede ser nula.");
        }
    }

    private void validarParticipante(Participante participante) {
        if (participante == null) {
            throw new ParticipanteInvalidoException("El participante no puede ser nulo.");
        }
    }

    public boolean esInscriptoPrimerDia(Inscripcion inscripcion) {
        validarInscripcion(inscripcion);
        return fechaInscripcion.equals(inscripcion.getFechaInscripcion());
    }
}