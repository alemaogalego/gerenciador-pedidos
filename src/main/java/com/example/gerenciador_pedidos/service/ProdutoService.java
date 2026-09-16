package com.example.gerenciador_pedidos.service;

import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.model.Produto;
import com.example.gerenciador_pedidos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    private ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto buscarOuCriar(String nome, Double preco, Categoria categoria){
        return produtoRepository.findByNome(nome)
                .orElseGet(() -> produtoRepository.save(new Produto(null, nome, preco, categoria)));

    }
}
