package com.coppel.services.impl;

import com.coppel.dto.PolizaDTO;
import com.coppel.entities.Polizas;
import com.coppel.exceptions.InternalException;
import com.coppel.mapper.PolizaMapper;
import com.coppel.repositories.PolizasRepository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PolizasServiceTest {

    @Mock
    PolizasRepository polizasRepository;

    @InjectMocks
    private PolizasService polizasService;

    Polizas polizaCreate = new Polizas(1, 1, 1, 5, "2024-01-05");

    PolizaDTO polizaDto = new PolizaDTO(1, 1, 5, "2024-01-05");

    PolizaDTO polizaDtoUpdate = new PolizaDTO(1, 1, 5, "2024-01-05");

    Polizas polizaById = new Polizas();

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
        polizaById.setId(id);
        polizaById.setEmpleadoGenero(3);
        polizaById.setIdInventario(1);
        polizaById.setCantidad(25);
        polizaById.setFecha(String.valueOf(LocalDate.now()));
        Optional<Polizas> optionalPolizaId = Optional.of(polizaById);

        when(polizasRepository.findById((long) id)).thenReturn(Optional.of(polizaById));

        Optional<PolizaDTO> optionalPolizas = Optional.ofNullable(polizasService.findPolizaById((long) id));

        assertEquals(polizaById.getId(), optionalPolizas.get().getId());
        assertThat(polizasService);
    }

    @Test
    void crearPoliza() throws InternalException {

        //Simular comportamiento de repositorio
        when(polizasRepository.insertarPoliza(polizaDto.getEmpleado(), polizaDto.getIdInventario(), polizaDto.getCantidad(), Date.valueOf(polizaDto.getFecha()))).thenReturn(polizaCreate);

        //Llamar al metodo a probar
        PolizaDTO polizaResult = polizasService.crearPoliza(polizaDto);

        //Verificar resultado
        assertEquals(polizaCreate.getEmpleadoGenero(), polizaResult.getEmpleado());
        assertEquals(polizaCreate.getIdInventario(), polizaResult.getIdInventario());

        verify(polizasRepository).insertarPoliza(
                polizaCreate.getEmpleadoGenero(),
                polizaCreate.getIdInventario(),
                polizaCreate.getCantidad(),
                Date.valueOf(polizaCreate.getFecha()));
    }

    @Test
    void eliminarPoliza() throws Exception {
        int id = 1;
        polizaById.setId(id);

        //Mockear el comportamiento del repositorio
        when(polizasRepository.findById((long) polizaById.getId())).thenReturn(Optional.of(polizaById));
        Mockito.doNothing().when(polizasRepository).borrarPoliza(polizaById.getId());

        //Llamar al metodo de servicio a probar
        polizasService.eliminarPoliza(polizaById.getId());

        //Verificar que se mando llamar repository a traves de service
        verify(polizasRepository).borrarPoliza(polizaById.getId());
        assertEquals(id, polizaById.getId());
    }

    @Test
    void modificarPoliza() throws Exception {

        //Simular comportamiento de repository para inyectar en service
        when(polizasRepository.actualizarPoliza(polizaDtoUpdate.getId(),
                polizaDtoUpdate.getEmpleado(), polizaDtoUpdate.getIdInventario(),
                polizaDtoUpdate.getCantidad(), Date.valueOf(polizaDtoUpdate.getFecha()))).thenReturn(polizaCreate);

        PolizaDTO polizaResult = polizasService.modificarPoliza(polizaDtoUpdate);

        //Verificar resultado
        assertEquals(polizaCreate.getEmpleadoGenero(), polizaResult.getEmpleado());
        assertEquals(polizaCreate.getIdInventario(), polizaResult.getIdInventario());

        verify(polizasRepository).actualizarPoliza(polizaDtoUpdate.getId(),
                polizaDtoUpdate.getEmpleado(), polizaDtoUpdate.getIdInventario(),
                polizaDtoUpdate.getCantidad(), Date.valueOf(polizaDtoUpdate.getFecha()));
    }
}