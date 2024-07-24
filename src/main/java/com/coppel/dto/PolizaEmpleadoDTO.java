package com.coppel.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class PolizaEmpleadoDTO {

    @Id
    @Column(name = "idpoliza")
    private Long id;

    @Embedded
    private EmpleadoToPolizaDTO empleado;

    @Column(name = "id_inventario")
    private int idInventario;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "fecha")
    private String fecha;

    public PolizaEmpleadoDTO() {
    }

    public PolizaEmpleadoDTO(Long id, EmpleadoToPolizaDTO empleado, int idInventario, int cantidad, String fecha) {
        this.id = id;
        this.empleado = empleado;
        this.idInventario = idInventario;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }
}
