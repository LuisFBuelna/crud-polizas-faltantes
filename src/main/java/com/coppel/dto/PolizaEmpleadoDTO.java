package com.coppel.dto;

import lombok.Data;

@Data
public class PolizaEmpleadoDTO {

    private int id;

    private int idEmpleado;

    private String nombre;

    private String apellido;

    private int idInventario;

    private int cantidad;

    private String fecha;
}
