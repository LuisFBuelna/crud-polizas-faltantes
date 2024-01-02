package com.coppel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PolizaDTO {

    private int id;

    @Min(value = 1, message = "El numero de empleado no puede ser menor a 1")
    private int empleado;

    @NotNull(message = "El sku no puede ser nulo")
    private int sku;

    @Min(value = 1, message = "La cantidad no puede ser menor a 1")
    private int cantidad;

    @NotEmpty(message = "La fecha no debe esta vacia")
    @NotNull(message = "La fecha no puede ser nulo")
    private String fecha;

    public PolizaDTO(int empleado, int sku, int cantidad, String fecha) {
        this.empleado = empleado;
        this.sku = sku;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }
}
