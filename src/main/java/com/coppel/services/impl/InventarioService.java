package com.coppel.services.impl;

import com.coppel.entities.Inventario;
import com.coppel.repositories.InventarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;
    
    public Inventario create(Inventario inventario){
        return inventarioRepository.save(inventario);
    }
    
    public Inventario save(Inventario inventario){
        return inventarioRepository.save(inventario);
    }
    
    public List<Inventario> getAllInventario(){
        return inventarioRepository.findAll();
    }
    
    public Optional<Inventario> deleteInventario(Long id){
        Optional<Inventario> existeArticulo = inventarioRepository.findById(id);
        if(existeArticulo.isPresent()){
            inventarioRepository.delete(existeArticulo.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return existeArticulo;
    }
    
    public Optional<Inventario> finInventarioById(Long id){
        return inventarioRepository.findById(id);
    }
}
