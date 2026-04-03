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

    public boolean seGuardoInscripcion() {
        return !inscripcionesGuardadas.isEmpty();
    }
}
