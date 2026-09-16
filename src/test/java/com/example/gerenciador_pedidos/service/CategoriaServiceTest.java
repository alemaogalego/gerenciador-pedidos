package com.example.gerenciador_pedidos.service;

import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void deveRetornarCategoriaExistenteQuandoJaExiste() {
        Categoria categoriaExistente = new Categoria(1L, "Bebidas");
        when(categoriaRepository.findByNome("Bebidas")).thenReturn(Optional.of(categoriaExistente));

        Categoria resultado = categoriaService.buscarOuCriar("Bebidas");

        assertEquals(1L, resultado.getId());
        assertEquals("Bebidas", resultado.getNome());
        verify(categoriaRepository, never()).save(any());
    }

    @Test
    void deveCriarNovaCategoriaQuandoNaoExiste() {
        when(categoriaRepository.findByNome("Doces")).thenReturn(Optional.empty());

        Categoria categoriaNova = new Categoria(2L, "Doces");
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaNova);

        Categoria resultado = categoriaService.buscarOuCriar("Doces");

        assertEquals(2L, resultado.getId());
        assertEquals("Doces", resultado.getNome());
        verify(categoriaRepository).save(any(Categoria.class));
    }
}