package org.example;

import Entities.Concurso;
import Entities.Inscripcion;
import Entities.Participante;
import Persistencia.connectionBD;
import Persistencia.RegistroInscripcion;
import Persistencia.RegistroInscripcionBD;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Connection conexion = connectionBD.obtenerConexion();

        RegistroInscripcion registro = new RegistroInscripcionBD(conexion);

        Concurso concurso = new Concurso(
                "CON-1",
                LocalDate.of(2026, 3, 24),
                LocalDate.of(2026, 3, 30),
                registro
        );

        LocalDate inicio = LocalDate.of(2026, 3, 24);
        Participante participante = new Participante("45015481", "Pedro");
        Inscripcion inscripcion = new Inscripcion(participante, inicio);

        concurso.inscribir(inscripcion);

        System.out.println("Inscripción guardada.");
        System.out.println("Puntos: " + participante.getPuntos());
    }
}
