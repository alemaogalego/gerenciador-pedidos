package com.example.gerenciador_pedidos;

import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.model.Pedido;
import com.example.gerenciador_pedidos.model.Produto;
import com.example.gerenciador_pedidos.repository.CategoriaRepository;
import com.example.gerenciador_pedidos.repository.PedidoRepository;
import com.example.gerenciador_pedidos.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class GerenciadorPedidosApplication implements CommandLineRunner {

	private final ProdutoRepository produtoRepository;
	private final PedidoRepository pedidoRepository;
	private final CategoriaRepository categoriaRepository;

	public GerenciadorPedidosApplication(ProdutoRepository produtoRepository, PedidoRepository pedidoRepository, CategoriaRepository categoriaRepository) {
		this.produtoRepository = produtoRepository;
		this.pedidoRepository = pedidoRepository;
		this.categoriaRepository = categoriaRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorPedidosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria categoria = new Categoria(null, "Eletrônicos");
		Categoria categoriaSalva = categoriaRepository.save(categoria);

		Produto produto = new Produto(null, "Smartphone", 1500.0, categoriaSalva);
		produtoRepository.save(produto);

		Pedido pedido = new Pedido(null, LocalDate.now());
		pedidoRepository.save(pedido);

	}
}