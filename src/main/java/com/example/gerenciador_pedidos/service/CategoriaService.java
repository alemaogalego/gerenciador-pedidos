package com.example.gerenciador_pedidos.service;

import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }


    public Categoria buscarOuCriar(String nome) {
        return categoriaRepository.findByNome(nome)
                .orElseGet(() -> categoriaRepository.save(new Categoria(null, nome)));
    }

}
