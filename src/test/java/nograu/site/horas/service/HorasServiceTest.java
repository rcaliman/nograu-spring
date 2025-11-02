package nograu.site.horas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nograu.site.horas.model.Horas;

class HorasServiceTest {

    private HorasService horasService;

    @BeforeEach
    void setUp() {
        horasService = new HorasService();
    }

    @Test
    void testCalculaSaida_CenarioNormal() {
        Horas horas = new Horas();
        horas.setHoraEntrada(LocalTime.of(8, 0));
        horas.setHoraAlmoco(LocalTime.of(12, 0));
        horas.setHoraRetornoAlmoco(LocalTime.of(13, 0));
        horas.setCargaHoraria(LocalTime.of(8, 0));

        LocalTime resultado = horasService.calculaSaida(horas);
        assertEquals(LocalTime.of(17, 0), resultado);
    }

    @Test
    void testCalculaSaida_CenarioComMinutos() {
        Horas horas = new Horas();
        horas.setHoraEntrada(LocalTime.of(9, 15));
        horas.setHoraAlmoco(LocalTime.of(12, 17));
        horas.setHoraRetornoAlmoco(LocalTime.of(13, 34));
        horas.setCargaHoraria(LocalTime.of(8, 0));

        LocalTime resultado = horasService.calculaSaida(horas);
        assertEquals(LocalTime.of(18, 32), resultado);
    }

    @Test
    void testCalculaSaida_CargaHorariaMenor() {
        Horas horas = new Horas();
        horas.setHoraEntrada(LocalTime.of(10, 0));
        horas.setHoraAlmoco(LocalTime.of(12, 0));
        horas.setHoraRetornoAlmoco(LocalTime.of(13, 0));
        horas.setCargaHoraria(LocalTime.of(6, 0));

        LocalTime resultado = horasService.calculaSaida(horas);
        assertEquals(LocalTime.of(17, 0), resultado);
    }
}