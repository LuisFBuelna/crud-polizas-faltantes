package com.coppel.repositories;

import com.coppel.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{
    
}
