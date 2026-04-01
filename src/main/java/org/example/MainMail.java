package org.example;

import Entities.Concurso;
import Entities.Inscripcion;
import Entities.Participante;
import Persistencia.*;

import java.sql.Connection;
import java.time.LocalDate;

public class MainMail {
    public static void main(String[] args) {
        Connection conexion = ConexionBD.obtenerConexion();

        RegistroInscripcion registro = new RegistroInscripcionBD(conexion);

        NotificadorInscripcion notificador = new NotificadorInscripcionMail(
                "sandbox.smtp.mailtrap.io",
                2525,
                "TU_USERNAME_MAILTRAP",
                "TU_PASSWORD_MAILTRAP",
                "no-reply@mitp.com"
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

        concurso.inscribir(participante, LocalDate.of(2026, 3, 24));

        System.out.println("Inscripción registrada y email enviado a Mailtrap.");
}
