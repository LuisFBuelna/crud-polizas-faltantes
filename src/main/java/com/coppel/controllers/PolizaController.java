package com.coppel.controllers;

import com.coppel.entities.Polizas;
import com.coppel.exceptions.IncorrectBodyException;
import com.coppel.exceptions.NotFoundException;
import com.coppel.services.impl.PolizasService;
import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/polizas")
@RestController
public class PolizaController {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private PolizasService polizaService;
    
    @GetMapping
    public ResponseEntity <List<Polizas>> listarTodasLasPolizas(){
        log.info("Obteniendo lista de polizas");
        return ResponseEntity.ok(polizaService.getAllPolizas());
    }
    
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Polizas> guardarPoliza(@RequestBody Polizas poliza){
        Polizas temporal = polizaService.save(poliza);
        try{
            return ResponseEntity.ok(temporal);
        }catch(IncorrectBodyException ex){
            throw ex;
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity <Optional<Polizas>> eliminarPoliza(@PathVariable Long id){
        log.info("Entrando a endpoint eliminarPoliza con JPA");
        Optional<Polizas> eliminar = polizaService.deletePoliza(id);
        try {
            log.info("Poliza eliminada");
            return ResponseEntity.ok(eliminar);
        } catch (NotFoundException ex) {
            log.info("Ha ocurrido un NotFoundException en eliminarPoliza");
            throw ex;
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity <Optional<Polizas>> listarPolizaPorId(@PathVariable("id") Long id){
        log.info("Entrando a endpoint listarPolizaPorId");
        Optional<Polizas> encontrar = polizaService.finPolizaById(id);
        try {
            log.info("Retornando poliza encontrada por id");
            return ResponseEntity.ok(polizaService.finPolizaById(id));
        } catch (NotFoundException ex) {
            throw ex;
        }
    }
    
    @PostMapping("/insertarPoliza")
    public ResponseEntity<Polizas> insertarPoliza(@RequestBody Polizas poliza) {
        log.info("Entrando a endpoint insertarPoliza");
        try {
            Polizas polizaTemporal = polizaService.crearPoliza(poliza);
            log.info("Retornando la poliza creada");
            return ResponseEntity.status(HttpStatus.OK).body(polizaTemporal);
        } catch (IncorrectBodyException ex) {
            log.info("Ha ocurrido un IncorrectBodyException");
            throw ex;
        }
          catch(Exception ex) {
            log.info("Ha ocurrido una Exception");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePoliza(@PathVariable int id) throws Exception {
        log.info("Entrando a endpoint deletePoliza");
        try{
            polizaService.eliminarPoliza(id);
        } catch (ConverterNotFoundException ex) {
            log.info("Ha ocurrido una ConverterNotFoundException");
            throw ex;
        } catch (NotFoundException ex) {
            log.info("Ha ocurrido una NotFoundException");
            throw  ex;
        } catch (DataAccessException ex) {
            log.info("Ha ocurrido una DataAccessException");
            throw ex;
        } catch (Exception ex) {
            log.info("Ha ocurrido una Exception");
            return (ResponseEntity<HttpStatus>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Poliza eliminada correctamente");
        return ResponseEntity.status(HttpStatus.OK).build();
    } 
    
    @PutMapping("/update")
    public ResponseEntity<Polizas> updatePoliza(@RequestBody Polizas poliza){
        log.info("Entrando a endpoint updatePoliza");
        try {
            polizaService.modificarPoliza(poliza);
        } catch (NotFoundException ex) {
            log.info("Ha ocurrido una DataAccessException");
            throw ex;
        } catch (Exception ex) {
            log.info("Ha ocurrido una Exception");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        log.info("Retornando poliza modificada");
        return ResponseEntity.status(HttpStatus.OK).body(poliza);
    }
    
}
