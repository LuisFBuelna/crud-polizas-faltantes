package com.coppel.services.impl;

import com.coppel.entities.Polizas;
import com.coppel.repositories.PolizasRepository;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PolizasService {
    
    @Autowired
    private PolizasRepository polizaRepository;
    
    public Polizas create (Polizas poliza){
        return polizaRepository.save(poliza);
    }
    
    public Polizas save(Polizas poliza){
        return polizaRepository.save(poliza);
    }
    
    public List<Polizas> getAllPolizas(){
        return polizaRepository.findAll();
    }
    
    public Optional<Polizas> deletePoliza(Long id){
        Optional<Polizas> existPoliza = polizaRepository.findById(id);
        if(existPoliza.isPresent()){
            polizaRepository.delete(existPoliza.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return existPoliza;
    }
    
    public Optional<Polizas> finPolizaById (Long id){
        return polizaRepository.findById(id);
    }

    public ResponseEntity<Polizas> crearPoliza(int empleadoGenero, 
            int sku, int cantidad, Date fecha){
        return polizaRepository.insertarPoliza(empleadoGenero, sku, cantidad, fecha);
    }
    
    public ResponseEntity<Polizas> eliminarPoliza(int id){
        return polizaRepository.borrarPoliza(id);
    }
    
    public ResponseEntity<Polizas> modificarPoliza(int id, int empleadoGenero, 
            int sku, int cantidad, Date fecha){
        return polizaRepository.actualizarPoliza(id, empleadoGenero, sku, cantidad, fecha);
    }    
    
    public Optional<Polizas> findPolizaById (int id){
        return polizaRepository.findPolizaById(id);
    }
    
    @QueryMapping
    public List<Polizas> allPolizas(){
        System.out.println("ENTRANDO A ALLPOLIZAS GRAPHQL");
        return polizaRepository.findAll();
    }
}
