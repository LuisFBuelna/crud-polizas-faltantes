package com.coppel.controllers;

import com.coppel.dto.PolizaDTO;
import com.coppel.entities.Polizas;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class PolizaControllerTest {

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
    void listarTodasLasPolizas() throws Exception {

        ResultActions response = mockMvc.perform(get("/polizas"));

        response.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void listarPolizaPorId() throws Exception {

        ResultActions response = mockMvc.perform(get("/polizas/{id}", 1));

        response.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void listarPolizaPorIdNotFoundException() throws Exception {

        ResultActions response = mockMvc.perform(get("/polizas/{id}", 2130));

        response.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void insertarPoliza() throws Exception {

        PolizaDTO polizaInsertar = new PolizaDTO(2, 9, 2, String.valueOf(LocalDate.now()));

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(polizaInsertar);

        ResultActions response = mockMvc.perform(post("/polizas/insertarPoliza")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empleado").value(polizaInsertar.getEmpleado()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cantidad").value(polizaInsertar.getCantidad()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fecha").value(polizaInsertar.getFecha()));
    }

    @Test
    void updatePoliza() throws Exception {

        PolizaDTO polizaUpdate = new PolizaDTO(1,2, 8, 30, String.valueOf(LocalDate.now()));

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(polizaUpdate);

        ResultActions response = mockMvc.perform(put("/polizas/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empleado").value(polizaUpdate.getEmpleado()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cantidad").value(polizaUpdate.getCantidad()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fecha").value(polizaUpdate.getFecha()));
    }

    @Test
    void deletePoliza() throws Exception {

        ResultActions response = mockMvc.perform(delete("/polizas/delete/{id}", 100));

        response.andDo(print())
                .andExpect(status().isBadRequest());
    }
}