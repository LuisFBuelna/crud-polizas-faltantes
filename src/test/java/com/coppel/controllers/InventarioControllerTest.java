package com.coppel.controllers;

import com.coppel.entities.Inventario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InventarioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void listarTodoElInventario() throws Exception {

        ResultActions result = mockMvc.perform(get("/inventario"));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void insertarArticulo() throws Exception {

        Inventario inventarioInsertar = new Inventario( 15, 2);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(inventarioInsertar);

        ResultActions result = mockMvc.perform(post("/inventario/insertarArticulo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.cantidad").value(inventarioInsertar.getCantidad()))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.idArticulo").value(inventarioInsertar.getIdArticulo()))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andReturn();
    }

    @Test
    void guardarArticulo() throws Exception {

        Inventario inventarioUpdate = new Inventario(8L, 75, 1);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(inventarioUpdate);

        ResultActions response = mockMvc.perform(put("/inventario/actualizar/{id}", 8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void eliminarArticulo() throws Exception {

        ResultActions response = mockMvc.perform(delete("/inventario/eliminar/{id}", 5));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void listarArticuloPorId() throws Exception {

        ResultActions response = mockMvc.perform(get("/inventario/{id}", 8));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}