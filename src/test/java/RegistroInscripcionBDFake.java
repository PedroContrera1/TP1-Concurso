import Entities.Inscripcion;
import Persistencia.RegistroInscripcion;

public class RegistroInscripcionBDFake implements RegistroInscripcion {

    private boolean invocado;

    public RegistroInscripcionBDFake() {
        this.invocado = false;
    }

    @Override
    public void guardar(Inscripcion inscripcion, String idConcurso) {
        this.invocado = true;

    }

    public boolean fueInvocado() {
        return invocado;
    }

}