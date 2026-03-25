package Persistencia;

import Entities.Inscripcion;

public class RegistroInscripcionFake implements RegistroInscripcion {
    private boolean invocado=false;
    @Override
    public void guardar(Inscripcion inscripcion, String idConcurso) {

    }
    public boolean fueInvocado() {
        return this.invocado=true;
    }
}
