package com.coppel.services.impl;

import com.coppel.controllers.EmpleadoController;
import com.coppel.entities.Empleado;
import com.coppel.entities.Inventario;
import com.coppel.exceptions.IncorrectBodyException;
import com.coppel.exceptions.NotFoundException;
import com.coppel.repositories.InventarioRepository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InventarioService {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoService.class);

    @Autowired
    private InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public Inventario create(Inventario inventario) {
        Inventario inventario1 = inventarioRepository.save(inventario);
        if (inventario == null) {
            throw new IncorrectBodyException("El articulo no se pudo registrar correctamente");
        }
        return inventario1;
    }

    public Inventario save(Inventario inventario) {
        log.info("Creando articulo");
        Inventario inventario1 = inventarioRepository.save(inventario);
        if (inventario == null) {
            log.info("Retornando un IncorrectBodyException desde capa servicio");
            throw new IncorrectBodyException("El articulo no se pudo registrar correctamente");
        }
        log.info("Articulo creado");
        return inventario1;
    }

    public Page<Inventario> getAllInventario() {
        log.info("Buscando todo el inventario");
        return new PageImpl<>(inventarioRepository.findAll());
    }

    public Optional<Inventario> deleteInventario(Long id) {
        log.info("Entrando a metodo de busqueda de articulo por id");
        Optional<Inventario> existeArticulo = inventarioRepository.findById(id);
        if (existeArticulo.isPresent()) {
            log.info("Articulo encontrado, se procede a eliminar");
            inventarioRepository.delete(existeArticulo.get());
        } else {
            log.info("Articulo no encontrado");
            throw new NotFoundException("El articulo a eliminar no fue encontrado");
        }
        return existeArticulo;
    }

    public Optional<Inventario> finInventarioById(Long id) {
        log.info("Buscando articulo por id");
        Optional<Inventario> existeArticulo = inventarioRepository.findById(id);
        if (!existeArticulo.isPresent()) {
            throw new NotFoundException("El articulo no se encontro en el inventario");
        }
        log.info("Articulo encontrado por id");
        return existeArticulo;
    }
}
