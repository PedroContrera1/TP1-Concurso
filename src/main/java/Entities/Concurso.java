package Entities;

import Exceptions.*;
import Persistencia.NotificadorInscripcion;
import Persistencia.RegistroInscripcion;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Concurso {
    private final String idConcurso;
    private final LocalDate fechaInicioInscripcion;
    private final LocalDate fechaFinInscripcion;
    private final Set<Inscripcion> inscripciones;
    private final RegistroInscripcion registroInscripcion;
    private final NotificadorInscripcion notificador;

    public Concurso(String idConcurso, LocalDate fechaInicioInscripcion, LocalDate fechaFinInscripcion,
                    RegistroInscripcion registroInscripcion, NotificadorInscripcion notificador) {
        validarNombre(idConcurso);
        validarFecha(fechaInicioInscripcion);
        validarFecha(fechaFinInscripcion);
        validarRegistro(registroInscripcion);
        validarPeriodo(fechaInicioInscripcion, fechaFinInscripcion);

        this.idConcurso = idConcurso;
        this.fechaInicioInscripcion = fechaInicioInscripcion;
        this.fechaFinInscripcion = fechaFinInscripcion;
        this.registroInscripcion = registroInscripcion;
        this.notificador=notificador;
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
        registroInscripcion.guardar(inscripcion, idConcurso);
        notificador.enviarConfirmacion(inscripcion.getParticipante(),getIdConcurso());
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

    public String getIdConcurso() {
        return idConcurso;
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
