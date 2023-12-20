package com.coppel.controllers;

import com.coppel.entities.Empleado;
import com.coppel.repositories.EmpleadoRepository;
import com.coppel.services.impl.EmpleadoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmpleadoControllerTest {

    @MockBean
    EmpleadoService empleadoService;

    @InjectMocks
    EmpleadoController empleadoController;

    @Autowired
    MockMvc mockMvc;

    Empleado empleadoUno;
    Empleado empleadoDos;
    List<Empleado> listaEmpleados = new ArrayList<>();

    Empleado crearEmpleado = new Empleado(77L, "Roberto", "Carlos", "SubGerente Muebles");


    @BeforeEach
    void setUp() {
        empleadoUno = new Empleado("Ernesto", "No Feliz", "Auxiliar");
        empleadoDos = new Empleado(21L, "Roberto", "Castañeda", "SubGerente");
        listaEmpleados.add(empleadoUno);
        listaEmpleados.add(empleadoDos);


        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    void listarTodosLosEmpleados() throws Exception {

        Empleado empleadoGet1 = new Empleado(2L, "Ernesto", "No Feliz", "Auxiliar");
        Empleado empleadoGet2 = new Empleado(3L, "Esteban", "Valenzuela", "Auxiliar");
        Empleado empleadoGet3 = new Empleado(4L, "Rocio", "Madrid", "Gerente");
        Empleado empleadoById = new Empleado(1L, "Carlos", "Santibañez", "Gerente de Tienda");

        List<Empleado> empleados = new ArrayList<>();
        empleados.add(empleadoGet1);
        empleados.add(empleadoGet2);
        empleados.add(empleadoGet3);
        empleados.add(empleadoById);

        when(empleadoService.getAllEmpleados()).thenReturn(empleados);

        this.mockMvc.perform(get("/empleados"))
                .andDo(print()).andExpect(status().isOk());

        //Verificar el resultado
        verify(empleadoService, times(1)).getAllEmpleados();


    }

    @Test
    void insertarEmpleado() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(crearEmpleado);

        //Pruebas unitarias
        when(empleadoService.create(crearEmpleado)).thenReturn(crearEmpleado);

        Empleado crearController = empleadoController.insertarEmpleado(crearEmpleado).getBody();

        //Verificar service
        assertEquals(crearEmpleado, crearController);
        assertEquals(crearEmpleado.getNombre(), crearController.getNombre());
        assertEquals(crearEmpleado.getApellido(), crearController.getApellido());
        assertEquals(crearEmpleado.getPuesto(), crearController.getPuesto());

        //Pruebas de integracion
        ResultActions response = this.mockMvc.perform(MockMvcRequestBuilders.post("/empleados/insertar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(crearEmpleado.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.puesto").value(crearEmpleado.getPuesto()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(4)))
                //.andExpect(MockMvcResultMatchers.content().json("{\"id\":34,\"nombre\":\"Ernesto\",\"apellido\":\"No Feliz\",\"puesto\":\"Auxiliar\"}"))
                .andReturn();

    }

    @Test
    void actualizarEmpleado() throws Exception {
        long empleadoId = 1L;
        Empleado empleadoGuardado = new Empleado("Armando", "Hoyos", "Subgerente de Area");

        Empleado empleadoUpdate = new Empleado(1L, "Ricardo", "Rocha", "Subgerente de Area 1");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(empleadoUpdate);

        given(empleadoService.findEmpleadoById(empleadoId)).willReturn(Optional.of(empleadoGuardado));
        given(empleadoService.save(any(Empleado.class)))
                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        ResultActions response = mockMvc.perform(put("/empleados/actualizar/{id}", empleadoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));


        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre").value(empleadoUpdate.getNombre()))
                .andExpect(jsonPath("$.apellido").value(empleadoUpdate.getApellido()))
                .andExpect(jsonPath("$.puesto").value(empleadoUpdate.getPuesto()));

    }

    @Test
    void eliminarEmpleado() throws Exception {

        ResultActions response = mockMvc.perform(delete("/empleados/eliminar/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @SneakyThrows
    @Test
    void listarEmpleadoPorId() throws Exception {
        Empleado empleadoById = new Empleado(1L, "Carlos", "Santibañez", "Gerente de Tienda");

        when(empleadoService.findEmpleadoById(1L))
                .thenReturn(Optional.of(empleadoById));

        this.mockMvc.perform(get("/empleados/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(empleadoService, times(1)).findEmpleadoById(1L);
    }
}