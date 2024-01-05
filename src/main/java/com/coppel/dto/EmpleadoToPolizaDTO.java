package com.coppel.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
public class EmpleadoToPolizaDTO {

    @Column(name = "empleado_genero")
    private int idEmpleado;

    @Column(name = "nombre_empleado")
    private String nombreEmpleado;

    @Column(name = "apellido_empleado")
    private String apellidoEmpleado;
}
