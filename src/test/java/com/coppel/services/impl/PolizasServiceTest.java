package com.coppel.services.impl;

import com.coppel.dto.PolizaDTO;
import com.coppel.entities.Polizas;
import com.coppel.repositories.PolizasRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PolizasServiceTest {

    @Mock
    PolizasRepository polizasRepository;

    @InjectMocks
    private PolizasService polizasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPolizas() {
    }

    @Test
    void getPolizasEmpleado() {
    }

    @Test
    void getPolizaEmpleadoById() {
    }

    @Test
    void findPolizaById() {
        int id = 1;
        Polizas polizaXId = new Polizas();
        polizaXId.setId(id);
        polizaXId.setEmpleadoGenero(3);
        polizaXId.setIdInventario(1);
        polizaXId.setCantidad(25);
        polizaXId.setFecha(String.valueOf(LocalDate.now()));
        Optional<Polizas> optionalPolizaId = Optional.of(polizaXId);

        when(polizasRepository.findById((long) id)).thenReturn(Optional.of(polizaXId));

        Optional<PolizaDTO> optionalPolizas = Optional.ofNullable(polizasService.findPolizaById((long) id));

        assertEquals(polizaXId.getId(), optionalPolizas.get().getId());
        assertThat(polizasService);
    }

    @Test
    void crearPoliza() {
    }

    @Test
    void eliminarPoliza() {
    }

    @Test
    void modificarPoliza() {
    }
}