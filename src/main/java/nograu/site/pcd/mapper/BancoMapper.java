package nograu.site.pcd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import nograu.site.pcd.dto.BancoResponse;
import nograu.site.pcd.model.Banco;

@Mapper(componentModel = "spring")
public interface BancoMapper {
    BancoMapper INSTANCE = Mappers.getMapper(BancoMapper.class);

    BancoResponse toBancoResponse(Banco banco);
}
