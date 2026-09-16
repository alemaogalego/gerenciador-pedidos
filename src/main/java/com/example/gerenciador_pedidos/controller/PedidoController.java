package com.example.gerenciador_pedidos.controller;

import com.example.gerenciador_pedidos.dto.CriarPedidoRequest;
import com.example.gerenciador_pedidos.dto.ItemPedidoResponse;
import com.example.gerenciador_pedidos.model.ItemPedido;
import com.example.gerenciador_pedidos.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<ItemPedidoResponse> criar(@RequestBody CriarPedidoRequest request) {
        ItemPedido itemPedido = pedidoService.criarPedidoComItem(
                request.nomeCategoria(),
                request.nomeProduto(),
                request.precoProduto(),
                request.quantidade(),
                request.valorUnitario()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(ItemPedidoResponse.fromEntity(itemPedido));
    }


    @GetMapping
    public ResponseEntity<List<ItemPedidoResponse>> listar() {
        List<ItemPedidoResponse> resposta = pedidoService.listarItens().stream()
                .map(ItemPedidoResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoResponse> buscarPorId(@PathVariable Long id) {
        ItemPedido item = pedidoService.buscarItemPorId(id);
        return ResponseEntity.ok(ItemPedidoResponse.fromEntity(item));
    }

}