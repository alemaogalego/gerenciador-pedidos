package com.example.gerenciador_pedidos.exception;

public class ItemPedidoNaoEncontradoException extends RuntimeException {
    public ItemPedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}