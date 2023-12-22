package com.coppel.services.impl;

import com.coppel.entities.Empleado;
import com.coppel.repositories.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author luis.buelna
 */
class EmpleadoServiceTest {
    
    @Mock
    private EmpleadoRepository empleadoRepository;
    
    @InjectMocks
    EmpleadoService empleadoService;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testGetUserByIdOptional(){
        Long id2 = 29L;
        Empleado empleado3 = new Empleado();
        empleado3.setId(id2);
        empleado3.setNombre("Duff");
        empleado3.setApellido("Mackagan");
        empleado3.setPuesto("Supervisor");
        Optional<Empleado> optionalEmpleado3 = Optional.of(empleado3);
        
        //Mockear el comportamiento del empleado de la api rest
        when(empleadoRepository.findById(id2)).thenReturn(optionalEmpleado3);
        
        //Llamar el metodo de servicio que queremos probar
        Optional<Empleado> optionalEmpleado4 = empleadoService.findEmpleadoById(id2);
        
        //Verificar el resultado
        assertEquals(optionalEmpleado3, optionalEmpleado4);
    }
    
    @Test
    public void testCreate(){
        //Datos de prueba
        Long id = 1L;
        Empleado empleadoCreate = new Empleado();
        empleadoCreate.setId(id);
        empleadoCreate.setNombre("Rodolfo");
        empleadoCreate.setApellido("Reno");
        empleadoCreate.setPuesto("Cargador de Bodega");
        
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
    public void testGetAllEmpleados(){
        //Datos de prueba
        Empleado empleadoGet1 = new Empleado(2L, "Raul", "Bastidas", "Almacen");
        Empleado empleadoGet2 = new Empleado(3L, "Esteban", "Gonzalez", "Almacen");
        Empleado empleadoGet3 = new Empleado(4L, "Laura", "Madrid", "Gerente");
        List<Empleado> empleados = new ArrayList<>();
        empleados.add(empleadoGet1);
        empleados.add(empleadoGet2);
        empleados.add(empleadoGet3);
        
        //Mockear el comportamiento del repositorio
        when(empleadoRepository.findAll()).thenReturn(empleados);
        
        //Llamar al metodo del servicio a probar
        List<Empleado> empleadosResult = empleadoService.getAllEmpleados();
        
        //Verificar el resultado
        assertThat(empleados).isNotNull();
        assertEquals(empleados.size(), empleadosResult.size());
        assertEquals(empleados.get(0), empleadosResult.get(0));
        assertEquals(empleados.get(1), empleadosResult.get(1));
        assertEquals(empleados.get(2), empleadosResult.get(2));
        
    }
    
    @Test
    public void testDeleteEmpleado(){
        //Datos de prueba
        Long empleadoId = 1L;
        Empleado empleadoD = new Empleado(1L, "Raul", "Bastidas", "Almacen");
        
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
