package com.coppel.controllers;

import com.coppel.entities.Empleado;
import com.coppel.services.impl.EmpleadoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WebMvcTest
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmpleadoService empleadoService;
    Empleado empleadoUno;
    Empleado empleadoDos;
    List<Empleado> listaEmpleados = new ArrayList<>();


    @BeforeEach
    void setUp() {
        empleadoUno = new Empleado(20L, "Carlos", "Santibañez", "Gerente de Tienda");
        empleadoDos = new Empleado(21L, "Roberto", "Castañeda", "SubGerente");
        listaEmpleados.add(empleadoUno);
        listaEmpleados.add(empleadoDos);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void listarTodosLosEmpleados() {

    }

    @Test
    void insertarEmpleado() {
    }

    @Test
    void guardarEmpleado() {
    }

    @Test
    void eliminarEmpleado() {
    }

    @Test
    void listarEmpleadoPorId() {
        when(empleadoService.findEmpleadoById(20L))
                .thenReturn(Optional.ofNullable(empleadoUno));
    }
}