package com.coppel.controllers;

import com.coppel.entities.Polizas;
import com.coppel.exceptions.InternalException;
import com.coppel.services.impl.PolizasService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/polizas")
@RestController
public class PolizaController {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PolizasService polizaService;

    @GetMapping
    public ResponseEntity<List<Polizas>> listarTodasLasPolizas() {
        log.info("Obteniendo lista de polizas");
        return ResponseEntity.ok(polizaService.getAllPolizas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Polizas>> listarPolizaPorId(@PathVariable("id") Long id) {
        log.info("Entrando a endpoint listarPolizaPorId");
        return ResponseEntity.ok(polizaService.finPolizaById(id));
    }

    @PostMapping("/insertarPoliza")
    public ResponseEntity<Polizas> insertarPoliza(@RequestBody @Valid Polizas poliza) throws InternalException {
        log.info("Entrando a endpoint insertarPoliza");
            Polizas polizaTemporal = polizaService.crearPoliza(poliza);
            log.info("Retornando la poliza creada");
            return ResponseEntity.status(HttpStatus.OK).body(polizaTemporal);
    }

    @PutMapping("/update")
    public ResponseEntity<Polizas> updatePoliza(@RequestBody Polizas poliza) {
        log.info("Entrando a endpoint updatePoliza");
            polizaService.modificarPoliza(poliza);
        log.info("Retornando poliza modificada");
        return ResponseEntity.status(HttpStatus.OK).body(poliza);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePoliza(@PathVariable int id) throws Exception {
        log.info("Entrando a endpoint deletePoliza");
            polizaService.eliminarPoliza(id);
        log.info("Poliza eliminada correctamente");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
