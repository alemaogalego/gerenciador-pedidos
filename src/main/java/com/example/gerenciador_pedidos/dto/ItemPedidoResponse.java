package com.example.gerenciador_pedidos.dto;

import com.example.gerenciador_pedidos.model.ItemPedido;

import java.time.LocalDate;

public record ItemPedidoResponse(
        Long id,
        Long pedidoId,
        LocalDate dataPedido,
        String nomeProduto,
        String categoria,
        Integer quantidade,
        Double valorUnitario,
        Double valorTotal
) {
    public static ItemPedidoResponse fromEntity(ItemPedido item) {
        return new ItemPedidoResponse(
                item.getId(),
                item.getPedido().getId(),
                item.getPedido().getData(),
                item.getProduto().getNome(),
                item.getProduto().getCategoria().getNome(),
                item.getQuantidade(),
                item.getValorUnitario(),
                item.getQuantidade() * item.getValorUnitario()
        );
    }
}