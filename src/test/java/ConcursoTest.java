import Entities.Concurso;
import Entities.Inscripcion;
import Entities.Participante;
import Exceptions.InscripcionFueraDeRangoException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ConcursoTest {
    RegistroInscripcionFake registroFake= new RegistroInscripcionFake();
    NotificadorInscripcionFake notificadorFake=new NotificadorInscripcionFake();
    @Test
    void unParticipanteSeInscribeEnUnConcurso() {
        RegistroInscripcionFake registroFake = new RegistroInscripcionFake();
        NotificadorInscripcionFake notificadorFake = new NotificadorInscripcionFake();

        LocalDate inicio = LocalDate.of(2026, 3, 23);
        Concurso concurso = new Concurso("CON-1", inicio, inicio.plusDays(7), registroFake, notificadorFake);
        Participante participante = new Participante("45015481", "Pedro", "pedro@mail.com");
        Inscripcion inscripcion = new Inscripcion(participante, inicio.plusDays(1));

        concurso.inscribir(inscripcion);

        assertTrue(concurso.estaInscripto(participante));
        assertEquals(0, participante.getPuntos());
        assertEquals(1, notificadorFake.cantidadEnviados());
        assertEquals("CON-1", notificadorFake.ultimoConcursoNotificado());
        assertTrue(registroFake.fueInvocado());
    }

    @Test
    void unParticipanteSeInscribeElPrimerDiaYGanaDiezPuntos() {
        RegistroInscripcionFake registroFake = new RegistroInscripcionFake();
        NotificadorInscripcionFake notificadorFake = new NotificadorInscripcionFake();

        LocalDate inicio = LocalDate.of(2026, 3, 23);
        Concurso concurso = new Concurso("CON-2", inicio, inicio.plusDays(7), registroFake, notificadorFake);
        Participante participante = new Participante("42023456", "Diego", "diego@gmail.com");
        Inscripcion inscripcion = new Inscripcion(participante, inicio);

        concurso.inscribir(inscripcion);

        assertTrue(concurso.estaInscripto(participante));
        assertTrue(concurso.esInscriptoPrimerDia(inscripcion));
        assertEquals(10, participante.getPuntos());
        assertEquals(1, notificadorFake.cantidadEnviados());
        assertEquals("CON-2", notificadorFake.ultimoConcursoNotificado());
        assertTrue(registroFake.fueInvocado());
    }

    @Test
    void unParticipanteIntentaInscribirseFueraDelRangoDeInscripcion() {
        LocalDate inicio = LocalDate.of(2026, 3, 23);
        Concurso concurso = new Concurso("CON-3", inicio, inicio.plusDays(7), registroFake, notificadorFake);
        Participante participante = new Participante("436789023", "Matias", "matias@gmail.com");
        Inscripcion inscripcion = new Inscripcion(participante, inicio.minusDays(1));

        InscripcionFueraDeRangoException e = assertThrows(
                InscripcionFueraDeRangoException.class,
                () -> concurso.inscribir(inscripcion)
        );

        assertEquals("La inscripción no se encuentra dentro del período permitido.", e.getMessage());
        assertFalse(concurso.estaInscripto(participante));
        assertFalse(registroFake.seGuardoInscripcion());
        assertFalse(notificadorFake.seEnvioMail());
    }

}
