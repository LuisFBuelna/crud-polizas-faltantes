package com.coppel.services.impl;

import com.coppel.entities.Empleado;
import com.coppel.repositories.EmpleadoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmpleadoService {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    public Empleado create (Empleado empleado){
        return empleadoRepository.save(empleado);
    }
    
    public Empleado save (Empleado empleado){
        return empleadoRepository.save(empleado);
    }
    
    public List<Empleado> getAllEmpleados(){
        return empleadoRepository.findAll();
    }
    
    public Optional<Empleado> deleteEmpleado (Long id){
        Optional<Empleado> existeEmpleado = empleadoRepository.findById(id);
        if(existeEmpleado.isPresent()){
            empleadoRepository.delete(existeEmpleado.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return existeEmpleado;
    }
    
    public Optional<Empleado> findEmpleadoById (Long id){
        return empleadoRepository.findById(id);
    }
    
    public Empleado findEmpleadoById2 (Long id){
        return empleadoRepository.getById(id);
    }
}
