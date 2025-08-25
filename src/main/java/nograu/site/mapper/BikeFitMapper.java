package nograu.site.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import nograu.site.bikefit.dto.BikeFitDtoRequest;
import nograu.site.bikefit.model.BikeFit;

@Mapper(componentModel = "spring")
public interface BikeFitMapper {

    BikeFitMapper INSTANCE = Mappers.getMapper(BikeFitMapper.class);

    @Mapping(target = "alturaSelim", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quadroMtb", ignore = true)
    @Mapping(target = "quadroSpeed", ignore = true)
    @Mapping(target = "topTubeEfetivo", ignore = true)
    @Mapping(target = "tronco", ignore = true)
    BikeFit toBikefit(BikeFitDtoRequest bikeFitDtoRequest);
    
}