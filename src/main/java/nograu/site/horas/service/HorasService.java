package nograu.site.horas.service;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import nograu.site.horas.model.Horas;

@Service
public class HorasService {
public LocalTime calculaSaida(Horas horas) {
        // Calcula quanto tempo trabalhou até o almoço (em Duration)
        Duration tempoTrabalhadoManha = Duration.between(
            horas.getHoraEntrada(), 
            horas.getHoraAlmoco()
        );
        
        // Converte a carga horária de LocalTime para Duration
        Duration cargaHorariaTotal = Duration.ofHours(horas.getCargaHoraria().getHour())
            .plusMinutes(horas.getCargaHoraria().getMinute());
        
        // Calcula quanto tempo ainda precisa trabalhar
        Duration tempoFaltante = cargaHorariaTotal.minus(tempoTrabalhadoManha);
        
        // Calcula a hora de saída somando o tempo faltante ao retorno do almoço
        LocalTime horaSaida = horas.getHoraRetornoAlmoco().plus(tempoFaltante);
        
        return horaSaida;
    }
}
