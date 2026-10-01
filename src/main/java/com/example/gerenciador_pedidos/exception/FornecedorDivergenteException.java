package com.example.gerenciador_pedidos.exception;

public class FornecedorDivergenteException extends RuntimeException {
    public FornecedorDivergenteException(String mensagem) {
        super(mensagem);
    }

}
