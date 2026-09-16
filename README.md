# Gerenciador de Pedidos

API REST desenvolvida em Java 17 e Spring Boot para gerenciamento de pedidos, produtos e categorias, com persistência via Spring Data JPA, tratamento de exceções de negócio e cobertura de testes automatizados em todas as camadas.

📌 Sobre o projeto

Este projeto nasceu como exercício prático do módulo de Spring Data JPA do meu curso de formação Java Backend (Alura). O desafio original pedia a modelagem das entidades (Categoria, Produto, Pedido, ItemPedido) e o uso do Spring Data JPA para persistência, validado manualmente via CommandLineRunner.

Decidi ir além do escopo original e usar esse projeto pra praticar, do zero, tudo que vem depois na trilha de um backend Java: uma API REST completa, tratamento de exceções de negócio, DTOs e testes automatizados nas três camadas da aplicação (repository, service e controller).

🚀 O que foi adicionado além do desafio original

O exercício da Alura cobria a modelagem JPA e a persistência básica. Por conta própria, adicionei:

Camada de API REST (@RestController) para criar e consultar pedidos via HTTP, em vez de validar tudo só pelo CommandLineRunner.
Tratamento centralizado de exceções com @RestControllerAdvice, convertendo erros de negócio (preço divergente, categoria divergente, item não encontrado) em respostas HTTP padronizadas (400, 404) em vez de erros genéricos 500.
DTOs de requisição e resposta, desacoplando o contrato da API do modelo interno de persistência (inclusive com um campo calculado, valorTotal, que não existe no banco).
Suíte de testes automatizados completa:
Testes unitários das entidades (JUnit 5);
Testes de integração dos repositories com banco em memória (@DataJpaTest + H2);
Testes da camada de serviço com mocks (Mockito), cobrindo inclusive os cenários de exceção;
Testes da camada de API com MockMvc (@WebMvcTest), simulando requisições HTTP sem subir o servidor.
Reorganização do código de teste manual (CommandLineRunner) para fora da classe principal (@SpringBootApplication), separando responsabilidades.
🛠️ Tecnologias utilizadas
Java 17
Spring Boot
Spring Data JPA / Hibernate
Spring Web (REST)
Bean Validation (Jakarta Validation)
PostgreSQL (banco de produção) / H2 (banco em memória para testes)
JUnit 5
Mockito
Maven
Lombok
🏗️ Arquitetura

O projeto segue uma arquitetura em camadas tradicional:

Controller  →  Service  →  Repository  →  Banco de dados
↑              ↑
DTOs      Regras de negócio / Exceptions
model: entidades JPA (Categoria, Produto, Pedido, ItemPedido)
repository: interfaces JpaRepository para acesso a dados
service: regras de negócio (busca ou criação de categoria/produto, validação de divergência de preço/categoria)
controller: endpoints REST
dto: objetos de requisição/resposta da API
exception: exceptions customizadas + handler global (@RestControllerAdvice)
📡 Endpoints
Método	Rota	Descrição
POST	/pedidos	Cria um pedido com um item
GET	/pedidos	Lista todos os itens de pedido cadastrados
GET	/pedidos/{id}	Busca um item de pedido específico por id
Exemplo de requisição — POST /pedidos
json
{
"nomeCategoria": "Eletrônicos",
"nomeProduto": "Smartphone",
"precoProduto": 1000.0,
"quantidade": 1,
"valorUnitario": 1000.0
}
Exemplo de resposta
json
{
"id": 9,
"pedidoId": 9,
"dataPedido": "2026-09-16",
"nomeProduto": "Smartphone",
"categoria": "Eletrônicos",
"quantidade": 1,
"valorUnitario": 1000.0,
"valorTotal": 1000.0
}
⚠️ Tratamento de erros

Quando os dados enviados não fazem sentido com o que já está cadastrado, a API responde de forma clara em vez de estourar um erro interno:

400 Bad Request — produto já existe com preço ou categoria diferentes do informado.
404 Not Found — item de pedido buscado por id não existe.
json
{
"timestamp": "2026-09-16T10:00:00",
"status": 400,
"mensagem": "Produto 'Smartphone' já existe com preço 1000.0, mas foi informado 900.0"
}
✅ Testes

O projeto tem testes automatizados nas três camadas:

CategoriaTest, ProdutoTest — testes unitários dos models
CategoriaRepositoryTest, ProdutoRepositoryTest — testes de integração com banco em memória
CategoriaServiceTest, ProdutoServiceTest — testes de regra de negócio com Mockito
PedidoControllerTest — testes da API com MockMvc

Para rodar todos os testes:

bash
mvn test
▶️ Como rodar o projeto
Pré-requisitos
Java 17+
Maven
PostgreSQL rodando localmente (ajuste as credenciais em application.properties)
Passos
bash
git clone <link-do-repositorio>
cd gerenciador-pedidos
mvn spring-boot:run

A aplicação sobe em http://localhost:8080.

🎯 Contexto

Este é um projeto de estudo, construído durante minha transição de carreira para desenvolvimento backend em Java/Spring, com foco em praticar conceitos que vão além do exercício original: API REST, boas práticas de tratamento de erros e testes automatizados em todas as camadas da aplicação.