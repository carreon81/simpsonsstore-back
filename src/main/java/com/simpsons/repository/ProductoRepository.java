package com.simpsons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simpsons.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    List<Producto> findByNameContainingIgnoreCase(String name);
}
