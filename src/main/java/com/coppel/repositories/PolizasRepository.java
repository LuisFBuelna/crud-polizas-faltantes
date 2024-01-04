package com.coppel.repositories;

import com.coppel.dto.PolizaDTO;
import com.coppel.dto.PolizaEmpleadoDTO;
import com.coppel.entities.Polizas;
import java.sql.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PolizasRepository extends JpaRepository<Polizas, Long>{

    @Query(value = "SELECT p.idpoliza, p.empleado_genero, empleado.nombre as nombre_empleado, empleado.apellido as apellido_empleado,p.id_inventario, p.cantidad, p.fecha \n" +
            "FROM polizas p\n" +
            "JOIN empleado ON p.empleado_genero = empleado.idempleado;", nativeQuery = true)
    public PolizaEmpleadoDTO listarPolizaEmpleado();
   
    @Query(value = "SELECT ridpoliza as idpoliza, rempleadogenero as empleado_genero, rid_inventario as id_inventario, rcantidad as cantidad, rfecha as fecha" +
            " FROM fun_insertar_poliza(:empleado_genero, :id_inventario, "
            + ":cantidad, :fecha)", nativeQuery = true)
    public Polizas insertarPoliza(
            @Param("empleado_genero")int empleado,
            @Param("id_inventario")int idInventario,
            @Param("cantidad")int cantidad, 
            @Param("fecha")Date fecha);
    
    @Query(value = "SELECT fun_eliminar_poliza(:id_poliza)", nativeQuery = true)
    public void borrarPoliza(@Param("id_poliza") int id);
    
    @Query(value = "SELECT ridpoliza as idpoliza, rempleadogenero as empleado_genero, rid_inventario as id_inventario, rcantidad as cantidad, rfecha as fecha" +
            " FROM fun_actualizar_poliza(:idpoliza, :empleado_genero, :id_inventario, "
            + ":cantidad, :fecha)", nativeQuery = true)
    public Polizas actualizarPoliza (
            @Param("idpoliza") int id,
            @Param("empleado_genero") int empleado,
            @Param("id_inventario") int idInventario,
            @Param("cantidad") int cantidad,
            @Param("fecha") Date fecha);

}
