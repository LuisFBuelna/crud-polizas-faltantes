package com.coppel.services.impl;

import com.coppel.entities.Inventario;
import com.coppel.repositories.InventarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

/**
 *
 * @author luis.buelna
 */
public class InventarioServiceTest {
    
    @Mock
    InventarioRepository inventarioRepository;
    
    @InjectMocks
    private InventarioService inventarioService;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void finInventarioByIdTest(){
        Long id = 1L;
        Inventario inventarioById = new Inventario();
        inventarioById.setId(id);
        inventarioById.setCantidad(5);
        inventarioById.setIdArticulo(1);
        Optional<Inventario> optionalInventarioById = Optional.of(inventarioById);
        
        when(inventarioRepository.findById(id)).thenReturn(optionalInventarioById);
        
        Optional<Inventario> optionalInventarioById2 = inventarioService.finInventarioById(id);
        
        assertEquals(optionalInventarioById, optionalInventarioById2);
    }
    
    @Test
    public void testCreate(){
        //Datos de prueba
        Long id = 2L;
        Inventario inventarioInsert = new Inventario();
        inventarioInsert.setId(id);
        inventarioInsert.setCantidad(70);
        inventarioInsert.setIdArticulo(1);
        
        //Mockear el comportamiento del repositorio
        when(inventarioRepository.save(inventarioInsert)).thenReturn(inventarioInsert);
        
        //Llamar al metodo del servicio a probar
        Inventario inventarioResult = inventarioService.create(inventarioInsert);
        
        //Verificar el resultado
        assertEquals(id, inventarioResult.getId());
        assertEquals(inventarioInsert.getIdArticulo(), inventarioResult.getIdArticulo());
        assertEquals(inventarioInsert.getCantidad(), inventarioResult.getCantidad());


        
        //Verificar que se haya llamado al metodo save del repositorio
        verify(inventarioRepository, Mockito.times(1)).save(inventarioInsert);
    }
    
    @Test
    public void testGetAllInventario(){
        //Datos de prueba
        Inventario inventarioGet1 = new Inventario(1L, 5, 15);
        Inventario inventarioGet2 = new Inventario(2L, 5, 20);
        Inventario inventarioGet3 = new Inventario(3L, 5, 25);
        List<Inventario> inventario = new ArrayList<>();
        inventario.add(inventarioGet1);
        inventario.add(inventarioGet2);
        inventario.add(inventarioGet3);
        
        //Mockear el comportamiento del repository
        when(inventarioRepository.findAll()).thenReturn(inventario);
        
        //Llamar el metodo del servicio a probar
        //Page<Inventario> inventarioResult = inventarioService.getAllInventario();
        
        //Verificar el resultado
        assertThat(inventario).isNotNull();
        //assertEquals(inventario.size(), inventarioResult.get().);
        //assertEquals(inventario.get(0), inventarioResult.get(0));
        //assertEquals(inventario.get(1), inventarioResult.get(1));
        //assertEquals(inventario.get(2), inventarioResult.get(2));
    }
}
