package Persistencia;

import Entities.Participante;

public interface NotificadorInscripcion {

    void enviarConfirmacion (Participante participante,String idConcurso);

}
