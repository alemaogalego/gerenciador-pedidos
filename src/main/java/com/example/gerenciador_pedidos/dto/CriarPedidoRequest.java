package com.example.gerenciador_pedidos.dto;

public record CriarPedidoRequest(
        String nomeCategoria,
        String nomeFornecedor,
        String nomeProduto,
        Double precoProduto,
        Integer quantidade,
        Double valorUnitario
) {}