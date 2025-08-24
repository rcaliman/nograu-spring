package nograu.site.pcd.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import nograu.site.annotation.CentsToDouble;

public record EmprestimoRequest(

        String codigoBanco,

        @DateTimeFormat(pattern = "ddMMyyyy")
        LocalDate proximaParcela,

        @DateTimeFormat(pattern = "ddMMyyyy")
        LocalDate ultimaParcela,

        Integer quantidadeParcelas,

        @CentsToDouble
        Double valorParcela,

        @CentsToDouble
        Double valorEmprestado

    ) {
    
}
