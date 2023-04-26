package com.coppel.controllers;

import com.coppel.entities.Polizas;
import com.coppel.services.impl.PolizasService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/polizas")
@RestController
public class PolizaController {
    
    @Autowired
    private PolizasService polizaService;
    
    @PutMapping("/insertar")
    private ResponseEntity<Polizas> insertarPoliza(@RequestBody Polizas poliza){
        Polizas temporal = polizaService.create(poliza);
        
        try {
            return ResponseEntity.created(new URI("7api/polizas"+temporal.getId())).body(temporal);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/actualizar/{id}")
    private ResponseEntity<Polizas> guardarPoliza(@RequestBody Polizas poliza){
        Polizas temporal = polizaService.save(poliza);
        try{
            return ResponseEntity.ok(temporal);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping
    private ResponseEntity <List<Polizas>> listarTodasLasPolizas(){
        return ResponseEntity.ok(polizaService.getAllPolizas());
    }
    
    @DeleteMapping("/eliminar/{id}")
    private ResponseEntity <Optional<Polizas>> eliminarPoliza(@PathVariable Long id){
        return ResponseEntity.ok(polizaService.deletePoliza(id));
    }
    
    @GetMapping("/{id}")
    private ResponseEntity <Optional<Polizas>> ListarPolizaPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(polizaService.finPolizaById(id));
    }
}
