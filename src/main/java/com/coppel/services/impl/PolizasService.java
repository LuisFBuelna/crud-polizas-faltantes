package com.coppel.services.impl;

import com.coppel.entities.Polizas;
import com.coppel.repositories.PolizasRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
}
