package com.example.gerenciador_pedidos.service;

import com.example.gerenciador_pedidos.exception.CategoriaDivergenteException;
import com.example.gerenciador_pedidos.exception.FornecedorDivergenteException;
import com.example.gerenciador_pedidos.exception.PrecoDivergenteException;
import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.model.Fornecedor;
import com.example.gerenciador_pedidos.model.Produto;
import com.example.gerenciador_pedidos.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveCriarNovoProdutoQuandoNaoExiste() {
        Categoria categoria = new Categoria(1L, "Bebidas");
        Fornecedor fornecedor = new Fornecedor(1L, "Ambev");
        when(produtoRepository.findByNome("Coca-Cola")).thenReturn(Optional.empty());

        Produto produtoSalvo = new Produto(1L, "Coca-Cola", 5.0, categoria);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

        Produto resultado = produtoService.buscarOuCriar("Coca-Cola", 5.0, categoria, fornecedor);

        assertEquals(1L, resultado.getId());
        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    void deveRetornarProdutoExistenteQuandoPrecoECategoriaIguais() {
        Categoria categoria = new Categoria(1L, "Bebidas");
        Fornecedor fornecedor = new Fornecedor(1L, "Ambev");
        Produto produtoExistente = new Produto(1L, "Coca-Cola", 5.0, categoria);
        when(produtoRepository.findByNome("Coca-Cola")).thenReturn(Optional.of(produtoExistente));

        Produto resultado = produtoService.buscarOuCriar("Coca-Cola", 5.0, categoria, fornecedor);

        assertSame(produtoExistente, resultado);
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveLancarPrecoDivergenteExceptionQuandoPrecoDiferente() {
        Categoria categoria = new Categoria(1L, "Bebidas");
        Fornecedor fornecedor = new Fornecedor(1L, "Ambev");
        Produto produtoExistente = new Produto(1L, "Coca-Cola", 5.0, categoria);
        when(produtoRepository.findByNome("Coca-Cola")).thenReturn(Optional.of(produtoExistente));

        assertThrows(PrecoDivergenteException.class, () ->
                produtoService.buscarOuCriar("Coca-Cola", 6.0, categoria, fornecedor));

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveLancarCategoriaDivergenteExceptionQuandoCategoriaDiferente() {
        Categoria categoriaOriginal = new Categoria(1L, "Bebidas");
        Fornecedor fornecedor = new Fornecedor(1L, "Ambev");
        Categoria categoriaDiferente = new Categoria(2L, "Doces");
        Produto produtoExistente = new Produto(1L, "Coca-Cola", 5.0, categoriaOriginal);
        when(produtoRepository.findByNome("Coca-Cola")).thenReturn(Optional.of(produtoExistente));

        assertThrows(CategoriaDivergenteException.class, () ->
                produtoService.buscarOuCriar("Coca-Cola", 5.0, categoriaDiferente, fornecedor));

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveLancarFornecedorDivergenteExceptionQuandoFornecedorDiferente() {
        Categoria categoria = new Categoria(1L, "Bebidas");
        Fornecedor fornecedorOriginal = new Fornecedor(1L, "Ambev");
        Fornecedor fornecedorDiferente = new Fornecedor(2L, "Heineken");
        Produto produtoExistente = new Produto(1L, "Coca-Cola", 5.0, fornecedorOriginal, categoria);
        when(produtoRepository.findByNome("Coca-Cola")).thenReturn(Optional.of(produtoExistente));

        assertThrows(FornecedorDivergenteException.class, () ->
                produtoService.buscarOuCriar("Coca-Cola", 5.0, categoria, fornecedorDiferente));

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveRetornarProdutoExistenteSemFornecedorSemLancarExcecao() {
        Categoria categoria = new Categoria(1L, "Bebidas");
        Fornecedor fornecedor = new Fornecedor(1L, "Ambev");
        Produto produtoAntigo = new Produto(1L, "Coca-Cola", 5.0, categoria);
        when(produtoRepository.findByNome("Coca-Cola")).thenReturn(Optional.of(produtoAntigo));

        Produto resultado = produtoService.buscarOuCriar("Coca-Cola", 5.0, categoria, fornecedor);

        assertSame(produtoAntigo, resultado);
    }
}