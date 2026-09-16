package com.example.gerenciador_pedidos.exception;

import java.time.LocalDateTime;

public record ErroResponse(LocalDateTime timestamp, int status, String mensagem) {}
