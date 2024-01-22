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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoService.class);

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> getAllEmpleados() {
        log.info("Buscando empleados");
        List<Empleado> empleados = empleadoRepository.findAll();
        return empleados;
    }

    public Page<Empleado> getAllEmpleadosPageable(Pageable pageable) {
        log.info("Buscando empleados");
        Page<Empleado> empleados = empleadoRepository.findAll(pageable);
        return empleados;
    }

    public Empleado create(Empleado empleado) throws Exception {
        try {
            Empleado empleado1 = empleadoRepository.save(empleado);
            if (empleado == null) {
                throw new IncorrectBodyException("El empleado no se pudo registrar correctamente");
            }
        } catch (IncorrectBodyException ex) {
            throw new IncorrectBodyException("El empleado no se pudo crear correctamente");
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        } catch (Exception ex) {
            throw new Exception();
        }
        return empleado;
    }

    public Optional<Empleado> deleteEmpleado(Long id) throws Exception {
        Optional<Empleado> existeEmpleado = empleadoRepository.findById(id);
        try {
            if (existeEmpleado.isPresent()) {
                empleadoRepository.delete(existeEmpleado.get());
            }
        } catch (NotFoundException ex) {
            throw new NotFoundException("El empleado a borrar no se encontro");
        } catch (Exception ex) {
            throw new Exception("Ocurrio un problema al borrar el empleado");
        }
        return existeEmpleado;
    }

    public Optional<Empleado> findEmpleadoById(Long id) throws Exception {
        try {
            Optional<Empleado> empleado = empleadoRepository.findById(id);
            if (empleado.isPresent()) {
                return empleado;
            } else {
                throw new NotFoundException("El empleado solicitado no fue encontrado");
            }
        } catch (Exception ex) {
            throw new Exception();
        }
    }
}
