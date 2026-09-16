package com.example.gerenciador_pedidos.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaTest {


    @Test
    void getId() {
        Categoria categoria = new Categoria(1L, "Bebidas");

        assertEquals(1L, categoria.getId());
    }

    @Test
    void getNome() {
        Categoria categoria = new Categoria(1L, "Bebidas");

        assertEquals("Bebidas", categoria.getNome());
    }
}