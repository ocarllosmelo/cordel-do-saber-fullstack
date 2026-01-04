# 📚 Cordel do Saber - Fullstack System

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)

Aplicação **Fullstack** desenvolvida para o gerenciamento de catálogo de livros de uma livraria. O projeto une a robustez de uma API Java Spring Boot com a interatividade de uma interface React moderna, aplicando uma identidade visual temática inspirada na **Literatura de Cordel**.

---

## 🚀 Tecnologias Utilizadas

### ☕ Back-end (API)
- **Java 21** (LTS)
- **Spring Boot 3** (Framework principal)
- **Spring Data JPA** (Persistência de dados)
- **PostgreSQL** (Banco de dados relacional)
- **Bean Validation** (Validação de dados de entrada)
- **Maven** (Gerenciamento de dependências)

### ⚛️ Front-end (Web)
- **React.js** (Biblioteca de interfaces)
- **Vite** (Build tool de alta performance)
- **JavaScript ES6+** (Lógica assíncrona com Fetch API)
- **CSS Modules & Grid** (Layout responsivo estilo "Estante de Livros")

---

## 🏛️ Arquitetura do Projeto

O projeto segue uma arquitetura moderna separando **Back-end** (Lógica) e **Front-end** (Visual):

* **Controller Layer (Java):** Responsável por expor os endpoints REST e lidar com as requisições HTTP.
* **Repository Layer (Java):** Interface de comunicação com o banco de dados (via Spring Data JPA).
* **Model/Entity (Java):** Representação das tabelas do banco de dados.
* **SPA (React):** O Front-end consome a API via requisições assíncronas (`fetch`), gerenciando o estado da tela (`useState`) e desenhando os componentes dinamicamente.
* **CORS Configuration:** Configuração de segurança para permitir a comunicação entre o Front e o Back.

---

## 🛠️ Funcionalidades (CRUD Integrado)

O sistema permite a gestão completa do acervo através da interface gráfica:

| Funcionalidade | Descrição |
|---|---|
| **Listagem Visual** | Exibição dos livros em formato de cards (Grid Layout Responsivo). |
| **Busca Rápida** | Filtro em tempo real por Título ou Autor. |
| **Cadastro** | Formulário para inserção de novos títulos com validação. |
| **Edição** | Atualização de preços e dados com preenchimento automático. |
| **Exclusão** | Remoção de livros do catálogo com confirmação de segurança. |

---

## ▶️ Como Rodar o Projeto

Como é um projeto Fullstack, precisamos rodar o servidor (Back) e o site (Front) simultaneamente.

### Pré-requisitos
* Java 21 instalado.
* Node.js e NPM instalados.
* PostgreSQL instalado e rodando.

### 1. Configuração do Banco de Dados
Crie um banco de dados no PostgreSQL chamado `catalog_db` e configure suas credenciais no arquivo `src/main/resources/application.properties` dentro da pasta `catalog-api`.

### 2. Iniciando o Back-end
No terminal, entre na pasta da API:
```bash
cd catalog-api
./mvnw spring-boot:run