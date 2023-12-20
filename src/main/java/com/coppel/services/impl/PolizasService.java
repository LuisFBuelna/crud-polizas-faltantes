package com.coppel.services.impl;

import com.coppel.controllers.EmpleadoController;
import com.coppel.entities.Polizas;
import com.coppel.exceptions.IncorrectBodyException;
import com.coppel.exceptions.NotFoundException;
import com.coppel.repositories.PolizasRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

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

    public Polizas create (Polizas poliza){
        Polizas polizaTemporal = polizaRepository.save(poliza);
        if (poliza == null) {
            throw new IncorrectBodyException("El articulo no se pudo registrar correctamente");
        }
        return polizaTemporal;
    }
    
    public Polizas save(Polizas poliza){
        log.info("Guardando poliza");
        Polizas temporal = polizaRepository.save(poliza);
        if (temporal == null) {
            throw new IncorrectBodyException("El articulo no se pudo registrar correctamente");
        }
        return temporal;
    }
    
    public List<Polizas> getAllPolizas(){
        return polizaRepository.findAll();
    }
    
    public Optional<Polizas> deletePoliza(Long id){
        log.info("Entrando a metodo deletePoliza de capa servicio");
        Optional<Polizas> existPoliza = polizaRepository.findById(id);
        if(existPoliza.isPresent()){
            log.info("Se encontro poliza con metodo findById, se procede a eliminar");
            polizaRepository.delete(existPoliza.get());
        } else {
            throw new NotFoundException("La poliza a eliminar no fue encontrada");
        }
        return existPoliza;
    }
    
    public Optional<Polizas> finPolizaById (Long id){
        log.info("Entrando a capa servicio findById");
        Optional<Polizas> encontrar = polizaRepository.findById(id);
        if (encontrar.isPresent()){
            log.info("Poliza encontrada");
            return polizaRepository.findById(id);
        } else {
            throw new NotFoundException("La poliza a buscar no fue encontrada");
        }
    }

    public Polizas crearPoliza(Polizas poliza){
        log.info("Creando poliza desde capa servicio");
        Date fechaDate = Date.valueOf(poliza.getFecha());

        Polizas polizaTemporal =
                polizaRepository.insertarPoliza(
                poliza.getEmpleadoGenero(),
                poliza.getSku(),
                poliza.getCantidad(),
                fechaDate);
        if (polizaTemporal != null) {
            log.info("Poliza creada");
            return polizaTemporal;
        } else {
            log.info("Ha ocurrido un error desde la capa de servicio");
            throw new IncorrectBodyException("La poliza no se pudo registrar correctamente");
        }
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
    
    public Polizas modificarPoliza(@RequestBody Polizas poliza){
        log.info("Entrando a modificarPoliza en capa de servicio");
        Date fechaDate = Date.valueOf(poliza.getFecha());
        try{
            Polizas polizaTemporal = polizaRepository.actualizarPoliza(poliza.getId(),
                    poliza.getEmpleadoGenero(), poliza.getSku(),
                    poliza.getCantidad(), fechaDate);

            log.info("Poliza modificada");
            return polizaTemporal;
        } catch (Exception ex) {
            log.info("Ha ocurrido una Exception en capa de servicio");
            throw ex;
        }
    }    
    
    public Optional<Polizas> findPolizaById (int id){
        return polizaRepository.findPolizaById(id);
    }
    
    @QueryMapping
    public List<Polizas> allPolizas(){

        return polizaRepository.findAll();
    }
}
