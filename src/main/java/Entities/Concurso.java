package Entities;

import Exceptions.FechaInvalidaException;
import Exceptions.InscripcionFueraDeRangoException;
import Exceptions.InscripcionInvalidaException;
import Exceptions.NombreInvalidoException;
import Exceptions.ParticipanteDuplicadoException;
import Exceptions.ParticipanteInvalidoException;
import Exceptions.PeriodoInscripcionInvalidoException;
import Persistencia.RegistroInscripcion;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Concurso {
    private final String id;
    private final LocalDate fechaInicioInscripcion;
    private final LocalDate fechaFinInscripcion;
    private final Set<Inscripcion> inscripciones;
    private final RegistroInscripcion registroInscripcion;

    public Concurso(String id, LocalDate fechaInicioInscripcion, LocalDate fechaFinInscripcion,
                    RegistroInscripcion registroInscripcion) {
        validarNombre(id);
        validarFecha(fechaInicioInscripcion);
        validarFecha(fechaFinInscripcion);
        validarRegistro(registroInscripcion);
        validarPeriodo(fechaInicioInscripcion, fechaFinInscripcion);

        this.id = id;
        this.fechaInicioInscripcion = fechaInicioInscripcion;
        this.fechaFinInscripcion = fechaFinInscripcion;
        this.registroInscripcion = registroInscripcion;
        this.inscripciones = new HashSet<>();
    }

    public void inscribir(Inscripcion inscripcion) {
        validarInscripcion(inscripcion);
        validarPeriodoInscripcion(inscripcion);

        if (inscripciones.contains(inscripcion)) {
            throw new ParticipanteDuplicadoException("El participante ya está inscripto en el concurso");
        }

        inscripciones.add(inscripcion);
        inscripcion.otorgarPuntosSiCorresponde(fechaInicioInscripcion);
        registroInscripcion.guardar(inscripcion, id);
    }

    public boolean estaInscripto(Participante participante) {
        validarParticipante(participante);
        return inscripciones.stream()
                .anyMatch(inscripcion -> inscripcion.getParticipante().equals(participante));
    }

    public boolean esInscriptoPrimerDia(Inscripcion inscripcion) {
        validarInscripcion(inscripcion);
        return fechaInicioInscripcion.equals(inscripcion.getFechaInscripcion());
    }

    public String getId() {
        return id;
    }

    private void validarRegistro(RegistroInscripcion registroInscripcion) {
        if (registroInscripcion == null) {
            throw new InscripcionInvalidaException("El registro de inscripciones no puede ser nulo.");
        }
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

    private void validarPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        if (!fechaInicio.isBefore(fechaFin)) {
            throw new PeriodoInscripcionInvalidoException(
                    "La fecha de inicio de inscripción debe ser anterior a la fecha de fin de inscripción."
            );
        }
    }

    private void validarPeriodoInscripcion(Inscripcion inscripcion) {
        LocalDate fecha = inscripcion.getFechaInscripcion();
        if (fecha.isBefore(fechaInicioInscripcion) || fecha.isAfter(fechaFinInscripcion)) {
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
}
