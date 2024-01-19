package com.coppel.services.impl;

import com.coppel.dto.ArticuloDTO;
import com.coppel.entities.Articulo;
import com.coppel.exceptions.InternalException;
import com.coppel.exceptions.NotFoundException;
import com.coppel.mapper.ArticuloMapper;
import com.coppel.repositories.ArticuloRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticuloService {

    private static final Logger log = LoggerFactory.getLogger(ArticuloService.class);

    @Autowired
    private ArticuloRepository articuloRepository;

    public Page<ArticuloDTO> obtenerArticulos(){
        log.info("Buscando todos los articulos");
        List<Articulo> articulos = articuloRepository.findAll();

        List<ArticuloDTO> articulosDTO = articulos.stream().map(
                articulosLista -> ArticuloMapper.mapper.articuloToArticuloDto(articulosLista))
                .collect(Collectors.toList());

        return new PageImpl<>(articulosDTO);
    }

    public ArticuloDTO findArticuloById(Long id){
        log.info("Buscando articulo por id");
        Optional<Articulo> existeArticulo = articuloRepository.findById(id);
        ArticuloDTO articuloDTO = ArticuloMapper.mapper.optionalArticuloToArticuloDto(existeArticulo);
        if (!existeArticulo.isPresent()) {
            throw new NotFoundException("El articulo no se encontro en el catalogo");
        }
        log.info("Articulo encontrado por id");
        return articuloDTO;
    }

    public ArticuloDTO crearArticulo(ArticuloDTO articuloDTO) throws InternalException{
        log.info("Creando articulo desde capa servicio");
        Articulo articuloRepo = null;
        Articulo articuloMapear = ArticuloMapper.mapper.articuloDtoToArticulo(articuloDTO);
        try {
            articuloRepo = articuloRepository.save(articuloMapear);
        } catch (DataAccessException ex) {
            log.info("Error de inconsistencia de datos");
            throw new InternalException("Error de incosistencia de datos");
        } catch (Exception ex) {
            log.info("Ha ocurrido un error interno en el servidor");
            throw new InternalException("Ha ocurrido un error interno en el servidor");
        }

        ArticuloDTO articuloMapeado = ArticuloMapper.mapper.articuloToArticuloDto(articuloRepo);
        return articuloMapeado;
    }

    public void eliminarArticulo(Long id) throws Exception {
        try {
            log.info("Buscando articulo desde capa servicio");
            articuloRepository.deleteById(id);
        } catch (NotFoundException ex) {
            log.info("Ha ocurrido un NotFoundException");
            throw new NotFoundException("La poliza a eliminar no fue encontrada");
        } catch (DataAccessException ex) {
            log.info("Ha ocurrido un DataAccessException");
            throw new DataAccessException("Error en la conexion a la base de datos") {
            };
        } catch (Exception ex) {
            log.info("Ha ocurrido un Exception");
            throw new Exception("Ha ocurrido una excepcion");
        }
    }

    public ArticuloDTO modificarArticulo(@RequestBody ArticuloDTO articuloDTO) {
        log.info("Entrando a modificarArticulo en capa de servicio");
        try {
            Articulo articuloTemporal =
                    articuloRepository.save(ArticuloMapper.mapper.articuloDtoToArticulo(articuloDTO));

            ArticuloDTO articuloMapeado = ArticuloMapper.mapper.articuloToArticuloDto(articuloTemporal);
            return articuloMapeado;
        } catch (NotFoundException ex) {
            log.info("Ha ocurrido una DataAccessException");
            throw ex;
        } catch (Exception ex) {
            log.info("Ha ocurrido una Exception en capa de servicio");
            throw ex;
        }
    }
}
