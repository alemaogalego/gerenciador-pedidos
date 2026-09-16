package com.example.gerenciador_pedidos.controller;

import com.example.gerenciador_pedidos.exception.ItemPedidoNaoEncontradoException;
import com.example.gerenciador_pedidos.exception.PrecoDivergenteException;
import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.model.ItemPedido;
import com.example.gerenciador_pedidos.model.Pedido;
import com.example.gerenciador_pedidos.model.Produto;
import com.example.gerenciador_pedidos.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;

    @Test
    void deveCriarPedidoERetornar201() throws Exception {
        Categoria categoria = new Categoria(1L, "Eletrônicos");
        Produto produto = new Produto(2L, "Smartphone", 1000.0, categoria);
        Pedido pedido = new Pedido(9L, LocalDate.now());
        ItemPedido itemPedido = new ItemPedido(9L, pedido, produto, 1, 1000.0);

        when(pedidoService.criarPedidoComItem(any(), any(), any(), any(), any()))
                .thenReturn(itemPedido);

        String jsonRequisicao = """
                {
                  "nomeCategoria": "Eletrônicos",
                  "nomeProduto": "Smartphone",
                  "precoProduto": 1000.0,
                  "quantidade": 1,
                  "valorUnitario": 1000.0
                }
                """;

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequisicao))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeProduto").value("Smartphone"))
                .andExpect(jsonPath("$.valorTotal").value(1000.0));
    }

    @Test
    void deveRetornar400QuandoPrecoDivergente() throws Exception {
        when(pedidoService.criarPedidoComItem(any(), any(), any(), any(), any()))
                .thenThrow(new PrecoDivergenteException("Preço divergente"));

        String jsonRequisicao = """
                {
                  "nomeCategoria": "Eletrônicos",
                  "nomeProduto": "Smartphone",
                  "precoProduto": 900.0,
                  "quantidade": 1,
                  "valorUnitario": 900.0
                }
                """;

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequisicao))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void deveListarItens() throws Exception {
        Categoria categoria = new Categoria(1L, "Eletrônicos");
        Produto produto = new Produto(2L, "Smartphone", 1000.0, categoria);
        Pedido pedido = new Pedido(9L, LocalDate.now());
        ItemPedido itemPedido = new ItemPedido(9L, pedido, produto, 1, 1000.0);

        when(pedidoService.listarItens()).thenReturn(List.of(itemPedido));

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeProduto").value("Smartphone"));
    }

    @Test
    void deveRetornar404QuandoItemNaoExiste() throws Exception {
        when(pedidoService.buscarItemPorId(999L))
                .thenThrow(new ItemPedidoNaoEncontradoException("não encontrado"));

        mockMvc.perform(get("/pedidos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}