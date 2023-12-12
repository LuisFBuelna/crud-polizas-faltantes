package com.coppel.services.impl;

import com.coppel.entities.Empleado;
import com.coppel.exceptions.IncorrectBodyException;
import com.coppel.exceptions.NotFoundException;
import com.coppel.repositories.EmpleadoRepository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoService.class);

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado create(Empleado empleado) {
        Empleado empleado1 = empleadoRepository.save(empleado);
        if (empleado == null) {
            throw new IncorrectBodyException("El empleado no se pudo registrar correctamente");
        }
        return empleado;
    }

    public Empleado save(Empleado empleado) {
        Empleado empleado1 = empleadoRepository.save(empleado);
        if (isString(empleado)) {
            throw new IllegalArgumentException("Los parametros proporcionados son incorrectos");
        }
        if (empleado == null) {
            throw new IncorrectBodyException("El empleado no se pudo modificar correctamente");
        }
        return empleado;
    }

    public List<Empleado> getAllEmpleados() {
        log.info("Buscando empleados");
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> deleteEmpleado(Long id) {
        Optional<Empleado> existeEmpleado = empleadoRepository.findById(id);
        try {
            if (existeEmpleado.isPresent()) {
                empleadoRepository.delete(existeEmpleado.get());
            }
        } catch (NotFoundException ex) {
            throw new NotFoundException("El empleado a borrar no se encontro");
        }
        return existeEmpleado;
    }

    public Optional<Empleado> findEmpleadoById(Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isPresent()) {
            return empleado;
        } else {
            throw new NotFoundException("El empleado solicitado no fue encontrado");
        }
    }

    public Optional<Empleado> findEmpleadoById2(Long id) {
        Optional<Empleado> empleado = Optional.of(empleadoRepository.getReferenceById(id));
        if (empleado.isPresent()) {
            return empleado;
        } else {
            throw new NotFoundException("El empleado solicitado no fue encontrado");
        }
    }

    public boolean isString(Empleado empleado) {
        if (!(empleado.getNombre() instanceof String) ||
                !(empleado.getApellido() instanceof String) ||
                !(empleado.getPuesto() instanceof String)) {
            return false;
        }
        return true;
    }

}
