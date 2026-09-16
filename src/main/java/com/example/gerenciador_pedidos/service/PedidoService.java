package com.example.gerenciador_pedidos.service;

import com.example.gerenciador_pedidos.exception.ItemPedidoNaoEncontradoException;
import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.model.ItemPedido;
import com.example.gerenciador_pedidos.model.Pedido;
import com.example.gerenciador_pedidos.model.Produto;
import com.example.gerenciador_pedidos.repository.CategoriaRepository;
import com.example.gerenciador_pedidos.repository.ItemPedidoRepository;
import com.example.gerenciador_pedidos.repository.PedidoRepository;
import com.example.gerenciador_pedidos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {
    private final CategoriaService categoriaService;
    private final ProdutoService produtoService;
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public List<ItemPedido> listarItens(){
        return itemPedidoRepository.findAll();
    }

    public ItemPedido buscarItemPorId(Long id) {
        return itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNaoEncontradoException("Item de pedido não encontrado com id: " + id));
    }

    public PedidoService(CategoriaService categoriaService, ProdutoService produtoService, PedidoRepository pedidoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.categoriaService = categoriaService;
        this.produtoService = produtoService;
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public ItemPedido criarPedidoComItem(String nomeCategoria, String nomeProduto, Double precoProduto, Integer quantidade, Double valorUnitario) {
        Categoria categoria = categoriaService.buscarOuCriar(nomeCategoria);
        Produto produto = produtoService.buscarOuCriar(nomeProduto, precoProduto, categoria);
        Pedido pedido = pedidoRepository.save(new Pedido(null, LocalDate.now()));
        ItemPedido itemPedido = new ItemPedido(null, pedido, produto, quantidade, valorUnitario);
        return itemPedidoRepository.save(itemPedido);
    }




}
