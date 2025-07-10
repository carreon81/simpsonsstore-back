package com.simpsons.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simpsons.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
