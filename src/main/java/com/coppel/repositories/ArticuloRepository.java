package com.coppel.repositories;

import com.coppel.entities.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloRepository extends JpaRepository <Articulo, Long>{
}
