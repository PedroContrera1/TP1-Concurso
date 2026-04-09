package Persistencia;

import Entities.Inscripcion;
import Exceptions.ParticipanteDuplicadoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RegistroInscripcionEnArchivo implements RegistroInscripcion {

    private final String rutaArchivo;

    public RegistroInscripcionEnArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public void guardar(Inscripcion inscripcion, String idConcurso) {
        String idParticipante = inscripcion.getParticipante().getId();

        if (yaExisteInscripcion(idParticipante, idConcurso)) {
            throw new ParticipanteDuplicadoException(
                    "El participante ya está inscripto en el concurso en el archivo."
            );
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo, true))) {
            writer.println(
                    inscripcion.getFechaInscripcion() + ";" +
                            idParticipante + ";" +
                            idConcurso
            );
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar la inscripción en el archivo.", e);
        }
    }

    private boolean yaExisteInscripcion(String idParticipante, String idConcurso) {
        File archivo = new File(rutaArchivo);

        if (!archivo.exists()) {
            return false;
        }

        try {
            List<String> lineas = Files.readAllLines(Path.of(rutaArchivo));

            for (String linea : lineas) {
                String[] partes = linea.split(";");
                if (partes.length >= 3) {
                    String participanteGuardado = partes[1];
                    String concursoGuardado = partes[2];

                    if (participanteGuardado.equals(idParticipante) &&
                            concursoGuardado.equals(idConcurso)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el archivo de inscripciones.", e);
        }

        return false;
    }
}