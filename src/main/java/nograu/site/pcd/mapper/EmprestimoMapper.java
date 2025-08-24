package nograu.site.pcd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {
    EmprestimoMapper INSTANCE = Mappers.getMapper(EmprestimoMapper.class);

}
