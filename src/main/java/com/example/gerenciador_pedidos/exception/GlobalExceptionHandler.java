package com.example.gerenciador_pedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PrecoDivergenteException.class, CategoriaDivergenteException.class})
    public ResponseEntity<ErroResponse> tratarDivergencia(RuntimeException ex) {
        ErroResponse erro = new ErroResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ItemPedidoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarNaoEncontrado(ItemPedidoNaoEncontradoException ex) {
        ErroResponse erro = new ErroResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }
}