package com.coppel.services.impl;

import com.coppel.entities.Inventario;
import com.coppel.repositories.InventarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

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
        inventarioById.setNombre("Lavadora Mabe G600");
        inventarioById.setCantidad(5);
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
        inventarioInsert.setNombre("Refrigerador LG");
        inventarioInsert.setCantidad(70);
        
        //Mockear el comportamiento del repositorio
        when(inventarioRepository.save(inventarioInsert)).thenReturn(inventarioInsert);
        
        //Llamar al metodo del servicio a probar
        Inventario inventarioResult = inventarioService.create(inventarioInsert);
        
        //Verificar el resultado
        assertEquals(id, inventarioResult.getId());
        assertEquals(inventarioInsert.getNombre(), inventarioResult.getNombre());
        assertEquals(inventarioInsert.getCantidad(), inventarioResult.getCantidad());
        
        //Verificar que se haya llamado al metodo save del repositorio
        verify(inventarioRepository).save(inventarioInsert);
    }
    
    @Test
    public void testGetAllInventario(){
        //Datos de prueba
        Inventario inventarioGet1 = new Inventario(1L, "Plancha T200", 15);
        Inventario inventarioGet2 = new Inventario(2L, "Maleta Nautica", 20);
        Inventario inventarioGet3 = new Inventario(3L, "Celular Moto G XT", 25);
        List<Inventario> inventario = new ArrayList<>();
        inventario.add(inventarioGet1);
        inventario.add(inventarioGet2);
        inventario.add(inventarioGet3);
        
        //Mockear el comportamiento del repository
        when(inventarioRepository.findAll()).thenReturn(inventario);
        
        //Llamar el metodo del servicio a probar
        List<Inventario> inventarioResult = inventarioService.getAllInventario();
        
        //Verificar el resultado
        assertThat(inventario).isNotNull();
        assertEquals(inventario.size(), inventarioResult.size());
        assertEquals(inventario.get(0), inventarioResult.get(0));
        assertEquals(inventario.get(1), inventarioResult.get(1));
        assertEquals(inventario.get(2), inventarioResult.get(2));
    }
}
