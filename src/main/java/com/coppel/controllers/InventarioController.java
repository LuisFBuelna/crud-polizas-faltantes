package com.coppel.controllers;

import com.coppel.entities.Inventario;
import com.coppel.exceptions.IncorrectBodyException;
import com.coppel.exceptions.NotFoundException;
import com.coppel.services.impl.InventarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/inventario")
@RestController
public class InventarioController {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public ResponseEntity<List<Inventario>> listarTodoElInventario() {
        log.info("Buscando lista de inventario");
        return ResponseEntity.ok(inventarioService.getAllInventario());
    }

    @PostMapping("/insertarArticulo")
    public ResponseEntity<Inventario> insertarArticulo(@Valid @RequestBody Inventario inventario) {
        log.info("Entrando a endpoint insertarArticulo");
        Inventario temporal = inventarioService.save(inventario);
        try {
            log.info("Retornando articulo insertado");
            return new ResponseEntity(temporal, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            log.info("Ha ocurrido un IllegalArgumentException");
            throw ex;
        } catch (IncorrectBodyException ex) {
            log.info("Ha ocurrido un IncorrectBodyException");
            throw ex;
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Inventario> guardarArticulo(@PathVariable Long id, @RequestBody Inventario inventario) {
        log.info("Entrando a endpoint actualizar articulo");
        Optional<Inventario> temporal = inventarioService.finInventarioById(id);
        if (temporal.isPresent()) {
            try {
                Inventario inventarioTemporal = temporal.get();
                inventarioTemporal.setCantidad(inventario.getCantidad());
                inventarioTemporal.setIdArticulo(inventario.getIdArticulo());
                Inventario updatedInventario = inventarioService.save(inventarioTemporal);
                log.info("Retornando articulo modificado");
                return ResponseEntity.ok(updatedInventario);
            } catch (IllegalArgumentException ex) {
                log.info("Ha ocurrido un IllegalArgumentException");
                throw ex;
            } catch (IncorrectBodyException ex) {
                log.info("Ha ocurrido un IncorrectBodyException");
                throw ex;
            } catch (Exception e) {
                log.info("Ha ocurrido un Exception");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        return (ResponseEntity<Inventario>) ResponseEntity.status(HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity eliminarArticulo(@PathVariable Long id) {
        log.info("Entrando a endpoint eliminarArticulo");
        try {
            inventarioService.deleteInventario(id);
        } catch (NotFoundException ex) {
            log.info("Ha ocurrido un NotFoundException");
            throw ex;
        }
          catch (Exception ex) {
            log.info("Ha ocurrido un Exception");
            ex.printStackTrace();
        }
        log.info("Se ha eliminado el articulo");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Inventario>> listarArticuloPorId(@PathVariable("id") Long id) {
        log.info("Entrando al endpoint listarArticuloPorId");
        Optional<Inventario> inventario = inventarioService.finInventarioById(id);
        try {
            log.info("Retornando articulo encontrado por id");
            return ResponseEntity.ok(inventario);
        } catch (NotFoundException ex) {
            log.info("Ha ocurrido un NotFoundException");
            throw ex;
        }
    }
}
