package com.coppel.entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "polizas")
public class Polizas {

   // private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpoliza")
    private Long id;
    
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

    public Polizas(Long id, int empleadoGenero, int sku, int cantidad, Date fecha) {
        this.id = id;
        this.empleadoGenero = empleadoGenero;
        this.sku = sku;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
