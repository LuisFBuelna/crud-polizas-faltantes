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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/empleados")
@RestController
public class EmpleadoController {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<Page<Empleado>> listarTodosLosEmpleados(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("Obteniendo la lista de empleados");
        return ResponseEntity.ok(empleadoService.getAllEmpleados(pageable));
    }

    @PostMapping("/insertar")
    public ResponseEntity<Empleado> insertarEmpleado(@Valid @RequestBody Empleado empleado) throws Exception {
        empleadoService.create(empleado);
        return ResponseEntity.created(new URI("/api/empleados" + empleado.getId())).body(empleado);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) throws Exception {
        Empleado temporal = null;
        temporal = empleadoService.create(empleado);
        return ResponseEntity.status(HttpStatus.OK).body(temporal);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Optional<Empleado>> eliminarEmpleado(@PathVariable Long id) throws Exception {
            empleadoService.deleteEmpleado(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Empleado>> listarEmpleadoPorId(@PathVariable("id") Long id) throws Exception {
        ResponseEntity<Optional<Empleado>> empleado;
            empleado = ResponseEntity.ok(empleadoService.findEmpleadoById(id));
        return empleado;
    }
}
    
    

