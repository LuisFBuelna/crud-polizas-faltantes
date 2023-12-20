package com.coppel.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "polizas")
public class Polizas {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpoliza")
    private int id;
    
    @NotNull
    @Column(name = "empleadogenero")
    private Integer empleadoGenero;

    @NotNull
    @Column(name = "sku")
    private int sku;

    @NotNull
    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "fecha")
    private String fecha;
    
    public Polizas() {
    }

    public Polizas(int id, int empleadoGenero, int sku, int cantidad, String fecha) {
        this.id = id;
        this.empleadoGenero = empleadoGenero;
        this.sku = sku;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public Polizas(Integer empleadoGenero, int sku, int cantidad, String fecha) {
        this.empleadoGenero = empleadoGenero;
        this.sku = sku;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpleadoGenero() {
        return empleadoGenero;
    }

    public void setEmpleadoGenero(int empleadoGenero) {
        this.empleadoGenero = empleadoGenero;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    

}
