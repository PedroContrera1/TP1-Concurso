import Entities.Inscripcion;
import Persistencia.RegistroInscripcion;

import java.util.ArrayList;
import java.util.List;

public class RegistroInscripcionFake implements RegistroInscripcion {

    private final List<Inscripcion> inscripcionesGuardadas = new ArrayList<>();
    private final List<String> concursosGuardados = new ArrayList<>();

    @Override
    public void guardar(Inscripcion inscripcion, String idConcurso) {
        inscripcionesGuardadas.add(inscripcion);
        concursosGuardados.add(idConcurso);
    }


    public boolean fueInvocado() {
        return true;
    }

    public int cantidadGuardados() {
        return inscripcionesGuardadas.size();
    }

    public Inscripcion ultimaInscripcion() {
        return inscripcionesGuardadas.get(inscripcionesGuardadas.size() - 1);
    }

    public String ultimoIdConcurso() {
        return concursosGuardados.get(concursosGuardados.size() - 1);
    }

    public boolean seGuardoInscripcion() {
        return !inscripcionesGuardadas.isEmpty();
    }
}
