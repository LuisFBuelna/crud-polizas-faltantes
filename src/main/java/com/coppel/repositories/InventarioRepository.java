package com.coppel.repositories;

import com.coppel.entities.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long>{
    
}
