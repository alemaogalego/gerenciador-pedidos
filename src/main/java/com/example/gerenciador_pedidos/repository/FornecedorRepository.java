package com.example.gerenciador_pedidos.repository;

import com.example.gerenciador_pedidos.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}
