package nograu.site.horas.service;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

import nograu.site.horas.model.Horas;

@Service
public class HorasService {
    public LocalTime calculaSaida(Horas horas) {
        var horasTrabalhadas = horas.getHoraAlmoco().minusHours(horas.getHoraEntrada().getHour()).minusMinutes(horas.getHoraEntrada().getMinute());
        var horasFaltantes = horas.getCargaHoraria().minusHours(horasTrabalhadas.getHour()).minusMinutes(horasTrabalhadas.getMinute());
        var horaSaida = horas.getHoraRetornoAlmoco().plusHours(horasFaltantes.getHour()).plusMinutes(horasFaltantes.getMinute());
        return horaSaida;
    }
}
