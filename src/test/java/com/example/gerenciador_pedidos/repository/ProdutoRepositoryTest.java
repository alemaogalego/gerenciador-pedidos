package com.example.gerenciador_pedidos.repository;

import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.model.Fornecedor;
import com.example.gerenciador_pedidos.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProdutoRepositoryTest {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Test
    void findByNome() {
        Categoria categoria = categoriaRepository.save(new Categoria(null, "Bebidas"));
        produtoRepository.save(new Produto(null, "Coca-Cola", 5.0, categoria));

        Optional<Produto> encontrado = produtoRepository.findByNome("Coca-Cola");

        assertTrue(encontrado.isPresent());
        assertEquals("Coca-Cola", encontrado.get().getNome());
    }
    @Test
    void findByNomeQuandoNaoExiste() {
        Optional<Produto> encontrado = produtoRepository.findByNome("Produto Inexistente");

        assertTrue(encontrado.isEmpty());
    }

    @Test
    void findByFornecedor() {
        Fornecedor fornecedor = fornecedorRepository.save(new Fornecedor(null, "Coca-Cola"));
        Categoria categoria = categoriaRepository.save(new Categoria(null, "Bebidas"));
        produtoRepository.save(new Produto(null, "Coca-Cola", 5.0, fornecedor, categoria));

        List<Produto> encontrados = produtoRepository.findByFornecedor(fornecedor);

        assertEquals(1, encontrados.size());
        assertEquals("Coca-Cola", encontrados.get(0).getNome());
        assertEquals(fornecedor.getId(), encontrados.get(0).getFornecedor().getId());
    }
}