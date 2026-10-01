package com.example.gerenciador_pedidos.repository;

import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.model.Fornecedor;
import com.example.gerenciador_pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByNome(String nome);

    List<Produto> findByFornecedor(Fornecedor fornecedor);

}
