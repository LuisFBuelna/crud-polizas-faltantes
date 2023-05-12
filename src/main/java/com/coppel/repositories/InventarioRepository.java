package com.coppel.repositories;

import com.coppel.entities.Inventario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventarioRepository extends JpaRepository<Inventario, Long>{
    
    
    @Query(value = "SELECT * FROM fun_consultar_inventario_2()", nativeQuery = true)
    public List<Inventario> verInventario();
    
}
