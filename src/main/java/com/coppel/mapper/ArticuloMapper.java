package com.coppel.mapper;

import com.coppel.dto.ArticuloDTO;
import com.coppel.entities.Articulo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface ArticuloMapper {

    ArticuloMapper mapper = Mappers.getMapper(ArticuloMapper.class);

    ArticuloDTO articuloToArticuloDto(Articulo articulo);

    Articulo articuloDtoToArticulo(ArticuloDTO articuloDTO);

    default ArticuloDTO optionalArticuloToArticuloDto(Optional<Articulo> optionalArticulo) {
        return optionalArticulo.map(this::articuloToArticuloDto).orElse(null);
    }

}
