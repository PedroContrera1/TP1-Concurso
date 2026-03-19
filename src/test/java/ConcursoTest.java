import Entities.Concurso;
import Entities.Inscripcion;
import Entities.Participante;
import Exceptions.InscripcionFueraDeRangoException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ConcursoTest {

    @Test
    public void unParticipanteSeInscribeEnUnConcurso() {
        LocalDate fechaInscripcion = LocalDate.now();
        LocalDate fechaFin = fechaInscripcion.plusWeeks(1);

        Concurso concurso = new Concurso("Concurso de Programacion", fechaInscripcion, fechaFin);
        Participante participante = new Participante("45015481","Pedro");
        Inscripcion inscripcion = new Inscripcion(participante, fechaInscripcion.plusDays(1));

        concurso.inscribir(inscripcion);

        assertTrue(concurso.estaInscripto(participante));
        assertEquals(0, participante.getPuntos());
    }

    @Test
    public void unParticipanteSeInscribeElPrimerDiaYGanaDiezPuntos() {
        LocalDate fechaInscripcion = LocalDate.now();
        LocalDate fechaFin = fechaInscripcion.plusWeeks(1);

        Concurso concurso = new Concurso("Concurso de Salto", fechaInscripcion, fechaFin);
        Participante participante = new Participante("42023456","Diego");
        Inscripcion inscripcion = new Inscripcion(participante, fechaInscripcion);

        concurso.inscribir(inscripcion);

        assertTrue(concurso.estaInscripto(participante));
        assertEquals(10, participante.getPuntos());
    }

    @Test
    public void unParticipanteIntentaInscribirseFueraDelRangoDeInscripcion() {
        LocalDate fechaInscripcion = LocalDate.now();
        LocalDate fechaFin = fechaInscripcion.plusWeeks(1);

        Concurso concurso = new Concurso("Concurso de Matematicas", fechaInscripcion, fechaFin);
        Participante participante = new Participante("436789023","Matias");
        Inscripcion inscripcion = new Inscripcion(participante, fechaInscripcion.minusDays(1));

        InscripcionFueraDeRangoException e = assertThrows(
                InscripcionFueraDeRangoException.class,
                () -> concurso.inscribir(inscripcion)
        );

        assertEquals("La inscripción no se encuentra dentro del período permitido.", e.getMessage());
        assertFalse(concurso.estaInscripto(participante));

    }

}
