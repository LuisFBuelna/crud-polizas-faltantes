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

    @Autowired
    MockMvc mockMvc;

    Empleado crearEmpleado = new Empleado(77L, "Roberto", "Carlos", "SubGerente Muebles", 1);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    void listarTodosLosEmpleados() throws Exception {

        this.mockMvc.perform(get("/empleados"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void insertarEmpleado() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(crearEmpleado);

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(5)))
                .andReturn();

    }

    @Test
    void actualizarEmpleado() throws Exception {
        long empleadoId = 2L;
        Empleado empleadoUpdate = new Empleado(2L, "Ricardo", "Rocha", "Subgerente de Area 1", 1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(empleadoUpdate);

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
        Empleado empleadoById = new Empleado(1L, "Carlos", "Santibañez", "Gerente de Tienda", 1);

        this.mockMvc.perform(get("/empleados/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}