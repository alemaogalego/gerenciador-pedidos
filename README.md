# Gerenciador de Pedidos

API REST em **Java 17** e **Spring Boot** para gerenciamento de pedidos, produtos, categorias e fornecedores, com persistência via Spring Data JPA, tratamento de exceções de negócio e testes automatizados em todas as camadas.

> 📚 **Projeto de estudo.** Começou como um **desafio da [Alura](https://www.alura.com.br/)** e foi expandido por conta própria para praticar o que vem depois na trilha de backend Java.

## 📌 Sobre o projeto

Este projeto nasceu como exercício prático do módulo de **Spring Data JPA** da formação Java Backend da Alura. O desafio original pedia:

- modelar as entidades (`Categoria`, `Produto`, `Pedido`, `ItemPedido` e depois `Fornecedor`);
- mapear os relacionamentos entre elas (`@ManyToOne`, `@OneToMany`, `@ManyToMany`);
- validar tudo manualmente pela classe principal, via `CommandLineRunner`.

Usei esse desafio como base e fui além do escopo original, transformando-o numa API REST com regras de negócio, DTOs, tratamento de erros e testes automatizados nas três camadas (repository, service e controller).

## 🚀 O que foi melhorado em relação ao desafio original

- **API REST** (`@RestController`) para criar e consultar pedidos via HTTP, em vez de validar tudo só pelo `CommandLineRunner`.
- **Regras de negócio no service**: categoria, fornecedor e produto são reaproveitados quando já existem ("buscar ou criar"), e a API recusa dados que divergem do que já está cadastrado (preço, categoria ou fornecedor diferentes).
- **Tratamento centralizado de exceções** com `@RestControllerAdvice`, convertendo erros de negócio em respostas HTTP padronizadas (`400`, `404`) em vez de erros genéricos `500`.
- **DTOs** de requisição e resposta, desacoplando o contrato da API do modelo de persistência (inclusive com um campo calculado, `valorTotal`, que não existe no banco).
- **Testes automatizados** em todas as camadas (detalhes abaixo).
- **Código de teste manual** (`CommandLineRunner`) movido para fora da classe principal, separando responsabilidades.

### Relacionamentos JPA: decisões tomadas

| Relacionamento | Mapeamento | Observação |
|---|---|---|
| `Produto` → `Categoria` | `@ManyToOne` | Lado dono do relacionamento (coluna `categoria_id`). |
| `Categoria` → `Produto` | `@OneToMany(mappedBy = "categoria", cascade = PERSIST)` | Torna o relacionamento **bidirecional**. Salvar uma categoria salva seus produtos; usei `PERSIST` em vez de `ALL` para que apagar uma categoria **não** apague produtos que podem estar em pedidos. |
| `Produto` → `Fornecedor` | `@ManyToOne` (unidirecional) | Fornecedor **opcional**, para manter compatibilidade com produtos já cadastrados. |
| `Produto` ↔ `Pedido` | via entidade `ItemPedido` | O desafio sugeria um `@ManyToMany` com `@JoinTable`. **Optei por não usar**, porque o `ItemPedido` já faz o papel de tabela intermediária e ainda guarda `quantidade` e `valorUnitario`, o que um `@ManyToMany` puro não permite. |

## 🛠️ Tecnologias

- Java 17
- Spring Boot 4
- Spring Data JPA / Hibernate
- Spring Web (REST)
- Bean Validation (Jakarta Validation)
- PostgreSQL (aplicação) / H2 (banco em memória para testes)
- JUnit 5 e Mockito
- Lombok
- Maven

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

```
Controller  →  Service  →  Repository  →  Banco de dados
    ↑             ↑
   DTOs     Regras de negócio / Exceptions
```

- **`model`**: entidades JPA (`Categoria`, `Produto`, `Fornecedor`, `Pedido`, `ItemPedido`)
- **`repository`**: interfaces `JpaRepository` para acesso a dados
- **`service`**: regras de negócio ("buscar ou criar" de categoria, fornecedor e produto; validação de divergências)
- **`controller`**: endpoints REST
- **`dto`**: objetos de requisição e resposta da API
- **`exception`**: exceptions customizadas e handler global (`@RestControllerAdvice`)
- **`config`**: `CommandLineRunner` com dados de teste manual

## 📡 Endpoints

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/pedidos` | Cria um pedido com um item |
| `GET` | `/pedidos` | Lista todos os itens de pedido |
| `GET` | `/pedidos/{id}` | Busca um item de pedido por id |

### Exemplo de requisição: `POST /pedidos`

```json
{
  "nomeCategoria": "Eletrônicos",
  "nomeFornecedor": "Samsung",
  "nomeProduto": "Smartphone",
  "precoProduto": 1000.0,
  "quantidade": 1,
  "valorUnitario": 1000.0
}
```

- **`nomeFornecedor`** é opcional.
- **`precoProduto`** é o preço de catálogo do produto. Se o produto já existir, ele precisa bater com o preço cadastrado.
- **`valorUnitario`** é o valor cobrado nesta venda e pode ser diferente do preço de catálogo (por exemplo, com desconto).

### Exemplo de resposta (`201 Created`)

```json
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
```

## ⚠️ Tratamento de erros

Quando os dados enviados não fazem sentido com o que já está cadastrado, a API responde de forma clara em vez de estourar um erro interno:

- **`400 Bad Request`**: o produto já existe com **preço**, **categoria** ou **fornecedor** diferentes do informado.
- **`404 Not Found`**: o item de pedido buscado por id não existe.

```json
{
  "timestamp": "2026-09-16T10:00:00",
  "status": 400,
  "mensagem": "Produto 'Smartphone' já existe com preço 1000.0, mas foi informado 900.0"
}
```

## ✅ Testes

| Camada | Classes | Como |
|---|---|---|
| Model | `CategoriaTest`, `ProdutoTest` | Testes unitários (JUnit 5) |
| Repository | `CategoriaRepositoryTest`, `ProdutoRepositoryTest` | Integração com H2 (`@DataJpaTest`), incluindo o cascade de categoria → produtos e a busca de produtos por fornecedor |
| Service | `CategoriaServiceTest`, `ProdutoServiceTest` | Regras de negócio com Mockito, incluindo os cenários de divergência e produtos antigos sem fornecedor |
| Controller | `PedidoControllerTest` | Requisições HTTP simuladas com MockMvc (`@WebMvcTest`) |

Para rodar todos os testes:

```bash
./mvnw test
```

> O teste `GerenciadorPedidosApplicationTests` sobe o contexto completo e precisa do PostgreSQL configurado (veja abaixo).

## ▶️ Como rodar

### Pré-requisitos

- Java 17+
- PostgreSQL rodando, com um banco chamado **`gerenciador-pedidos`** já criado

### Configuração

A conexão com o banco é lida de variáveis de ambiente:

| Variável | Exemplo |
|---|---|
| `DB_HOST` | `localhost:5432` |
| `DB_USER` | `postgres` |
| `DB_PASSWORD` | `sua-senha` |

As tabelas são criadas automaticamente pelo Hibernate (`ddl-auto=update`).

### Executando

```bash
git clone https://github.com/alemaogalego/gerenciador-pedidos.git
cd gerenciador-pedidos
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`. Ao iniciar, o `TesteManualRunner` cria um pedido de exemplo.

## 🔭 Possíveis próximos passos

- Pedido com **vários itens** (hoje cada chamada cria um pedido com um único item)
- Endpoints próprios para cadastrar categorias, fornecedores e produtos
- Exibir o fornecedor na resposta da API
- Documentação interativa com Swagger (springdoc-openapi)

## 🎯 Contexto

Este é um **projeto de estudo**, construído durante minha transição de carreira para desenvolvimento backend em Java/Spring. A base veio de um desafio da Alura, e as melhorias foram feitas para praticar conceitos que vão além do exercício original: API REST, modelagem de relacionamentos JPA, boas práticas de tratamento de erros e testes automatizados em todas as camadas.
