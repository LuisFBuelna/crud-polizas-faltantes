package com.coppel.services.impl;

import com.coppel.entities.Empleado;
import com.coppel.repositories.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

/**
 * @author luis.buelna
 */
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    EmpleadoService empleadoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByIdOptional() throws Exception {
        Long id2 = 29L;
        Empleado empleado3 = new Empleado();
        empleado3.setId(id2);
        empleado3.setNombre("Duff");
        empleado3.setApellido("Mackagan");
        empleado3.setPuesto("Supervisor");
        empleado3.setStatus(1);
        Optional<Empleado> optionalEmpleado3 = Optional.of(empleado3);

        //Mockear el comportamiento del empleado de la api rest
        when(empleadoRepository.findById(id2)).thenReturn(optionalEmpleado3);

        //Llamar el metodo de servicio que queremos probar
        Optional<Empleado> optionalEmpleado4 = empleadoService.findEmpleadoById(id2);

        //Verificar el resultado
        assertEquals(optionalEmpleado3, optionalEmpleado4);
    }

    @Test
    public void testCreate() throws Exception {
        //Datos de prueba
        Long id = 1L;
        Empleado empleadoCreate = new Empleado();
        empleadoCreate.setId(id);
        empleadoCreate.setNombre("Rodolfo");
        empleadoCreate.setApellido("Reno");
        empleadoCreate.setPuesto("Cargador de Bodega");
        empleadoCreate.setStatus(1);

        //Mockear el comportamiento del repositorio
        when(empleadoRepository.save(empleadoCreate)).thenReturn(empleadoCreate);

        //Llamar al metodo del servicio a probar
        Empleado empleadoResult = empleadoService.create(empleadoCreate);

        //Verificar el resultado
        assertEquals(id, empleadoResult.getId());
        assertEquals(empleadoCreate.getNombre(), empleadoResult.getNombre());
        assertEquals(empleadoCreate.getApellido(), empleadoResult.getApellido());
        assertEquals(empleadoCreate.getPuesto(), empleadoResult.getPuesto());

        //Verificar que se haya llamado al metodo save del repositorio
        verify(empleadoRepository).save(empleadoCreate);
    }

    @Test
    public void testGetAllEmpleadosPageable() {
        //Datos de prueba
        Empleado empleadoGet1 = new Empleado(2L, "Raul", "Bastidas", "Almacen", 1);
        Empleado empleadoGet2 = new Empleado(3L, "Esteban", "Gonzalez", "Almacen", 1);
        Empleado empleadoGet3 = new Empleado(4L, "Laura", "Madrid", "Gerente", 1);
        List<Empleado> empleadosList = new ArrayList<>();
        empleadosList.add(empleadoGet1);
        empleadosList.add(empleadoGet2);
        empleadosList.add(empleadoGet3);

        Page<Empleado> empleadosPage = new PageImpl<>(empleadosList);

        //Mockear el comportamiento del repositorio
        when(empleadoRepository.findAll(Pageable.unpaged())).thenReturn(empleadosPage);


        //Llamar al metodo del servicio a probar
        Page<Empleado> empleadosResult = empleadoService.getAllEmpleadosPageable(Pageable.unpaged());

        //Verificar el resultado
        assertThat(empleadosPage).isNotNull();
        assertEquals(empleadosPage.getTotalElements(), empleadosResult.getTotalElements());
        assertEquals(empleadosPage.getTotalPages(), empleadosResult.getTotalPages());
        verify(empleadoRepository).findAll(Pageable.unpaged());
    }

    @Test
    public void testDeleteEmpleado() throws Exception {
        //Datos de prueba
        Long empleadoId = 1L;
        Empleado empleadoD = new Empleado(1L, "Raul", "Bastidas", "Almacen", 1);

        //Mockear el comportamiento del repositorio
        when(empleadoRepository.findById(empleadoD.getId())).thenReturn(Optional.of(empleadoD));
        Mockito.doNothing().when(empleadoRepository).deleteById(empleadoId);
        //Llamar al metodo de servicio  a probar
        empleadoService.deleteEmpleado(empleadoId);

        //Verificar que se haya llamado al metodo deleteById del repositorio
        verify(empleadoRepository).findById(empleadoId);
        assertEquals(empleadoId, empleadoD.getId());
    }
}
