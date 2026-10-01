package com.example.gerenciador_pedidos.repository;

import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void deveSalvarECarregarCategoria() {
        Categoria categoria = new Categoria(null, "Bebidas");

        Categoria salva = categoriaRepository.save(categoria);

        assertTrue(salva.getId() != null);

        Optional<Categoria> encontrada = categoriaRepository.findById(salva.getId());
        assertTrue(encontrada.isPresent());
        assertEquals("Bebidas", encontrada.get().getNome());
    }

    @Test
    void deveSalvarProdutosEmCascataAoSalvarCategoria() {
        Categoria categoria = new Categoria(null, "Bebidas");
        Produto produto = new Produto(null, "Coca-Cola", 5.0, categoria);
        categoria.getProdutos().add(produto);

        categoriaRepository.save(categoria);

        Optional<Produto> encontrado = produtoRepository.findByNome("Coca-Cola");
        assertTrue(encontrado.isPresent());
        assertNotNull(encontrado.get().getId());
        assertEquals("Bebidas", encontrado.get().getCategoria().getNome());
    }
}
