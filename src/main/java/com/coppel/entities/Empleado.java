package com.coppel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "empleado")
public class Empleado implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idempleado")
    private Long id;
    
    @NotNull
    @Column(name = "nombre")
    private String nombre;
    
    @NotNull
    @Column(name = "apellido")
    private String apellido;
    
    @NotNull
    @Column(name = "puesto")
    private String puesto;

    @NotNull
    @Column(name = "estatus")
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Empleado(Long id, String nombre, String apellido, String puesto, int status) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.puesto = puesto;
        this.status = status;
    }

    public Empleado(String nombre, String apellido, String puesto, int status) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.puesto = puesto;
        this.status = status;
    }
}
