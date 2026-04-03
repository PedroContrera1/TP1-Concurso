package org.example;

import Entities.Concurso;
import Entities.Inscripcion;
import Entities.Participante;
import Persistencia.*;

import java.sql.Connection;
import java.time.LocalDate;

public class Main{
    public static void main(String[] args) {
        Connection conexion = connectionBD.obtenerConexion();

        RegistroInscripcion registro = new RegistroInscripcionBD(conexion);

        NotificadorInscripcion notificador = new NotificadorInscripcionMail(
                "sandbox.smtp.mailtrap.io",
                2525,
                "692498a3dff362",
                "7f757d0825d4b2",
                ""
        );

        Concurso concurso = new Concurso(
                "CON-1",
                LocalDate.of(2026, 3, 24),
                LocalDate.of(2026, 3, 30),
                registro,
                notificador
        );

        Participante participante = new Participante(
                "P-1",
                "Pedro",
                "pedro@example.com"
        );
        Inscripcion inscripcion = new Inscripcion(participante, LocalDate.of(2026, 3, 24));
        concurso.inscribir(inscripcion);

        System.out.println("Puntos del participante: " + participante.getPuntos());
    }
}
