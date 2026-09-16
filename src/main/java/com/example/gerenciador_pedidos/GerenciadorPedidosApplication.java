package com.example.gerenciador_pedidos;

import com.example.gerenciador_pedidos.model.Categoria;
import com.example.gerenciador_pedidos.model.ItemPedido;
import com.example.gerenciador_pedidos.model.Pedido;
import com.example.gerenciador_pedidos.model.Produto;
import com.example.gerenciador_pedidos.repository.CategoriaRepository;
import com.example.gerenciador_pedidos.repository.ItemPedidoRepository;
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
	private final ItemPedidoRepository itemPedidoRepository;

	public GerenciadorPedidosApplication(ProdutoRepository produtoRepository, PedidoRepository pedidoRepository, CategoriaRepository categoriaRepository, ItemPedidoRepository itemPedidoRepository) {
		this.produtoRepository = produtoRepository;
		this.pedidoRepository = pedidoRepository;
		this.categoriaRepository = categoriaRepository;
		this.itemPedidoRepository = itemPedidoRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorPedidosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria categoriaSalva = categoriaRepository.findByNome("Eletrônicos")
				.orElseGet(() -> categoriaRepository.save(new Categoria(null, "Eletrônicos")));


		Produto produtoSalvo = produtoRepository.findByNome("Notebook")
				.orElseGet(() -> produtoRepository.save(new Produto(null, "Notebook", 1500.0, categoriaSalva)));

		Pedido pedido = new Pedido(null, LocalDate.now());
		Pedido pedidoSalvo = pedidoRepository.save(pedido);

		ItemPedido itemPedido = new ItemPedido(null, pedidoSalvo, produtoSalvo, 5, 1300.00);
		itemPedidoRepository.save(itemPedido);

	}
}