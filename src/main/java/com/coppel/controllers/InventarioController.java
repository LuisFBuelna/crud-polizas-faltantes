package com.coppel.controllers;

import com.coppel.entities.Inventario;
import com.coppel.services.impl.InventarioService;
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

@RequestMapping("/inventario")
@RestController
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

     @GetMapping
    private ResponseEntity <List<Inventario>> listarTodoElInventario() {
        return ResponseEntity.ok(inventarioService.getAllInventario());
    }
    
    @PostMapping("/insertar")
    private ResponseEntity<Inventario> insertarArticulo(@Valid @RequestBody Inventario inventario) {
        Inventario temporal = inventarioService.save(inventario);
        return new ResponseEntity(temporal, HttpStatus.CREATED);

       // return ResponseEntity.created(URI.create("/inventario" + temporal.getId())).body(temporal);
    }
    
    @PutMapping("/actualizar/{id}")
    private ResponseEntity<Inventario> guardarArticulo(@PathVariable Long id, @RequestBody Inventario inventario){
        Optional<Inventario> temporal = inventarioService.finInventarioById(id);
            if(temporal.isPresent()){
             try{   
            Inventario inventarioTemporal = temporal.get();
            inventarioTemporal.setNombre(inventario.getNombre());
            inventarioTemporal.setCantidad(inventario.getCantidad());
            Inventario updatedInventario = inventarioService.save(inventarioTemporal);
            return ResponseEntity.ok(updatedInventario);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    
    }
            return (ResponseEntity<Inventario>) ResponseEntity.status(HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{id}")
    private ResponseEntity eliminarArticulo(@PathVariable Long id){
         try {
             inventarioService.deleteInventario(id);
         } catch (Exception ex){

         }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @GetMapping("/{id}")
    private ResponseEntity <Optional<Inventario>> listarArticuloPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(inventarioService.finInventarioById(id));
    }
    
    @GetMapping("/verinventario")
    private ResponseEntity<List<Inventario>> restarInventario(){
        return ResponseEntity.ok(inventarioService.obtenerInventario());
    }
    
    
}
