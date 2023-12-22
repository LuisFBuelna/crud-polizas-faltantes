package com.coppel.repositories;

import com.coppel.entities.Polizas;
import java.sql.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PolizasRepository extends JpaRepository<Polizas, Long>{
   
    @Query(value = "SELECT ridpoliza as idpoliza, rempleadogenero as empleadogenero, rsku as sku, rcantidad as cantidad, rfecha as fecha" +
            " FROM fun_insertar_poliza(:empleadogenero, :sku, "
            + ":cantidad, :fecha)", nativeQuery = true)
    public Polizas insertarPoliza(
            @Param("empleadogenero")int empleadoGenero,
            @Param("sku")int sku, 
            @Param("cantidad")int cantidad, 
            @Param("fecha")Date fecha);
    
    @Query(value = "SELECT fun_eliminar_poliza(:id_poliza)", nativeQuery = true)
    public void borrarPoliza(@Param("id_poliza") int id);
    
    @Query(value = "SELECT ridpoliza as idpoliza, rempleadogenero as empleadogenero, rsku as sku, rcantidad as cantidad, rfecha as fecha" +
            " FROM fun_actualizar_poliza(:idpoliza, :empleadogenero, :sku, "
            + ":cantidad, :fecha)", nativeQuery = true)
    public Polizas actualizarPoliza (
            @Param("idpoliza") int id,
            @Param("empleadogenero") int empleadoGenero,
            @Param("sku") int sku,
            @Param("cantidad") int cantidad,
            @Param("fecha") Date fecha);

}
