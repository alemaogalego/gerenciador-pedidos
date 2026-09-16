package com.example.gerenciador_pedidos.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    void getId() {
        Produto produto = new Produto(null, "Coca-Cola", 5.0, new Categoria(1L, "Bebidas"));
        assertNull(produto.getId());
    }

    @Test
    void getNome() {
        Produto produto = new Produto(null, "Coca-Cola", 5.0, new Categoria(1L, "Bebidas"));
        assertEquals("Coca-Cola", produto.getNome());
    }

    @Test
    void getPreco() {
        Produto produto = new Produto(null, "Coca-Cola", 5.0, new Categoria(1L, "Bebidas"));
        assertEquals(5.0, produto.getPreco());
    }

    @Test
    void getCategoria() {
        Produto produto = new Produto(null, "Coca-Cola", 5.0, new Categoria(1L, "Bebidas"));
        assertEquals(1L, produto.getCategoria().getId());
        assertEquals("Bebidas", produto.getCategoria().getNome());
    }
}