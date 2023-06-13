package com.coppel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import lombok.Builder;

@Builder
@Entity
@Table(name = "polizas")
public class Polizas {

   // private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpoliza")
    private int id;
    
    @NotNull
    @Column(name = "empleadogenero")
    private int empleadoGenero;

    @NotNull
    @Column(name = "sku")
    private int sku;

    @NotNull
    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "fecha")
    private Date fecha;
    
    public Polizas() {
    }

    public Polizas(int id, int empleadoGenero, int sku, int cantidad, Date fecha) {
        this.id = id;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    

}
