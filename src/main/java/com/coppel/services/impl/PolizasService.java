package com.coppel.services.impl;

import com.coppel.controllers.EmpleadoController;
import com.coppel.dto.PolizaDTO;
import com.coppel.dto.PolizaEmpleadoDTO;
import com.coppel.entities.Polizas;
import com.coppel.exceptions.IncorrectBodyException;
import com.coppel.exceptions.InternalException;
import com.coppel.exceptions.NotFoundException;
import com.coppel.mapper.PolizaMapper;
import com.coppel.repositories.PolizaEmpleadoDTORepository;
import com.coppel.repositories.PolizasRepository;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PolizasService {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private PolizasRepository polizaRepository;

    @Autowired
    private PolizaEmpleadoDTORepository dtoRepository;

    public PolizasService(PolizasRepository polizaRepository) {
        this.polizaRepository = polizaRepository;
    }

    public List<PolizaDTO> getAllPolizas() {
        List<Polizas> polizas = polizaRepository.findAll();

        List<PolizaDTO> polizasDto = polizas.stream().map(
                polizasLista -> PolizaMapper.mapper.polizaToPolizaDto(polizasLista)).collect(Collectors.toList());

        return polizasDto;
    }

    public List<PolizaEmpleadoDTO> getPolizasEmpleado() {
        return (List<PolizaEmpleadoDTO>) dtoRepository.listarPolizaEmpleado();
    }

    public PolizaEmpleadoDTO getPolizaEmpleadoById(int idPoliza) {
        return dtoRepository.listarPolizaEmpleadoPorId(idPoliza);
    }

    public PolizaDTO findPolizaById(@NonNull @Positive Long id) {
        log.info("Entrando a capa servicio findById");
        Optional<Polizas> encontrar = polizaRepository.findById(id);
        PolizaDTO polizaDTO = PolizaMapper.mapper.optionalPolizaToPolizaDto(encontrar);


        if (!encontrar.isPresent()) {
            throw new NotFoundException("La poliza a buscar no fue encontrada");
        }
        log.info("Poliza encontrada");
        return polizaDTO;
    }

    public PolizaDTO crearPoliza(PolizaDTO polizaDTO) throws InternalException {
        log.info("Creando poliza desde capa servicio");
        Polizas polizaRepo = null;
        try {
            polizaRepo = polizaRepository.insertarPoliza(
                    polizaDTO.getEmpleado(),
                    polizaDTO.getIdInventario(),
                    polizaDTO.getCantidad(),
                    Date.valueOf(polizaDTO.getFecha()));

            if (Date.valueOf(polizaDTO.getFecha()).after(Date.valueOf(LocalDate.now()))) {
                throw new IncorrectBodyException("Debe ingresar una fecha valida");
            }

        } catch (DataAccessException ex) {
            log.info("Error de inconsistencia de datos");
            throw new InternalException("Error de incosistencia de datos");
        } catch (Exception ex) {
            log.info("Ha ocurrido un error interno en el servidor");
            throw new InternalException("Ha ocurrido un error interno en el servidor");
        }

        PolizaDTO polizaMapeada = PolizaMapper.mapper.polizaToPolizaDto(polizaRepo);
        log.info("Poliza creada");
        return polizaMapeada;
    }


    public void eliminarPoliza(int id) throws Exception {

        try {
            log.info("Buscando poliza desde capa servicio");
            polizaRepository.borrarPoliza(id);
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

    public PolizaDTO modificarPoliza(@RequestBody PolizaDTO polizaDTO) throws Exception {
        log.info("Entrando a modificarPoliza en capa de servicio");
        try {
            Polizas polizaTemporal = polizaRepository.actualizarPoliza(polizaDTO.getId(),
                    polizaDTO.getEmpleado(), polizaDTO.getIdInventario(),
                    polizaDTO.getCantidad(), Date.valueOf(polizaDTO.getFecha()));

            if (Date.valueOf(polizaDTO.getFecha()).after(Date.valueOf(LocalDate.now()))) {
                throw new IncorrectBodyException("Debe ingresar una fecha valida");
            }

            PolizaDTO polizaMapeada = PolizaMapper.mapper.polizaToPolizaDto(polizaTemporal);
            log.info("Poliza modificada");
            return polizaMapeada;
        } catch (NotFoundException ex) {
            log.info("Ha ocurrido una DataAccessException");
            throw new NotFoundException("La poliza a modificar no fue encontrada");
        } catch (Exception ex) {
            log.info("Ha ocurrido una Exception en capa de servicio");
            throw new Exception();
        }
    }
}
