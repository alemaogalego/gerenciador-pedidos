package com.example.gerenciador_pedidos.config;

import com.example.gerenciador_pedidos.exception.CategoriaDivergenteException;
import com.example.gerenciador_pedidos.exception.PrecoDivergenteException;
import com.example.gerenciador_pedidos.service.PedidoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TesteManualRunner implements CommandLineRunner {

    private final PedidoService pedidoService;

    public TesteManualRunner(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Override
    public void run(String... args) {
        try {
            pedidoService.criarPedidoComItem("FOOD", "Smartphone", 1000.0, 1, 1000.0);
        } catch (PrecoDivergenteException | CategoriaDivergenteException e) {
            System.out.println("Erro ao criar pedidos: " + e.getMessage());
        }
}
}