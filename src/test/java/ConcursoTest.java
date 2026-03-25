import Entities.Concurso;
import Entities.Inscripcion;
import Entities.Participante;
import Exceptions.InscripcionFueraDeRangoException;
import Persistencia.RegistroInscripcion;
import Persistencia.RegistroInscripcionBD;
import Persistencia.connectionBD;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ConcursoTest {

    @Test
    void unParticipanteSeInscribeEnUnConcurso() {
        Connection conexion= connectionBD.obtenerConexion();
        RegistroInscripcion registro=new RegistroInscripcionBD(conexion);
        LocalDate inicio = LocalDate.of(2026, 3, 23);
        Concurso concurso = new Concurso("CON-1", inicio, inicio.plusDays(7), registro);
        Participante participante = new Participante("45015481", "Pedro");
        Inscripcion inscripcion = new Inscripcion(participante, inicio.plusDays(1));

        concurso.inscribir(inscripcion);

        assertTrue(concurso.estaInscripto(participante));
        assertEquals(0, participante.getPuntos());
    }

    @Test
    void unParticipanteSeInscribeElPrimerDiaYGanaDiezPuntos() {
        Connection conexion= connectionBD.obtenerConexion();
        RegistroInscripcion registro=new RegistroInscripcionBD(conexion);
        LocalDate inicio = LocalDate.of(2026, 3, 23);
        Concurso concurso = new Concurso("CON-2", inicio, inicio.plusDays(7), registro);
        Participante participante = new Participante("42023456", "Diego");
        Inscripcion inscripcion = new Inscripcion(participante, inicio);

        concurso.inscribir(inscripcion);

        assertTrue(concurso.estaInscripto(participante));
        assertTrue(concurso.esInscriptoPrimerDia(inscripcion));
        assertEquals(10, participante.getPuntos());
    }

    @Test
    void unParticipanteIntentaInscribirseFueraDelRangoDeInscripcion() {
        Connection conexion= connectionBD.obtenerConexion();
        RegistroInscripcion registro=new RegistroInscripcionBD(conexion);
        LocalDate inicio = LocalDate.of(2026, 3, 23);
        Concurso concurso = new Concurso("CON-3", inicio, inicio.plusDays(7), registro);
        Participante participante = new Participante("436789023", "Matias");
        Inscripcion inscripcion = new Inscripcion(participante, inicio.minusDays(1));

        InscripcionFueraDeRangoException e = assertThrows(
                InscripcionFueraDeRangoException.class,
                () -> concurso.inscribir(inscripcion)
        );

        assertEquals("La inscripción no se encuentra dentro del período permitido.", e.getMessage());
        assertFalse(concurso.estaInscripto(participante));
    }

}
