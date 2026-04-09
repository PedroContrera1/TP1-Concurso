import Entities.Inscripcion;
import Persistencia.RegistroInscripcion;

public class RegistroInscripcionArchivoFake implements RegistroInscripcion {

    private boolean invocado;

    public RegistroInscripcionArchivoFake() {
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