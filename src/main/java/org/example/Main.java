package org.example;

import Entities.Concurso;
import Entities.Inscripcion;
import Entities.Participante;
import Persistencia.RegistroInscripcionEnArchivo;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        RegistroInscripcionEnArchivo registro = new RegistroInscripcionEnArchivo("inscripciones.txt");

        LocalDate inicio = LocalDate.of(2026, 4, 1);
        Concurso concurso = new Concurso(
                "CON-1",
                inicio,
                inicio.plusDays(7),
                registro
        );

        Participante participante = new Participante(
                "12345678",
                "Juan"
        );

        Inscripcion inscripcion = new Inscripcion(
                participante,
                inicio.plusDays(1)
        );

        concurso.inscribir(inscripcion);

        System.out.println("Inscripción realizada correctamente.");
    }
}