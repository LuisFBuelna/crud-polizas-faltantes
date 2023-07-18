package com.coppel.repositories;

import com.coppel.entities.Polizas;
import java.sql.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

public interface PolizasRepository extends JpaRepository<Polizas, Long>{
   
    @Query(value = "SELECT FROM fun_insertar_poliza(:empleadogenero, :sku, "
            + ":cantidad, :fecha)", nativeQuery = true)
    public ResponseEntity<Polizas> insertarPoliza(
            @Param("empleadogenero")int empleadoGenero,
            @Param("sku")int sku, 
            @Param("cantidad")int cantidad, 
            @Param("fecha")Date fecha);
    
    @Query(value = "SELECT FROM fun_eliminar_poliza(:id_poliza)", nativeQuery = true)
    public ResponseEntity<Polizas> borrarPoliza(@Param("id_poliza") int id);
    
    @Query(value = "SELECT FROM fun_actualizar_poliza(:idpoliza, :empleadogenero, :sku, "
            + ":cantidad, :fecha)", nativeQuery = true)
    public ResponseEntity<Polizas> actualizarPoliza (
            @Param("idpoliza") int id,
            @Param("empleadogenero") int empleadoGenero,
            @Param("sku") int sku,
            @Param("cantidad") int cantidad,
            @Param("fecha") Date fecha);
    
    @Query(value = "SELECT * FROM polizas where id_poliza = :id_poliza", nativeQuery = true)
    public Optional<Polizas> findPolizaById (@Param("id_poliza") int id);
    
}
