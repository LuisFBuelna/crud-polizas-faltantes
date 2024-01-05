package com.coppel.repositories;

import com.coppel.dto.PolizaEmpleadoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PolizaEmpleadoDTORepository extends JpaRepository<PolizaEmpleadoDTO, Long> {

    @Query(value = "SELECT * FROM fun_listar_poliza_empleado()", nativeQuery = true)
    public List<PolizaEmpleadoDTO> listarPolizaEmpleado();

    @Query(value = "SELECT ridpoliza as idpoliza, rempleado_genero as empleado_genero, " +
            "rnombre_empleado as nombre_empleado, rapellido_empleado as apellido_empleado," +
            "rid_inventario as id_inventario, rcantidad as cantidad, rfecha as fecha " +
            "FROM fun_listar_poliza_empleado_por_id(:id)", nativeQuery = true)
    public PolizaEmpleadoDTO listarPolizaEmpleadoPorId(@Param("id") int idPoliza);
}
