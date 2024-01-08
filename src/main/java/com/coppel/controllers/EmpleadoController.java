package com.coppel.controllers;

import com.coppel.entities.Empleado;
import com.coppel.exceptions.IncorrectBodyException;
import com.coppel.exceptions.NotFoundException;
import com.coppel.services.impl.EmpleadoService;
import jakarta.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/empleados")
@RestController
@CrossOrigin("**")
public class EmpleadoController {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<List<Empleado>> listarTodosLosEmpleados() {
        log.info("Obteniendo la lista de empleados");
        return ResponseEntity.ok(empleadoService.getAllEmpleados());
    }

    @PostMapping("/insertar")
    public ResponseEntity<Empleado> insertarEmpleado(@Valid @RequestBody Empleado empleado) {
        try {
            empleadoService.create(empleado);
            return ResponseEntity.created(new URI("/api/empleados" + empleado.getId())).body(empleado);
        } catch (IncorrectBodyException ex) {
            throw ex;
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        Empleado temporal = null;
        try {
            temporal = empleadoService.save(empleado);
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (IncorrectBodyException ex) {
            throw ex;
        }
        return ResponseEntity.ok(temporal);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Optional<Empleado>> eliminarEmpleado(@PathVariable Long id) {
        try {
            empleadoService.deleteEmpleado(id);
        } catch (NotFoundException ex) {
            throw ex;
        }
          catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Empleado>> listarEmpleadoPorId(@PathVariable("id") Long id) {
        ResponseEntity<Optional<Empleado>> empleado;
        try {
            empleado = ResponseEntity.ok(empleadoService.findEmpleadoById(id));
        } catch (NotFoundException ex) {
            throw ex;
        }
        return empleado;
    }
}
    
    

