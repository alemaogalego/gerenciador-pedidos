package com.example.gerenciador_pedidos.controller;

import com.example.gerenciador_pedidos.dto.CriarPedidoRequest;
import com.example.gerenciador_pedidos.model.ItemPedido;
import com.example.gerenciador_pedidos.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<ItemPedido> criar(@RequestBody CriarPedidoRequest request) {
        ItemPedido itemPedido = pedidoService.criarPedidoComItem(
                request.nomeCategoria(),
                request.nomeProduto(),
                request.precoProduto(),
                request.quantidade(),
                request.valorUnitario()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(itemPedido);
    }
}