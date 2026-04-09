package Persistencia;

import Entities.Inscripcion;

public interface RegistroInscripcion {
    void guardar(Inscripcion inscripcion, String idConcurso);
}
