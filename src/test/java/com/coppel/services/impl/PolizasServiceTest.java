package com.coppel.services.impl;

import com.coppel.entities.Polizas;
import com.coppel.repositories.PolizasRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.willDoNothing;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author luis.buelna
 */
public class PolizasServiceTest {
    
    @Mock
    PolizasRepository polizasRepository;
    
    @InjectMocks
    private PolizasService polizasService;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testFindPolizaById(){
        int id = 1;
        Polizas polizaXId = new Polizas();
        polizaXId.setId(id);
        polizaXId.setEmpleadoGenero(3);
        polizaXId.setSku(100);
        polizaXId.setCantidad(25);
        polizaXId.setFecha(Date.valueOf(LocalDate.now()));
        Optional<Polizas> optionalPolizaId = Optional.of(polizaXId);
        
        when(polizasRepository.findById(Long.MIN_VALUE));
    }
}
