package com.example.gerenciador_pedidos.exception;

public class PrecoDivergenteException extends RuntimeException {
    public PrecoDivergenteException(String mensagem) {
        super(mensagem);
    }
}
