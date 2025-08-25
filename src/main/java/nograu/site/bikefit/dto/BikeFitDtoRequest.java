package nograu.site.bikefit.dto;

public record BikeFitDtoRequest(
    Double cavalo,
    Double esterno,
    Double braco,
    String email,
    Boolean usarIa
) {
    
}
