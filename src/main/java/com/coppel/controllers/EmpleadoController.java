package com.coppel.controllers;

import com.coppel.entities.Empleado;
import com.coppel.services.impl.EmpleadoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/empleados")
@RestController
public class EmpleadoController {
    
    @Autowired
    private EmpleadoService empleadoService;
    
    @GetMapping
    private ResponseEntity <List<Empleado>> listarTodosLosEmpleados(){
        return ResponseEntity.ok(empleadoService.getAllEmpleados());
    }
    
    @PostMapping("/insertar")
    private ResponseEntity<Empleado> insertarEmpleado(@Valid @RequestBody Empleado empleado){
        Empleado temporal = empleadoService.create(empleado);
        
        try{
            return ResponseEntity.created(new URI("/api/empleados"+temporal.getId())).body(temporal);
        } catch(URISyntaxException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/actualizar/{id}")
    private ResponseEntity<Empleado> guardarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado){
        Empleado temporal = empleadoService.save(empleado);
        try{
            return ResponseEntity.ok(temporal);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/eliminar/{id}")
    private ResponseEntity <Optional<Empleado>> eliminarEmpleado(@PathVariable Long id){
        try {
            empleadoService.deleteEmpleado(id);
        } catch (Exception ex){
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }  
    
    @GetMapping("/{id}")
    private ResponseEntity <Optional<Empleado>> listarEmpleadoPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(empleadoService.findEmpleadoById(id));
    }
}
    
    

