package com.example.gerenciador_pedidos.service;

import com.example.gerenciador_pedidos.model.Fornecedor;
import com.example.gerenciador_pedidos.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {


    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }


    private final FornecedorRepository fornecedorRepository;

    public Fornecedor buscarOuCriar(String nome) {
        return fornecedorRepository.findByNome(nome)
                .orElseGet(() -> fornecedorRepository.save(new Fornecedor(null, nome)));
    }
}
