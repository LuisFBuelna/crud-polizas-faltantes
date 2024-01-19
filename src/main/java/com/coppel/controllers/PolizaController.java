package com.coppel.controllers;

import com.coppel.dto.PolizaDTO;
import com.coppel.dto.PolizaEmpleadoDTO;
import com.coppel.exceptions.InternalException;
import com.coppel.services.impl.PolizasService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/polizas")
@RestController
public class PolizaController {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private PolizasService polizaService;

    @GetMapping
    public ResponseEntity<Page<PolizaDTO>> listarTodasLasPolizas(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("Obteniendo lista de polizas");
        return ResponseEntity.ok(polizaService.getAllPolizas());
    }

    @GetMapping("/empleados")
    public ResponseEntity<List<PolizaEmpleadoDTO>> PolizasConEmpleado() {
        return ResponseEntity.ok(polizaService.getPolizasEmpleado());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolizaDTO> listarPolizaPorId(@PathVariable("id") Long id) {
        log.info("Entrando a endpoint listarPolizaPorId");
        return ResponseEntity.ok(polizaService.findPolizaById(id));
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<PolizaEmpleadoDTO> listarPolizaEmpleadoPorId(@PathVariable("id") int idPoliza) {
        log.info("Entrando a endpoint listarPolizaEmpleadoPorId");
        return ResponseEntity.ok((polizaService.getPolizaEmpleadoById(idPoliza)));
    }

    @PostMapping("/insertarPoliza")
    public ResponseEntity<PolizaDTO> insertarPoliza(@RequestBody @Valid PolizaDTO polizaDTO) throws InternalException {
        log.info("Entrando a endpoint insertarPoliza");
        PolizaDTO polizaTemporal = polizaService.crearPoliza(polizaDTO);
        log.info("Retornando la poliza creada");
        return ResponseEntity.status(HttpStatus.OK).body(polizaTemporal);
    }

    @PutMapping("/update")
    public ResponseEntity<PolizaDTO> updatePoliza(@RequestBody PolizaDTO polizaDTO) throws Exception {
        log.info("Entrando a endpoint updatePoliza");
        PolizaDTO polizaModificada = polizaService.modificarPoliza(polizaDTO);
        log.info("Retornando poliza modificada");
        return ResponseEntity.status(HttpStatus.OK).body(polizaModificada);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePoliza(@PathVariable int id) throws Exception {
        log.info("Entrando a endpoint deletePoliza");
        polizaService.eliminarPoliza(id);
        log.info("Poliza eliminada correctamente");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
