package com.coppel.controllers;

import com.coppel.dto.ArticuloDTO;
import com.coppel.exceptions.InternalException;
import com.coppel.services.impl.ArticuloService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/articulos")
@RestController
@CrossOrigin("*")
public class ArticuloController {

    private static final Logger log = LoggerFactory.getLogger(ArticuloController.class);

    @Autowired
    ArticuloService articuloService;

    @GetMapping
    public ResponseEntity<List<ArticuloDTO>> listarTodosLosArticulos(){
        log.info("Obteniendo lista de articulos");
        return ResponseEntity.ok(articuloService.obtenerArticulos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticuloDTO> listarArticuloPorId(@PathVariable("id") Long id) {
        log.info("Entrando a endpoint listarArticuloPorId");
        return ResponseEntity.ok(articuloService.findArticuloById(id));
    }

    @PostMapping("/insertarArticulo")
    public ResponseEntity<ArticuloDTO> insertarArticulo(@RequestBody @Valid ArticuloDTO articuloDTO) throws InternalException {
        log.info("Entrando a endpoint insertarArticulo");
        ArticuloDTO articuloTemporal = articuloService.crearArticulo(articuloDTO);
        log.info("Retornando el articulo creado");
        return ResponseEntity.status(HttpStatus.OK).body(articuloTemporal);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ArticuloDTO> updateArticulo(@PathVariable Long id, @RequestBody ArticuloDTO articuloDTO) {
        log.info("Entrando a endpoint updateArticulo");
        ArticuloDTO articuloModificado =articuloService.modificarArticulo(articuloDTO);
        log.info("Retornando articulo modificado");
        return ResponseEntity.status(HttpStatus.OK).body(articuloModificado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteArticulo(@PathVariable Long id) throws Exception {
        log.info("Entrando a endpoint deleteArticulo");
        articuloService.eliminarArticulo(id);
        log.info("Articulo eliminado correctamente");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
