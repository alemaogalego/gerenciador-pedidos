package com.example.gerenciador_pedidos.repository;

import com.example.gerenciador_pedidos.model.Categoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void deveSalvarECarregarCategoria() {
        Categoria categoria = new Categoria(null, "Bebidas");

        Categoria salva = categoriaRepository.save(categoria);

        assertTrue(salva.getId() != null);

        Optional<Categoria> encontrada = categoriaRepository.findById(salva.getId());
        assertTrue(encontrada.isPresent());
        assertEquals("Bebidas", encontrada.get().getNome());
    }
}
