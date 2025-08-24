package nograu.site.pcd.dto;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record EmprestimoResponse(
        LocalDate proximaParcela,
        LocalDate ultimaParcela,
        Integer quantidadeParcelas,
        Double valorParcela,
        Double valorEmprestado,
        Double taxaJuros,
        Integer mesesEmSer,
        Double saldoDevedor,
        BancoResponse banco,
        LocalDate createdAt) {

}
