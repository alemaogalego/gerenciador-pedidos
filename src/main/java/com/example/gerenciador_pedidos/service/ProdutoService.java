package com.example.gerenciador_pedidos.service;

import com.example.gerenciador_pedidos.exception.CategoriaDivergenteException;
import com.example.gerenciador_pedidos.exception.PrecoDivergenteException;
import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.model.Produto;
import com.example.gerenciador_pedidos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {
    private ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto buscarOuCriar(String nome, Double preco, Categoria categoria){
        Optional<Produto> produtoExistente = produtoRepository.findByNome(nome);

        if(produtoExistente.isPresent()){
            Produto produto = produtoExistente.get();
            if(!produto.getPreco().equals(preco)){
                throw new PrecoDivergenteException("Produto '" + nome + "' já existe com preço " + produto.getPreco() + ", mas foi informado " + preco);
            }

            if(!produto.getCategoria().getId().equals(categoria.getId())) {
                throw new CategoriaDivergenteException("Produto '" + nome + "' já existe na categoria '" + produto.getCategoria().getNome()
                        + "', mas foi informada a categoria '" + categoria.getNome() + "'");
            }
            return produto;
        }
        return produtoRepository.save(new Produto(null, nome, preco, categoria));

    }

}
