package Persistencia;

import Entities.Inscripcion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroInscripcionBD implements RegistroInscripcion {

    private final Connection conexion;

    public RegistroInscripcionBD(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void guardar(Inscripcion inscripcion, String idConcurso) {
        String sql = "INSERT INTO registro_inscripciones (fecha, id_participante, id_concurso) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(inscripcion.getFechaInscripcion()));
            ps.setString(2, inscripcion.getParticipante().getId());
            ps.setString(3, idConcurso);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Error 1062: Duplicate entry (UNIQUE constraint violation)
            if (e.getErrorCode() == 1062) {
                throw new Exceptions.ParticipanteDuplicadoException(
                        "El participante ya está inscripto en el concurso en la base de datos.");
            }
            throw new RuntimeException("No se pudo guardar la inscripción en la base de datos", e);
        }
    }

    public boolean fueInvocado() {
        return false;
    }

}