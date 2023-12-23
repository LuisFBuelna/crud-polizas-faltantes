package com.coppel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class PolizaDTO {

    private int id;

    @Min(value = 1, message = "El numero de empleado no puede ser menor a 1")
    private int empleado;

    @NotNull(message = "El sku no puede ser nulo")
    private int sku;

    @Min(value = 1, message = "La cantidad no puede ser menor a 1")
    private int cantidad;

    @NotNull(message = "La fecha no puede ser nulo")
    @NotEmpty
    private String fecha;
}
