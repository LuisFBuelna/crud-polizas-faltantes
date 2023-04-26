package com.coppel.controllers;

import com.coppel.entities.Inventario;
import com.coppel.services.impl.InventarioService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/inventario")
@RestController
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

     @GetMapping
    private ResponseEntity<List<Inventario>> listarTodoElInventario() {
        return ResponseEntity.ok(inventarioService.getAllInventario());
    }
    
    @PutMapping("/insertar")
    private ResponseEntity<Inventario> insertarArticulo(@RequestBody Inventario inventario) {
        Inventario temporal = inventarioService.create(inventario);

        try {
            return ResponseEntity.created(new URI("/api/inventario" + temporal.getId())).body(temporal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/actualizar/{id}")
    private ResponseEntity<Inventario> guardarArticulo(@RequestBody Inventario inventario){
        Inventario temporal = inventarioService.save(inventario);
        try{
            return ResponseEntity.ok(temporal);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    private ResponseEntity eliminarArticulo(@PathVariable Long id){
        return ResponseEntity.ok(inventarioService.deleteInventario(id));
    }
    
    @GetMapping("/{id}")
    private ResponseEntity <Optional<Inventario>> listarArticuloPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(inventarioService.finInventarioById(id));
    }
    

}
