package com.example.gerenciador_pedidos;

import com.example.gerenciador_pedidos.exception.CategoriaDivergenteException;
import com.example.gerenciador_pedidos.exception.PrecoDivergenteException;
import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.model.ItemPedido;
import com.example.gerenciador_pedidos.model.Pedido;
import com.example.gerenciador_pedidos.model.Produto;
import com.example.gerenciador_pedidos.repository.CategoriaRepository;
import com.example.gerenciador_pedidos.repository.ItemPedidoRepository;
import com.example.gerenciador_pedidos.repository.PedidoRepository;
import com.example.gerenciador_pedidos.repository.ProdutoRepository;
import com.example.gerenciador_pedidos.service.PedidoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class GerenciadorPedidosApplication {
	public static void main(String[] args) {
		SpringApplication.run(GerenciadorPedidosApplication.class, args);
	}
}