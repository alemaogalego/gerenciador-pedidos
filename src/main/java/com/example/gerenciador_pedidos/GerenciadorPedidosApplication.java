package com.example.gerenciador_pedidos;

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
public class GerenciadorPedidosApplication implements CommandLineRunner {

	private PedidoService pedidoService;

	public GerenciadorPedidosApplication(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorPedidosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		pedidoService.criarPedidoComItem("Eletrônicos", "Smartphone", 1000.0, 1, 1000.0);

	}
}