package com.coppel.mapper;

import com.coppel.dto.PolizaDTO;
import com.coppel.entities.Polizas;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface PolizaMapper {

    PolizaMapper mapper = Mappers.getMapper(PolizaMapper.class);

    @Mapping(source = "empleadoGenero", target = "empleado")
    PolizaDTO polizaToPolizaDto(Polizas polizas);

    @Mapping(source = "empleado", target = "empleadoGenero")
    Polizas  polizaDtoToPoliza(PolizaDTO polizaDTO);

    default PolizaDTO optionalPolizaToPolizaDto(Optional<Polizas> optionalPolizas) {
        return optionalPolizas.map(this::polizaToPolizaDto).orElse(null);
    }
}
