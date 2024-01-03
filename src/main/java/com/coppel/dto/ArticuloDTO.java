package com.coppel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ArticuloDTO {

    private Long id;

    @Min(value = 1, message = "El sku no puede ser menor a 1")
    private Long sku;

    @NotNull(message = "El nombre no puede ser nulo")
    private String nombre;

    @NotNull(message = "La descripcion no puede ser nulo")
    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo")
    private int precio;
}
