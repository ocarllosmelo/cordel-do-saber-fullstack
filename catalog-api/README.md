# 📚 Cordel do Saber API

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)

API RESTful desenvolvida para o gerenciamento de catálogo de livros de uma livraria. O projeto aplica as melhores práticas de desenvolvimento moderno, focando em organização arquitetural, tratamento de erros e validação de dados.

---

## 🚀 Tecnologias Utilizadas

- **Java 21** (LTS)
- **Spring Boot 3** (Framework principal)
- **Spring Data JPA** (Persistência de dados)
- **PostgreSQL** (Banco de dados relacional)
- **Lombok** (Redução de boilerplate)
- **Bean Validation** (Validação de dados de entrada)
- **Maven** (Gerenciamento de dependências)

## 🏛️ Arquitetura do Projeto

O projeto segue a arquitetura em camadas (Layered Architecture) para garantir a separação de responsabilidades:

* **Controller Layer:** Responsável por expor os endpoints REST e lidar com as requisições HTTP.
* **Service Layer:** Contém as regras de negócio e validações lógicas.
* **Repository Layer:** Interface de comunicação com o banco de dados (via Spring Data JPA).
* **Model/Entity:** Representação das tabelas do banco.
* **DTO (Data Transfer Object):** Padrão utilizado para trafegar dados entre o cliente e a API, protegendo a entidade interna.
* **Exception Handler:** Tratamento centralizado de erros, retornando respostas HTTP adequadas (404, 422, etc.) em vez de erros genéricos.

---

## 🛠️ Funcionalidades (CRUD Completo)

### 📖 Livros

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/livros` | Lista todos os livros cadastrados. |
| `GET` | `/livros/{id}` | Busca um livro específico pelo ID. |
| `POST` | `/livros` | Cadastra um novo livro (com validação de dados). |
| `PUT` | `/livros/{id}` | Atualiza os dados de um livro existente. |
| `DELETE` | `/livros/{id}` | Remove um livro do catálogo. |

### 🛡️ Exemplos de Validação e Erros

A API está blindada contra dados inconsistentes:

* **Cadastro com preço negativo:** Retorna `422 Unprocessable Entity` com mensagem detalhada.
* **Busca por ID inexistente:** Retorna `404 Not Found` com mensagem amigável.

---

## ▶️ Como Rodar o Projeto

### Pré-requisitos
* Java 21 instalado.
* PostgreSQL instalado e rodando.
* Maven (opcional, o wrapper `mvnw` já está incluso).

### Configuração do Banco de Dados
1. Crie um banco de dados no PostgreSQL chamado `catalog_api_db`.
2. Configure suas credenciais no arquivo `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha


## 👨‍💻 Autor

Desenvolvido por **Carlos Melo**.

* [LinkedIn](https://www.linkedin.com/in/carlos-alberto-2a35932bb)
* [GitHub](https://github.com/ocarllosmelo)