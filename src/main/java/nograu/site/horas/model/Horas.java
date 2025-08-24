package nograu.site.horas.model;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Horas {

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "{horas.NotNull.message}")
    private LocalTime horaEntrada;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "{horas.NotNull.message}")
    private LocalTime horaAlmoco;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "{horas.NotNull.message}")
    private LocalTime horaRetornoAlmoco;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "{horas.NotNull.message}")
    private LocalTime cargaHoraria;
}
