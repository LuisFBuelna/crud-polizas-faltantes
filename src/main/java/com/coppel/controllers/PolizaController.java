package com.coppel.controllers;

import com.coppel.entities.Polizas;
import com.coppel.repositories.PolizasRepository;
import com.coppel.services.impl.PolizasService;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/polizas")
@RestController
public class PolizaController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private PolizasService polizaService;
    
    @GetMapping
    private ResponseEntity <List<Polizas>> listarTodasLasPolizas(){
        return ResponseEntity.ok(polizaService.getAllPolizas());
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
    
    @DeleteMapping("/eliminar/{id}")
    private ResponseEntity <Optional<Polizas>> eliminarPoliza(@PathVariable Long id){
        return ResponseEntity.ok(polizaService.deletePoliza(id));
    }
    
    @GetMapping("/{id}")
    private ResponseEntity <Optional<Polizas>> ListarPolizaPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(polizaService.finPolizaById(id));
    }
    
    @PostMapping("/insertarPoliza")
    private ResponseEntity<Polizas> insertarPoliza(@RequestBody Polizas poliza){
        try{
            polizaService.crearPoliza(
            poliza.getEmpleadoGenero(),
            poliza.getSku(),
            poliza.getCantidad(),
            poliza.getFecha()
            );
        }catch(Exception ex){
        }
        return ResponseEntity.status(HttpStatus.OK).body(poliza);
    }
    
    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Polizas> deletePoliza(@PathVariable int id){
        try{
            polizaService.eliminarPoliza(id);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    } 
    
    @PutMapping("/update")
    private ResponseEntity<Polizas> updatePoliza(@RequestBody Polizas poliza){
        try {
            polizaService.modificarPoliza(
                    poliza.getId(),
                    poliza.getEmpleadoGenero(),
                    poliza.getSku(),
                    poliza.getCantidad(), 
                    poliza.getFecha());
        } catch (Exception e) {
            return (ResponseEntity<Polizas>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(poliza);
    }
    
}
