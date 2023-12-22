package com.coppel.services.impl;

import com.coppel.controllers.EmpleadoController;
import com.coppel.entities.Polizas;
import com.coppel.exceptions.IncorrectBodyException;
import com.coppel.exceptions.InternalException;
import com.coppel.exceptions.NotFoundException;
import com.coppel.exceptions.PolizasTimeOutException;
import com.coppel.repositories.PolizasRepository;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PolizasService {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private PolizasRepository polizaRepository;

    public PolizasService(PolizasRepository polizaRepository) {
        this.polizaRepository = polizaRepository;
    }

    public List<Polizas> getAllPolizas() {
        return polizaRepository.findAll();
    }

    public Optional<Polizas> deletePoliza(Long id) {
        log.info("Entrando a metodo deletePoliza de capa servicio");
        Optional<Polizas> existPoliza = polizaRepository.findById(id);
        if (existPoliza.isPresent()) {
            log.info("Se encontro poliza con metodo findById, se procede a eliminar");
            polizaRepository.delete(existPoliza.get());
        } else {
            throw new NotFoundException("La poliza a eliminar no fue encontrada");
        }
        return existPoliza;
    }

    public Optional<Polizas> finPolizaById(@NonNull @Positive Long id) {
        log.info("Entrando a capa servicio findById");
        Optional<Polizas> encontrar = polizaRepository.findById(id);
        if (!encontrar.isPresent()) {
            throw new NotFoundException("La poliza a buscar no fue encontrada");
        }
        log.info("Poliza encontrada");
        return polizaRepository.findById(id);
    }

    public Polizas crearPoliza(Polizas poliza) throws InternalException {
        Polizas polizaTemporal;
        log.info("Creando poliza desde capa servicio");
        if (contieneValorCero(poliza)) {
            log.info("La poliza no se pudo registrar correctamente");
            throw new IncorrectBodyException("La poliza no se pudo registrar correctamente");
        }
        try {
            polizaTemporal =
                    polizaRepository.insertarPoliza(
                            poliza.getEmpleadoGenero(),
                            poliza.getSku(),
                            poliza.getCantidad(),
                            Date.valueOf(poliza.getFecha()));
        } catch (DataAccessException ex) {
            log.info("Error de inconsistencia de datos");
            throw new InternalException("Error de incosistencia de datos");
        } catch (Exception ex) {
            log.info("Ha ocurrido un error interno en el servidor");
            throw new InternalException("Ha ocurrido un error interno en el servidor");
        }


        log.info("Poliza creada");
        return polizaTemporal;
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

    public Polizas modificarPoliza(@RequestBody Polizas poliza) {
        log.info("Entrando a modificarPoliza en capa de servicio");
        Date fechaDate = Date.valueOf(poliza.getFecha());
        try {
            Polizas polizaTemporal = polizaRepository.actualizarPoliza(poliza.getId(),
                    poliza.getEmpleadoGenero(), poliza.getSku(),
                    poliza.getCantidad(), fechaDate);

            log.info("Poliza modificada");
            return polizaTemporal;
        } catch (NotFoundException ex) {
            log.info("Ha ocurrido una DataAccessException");
            throw ex;
        } catch (Exception ex) {
            log.info("Ha ocurrido una Exception en capa de servicio");
            throw ex;
        }
    }

    @QueryMapping
    public List<Polizas> allPolizas() {

        return polizaRepository.findAll();
    }

    public boolean contieneValorCero(Polizas poliza) {
        if (poliza.getEmpleadoGenero() == 0 || poliza.getCantidad() == 0
                || poliza.getFecha() == null || poliza.getSku() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
