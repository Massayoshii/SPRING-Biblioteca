# 📚 API de Biblioteca

API REST desenvolvida em **Java com Spring Boot** para gerenciamento de uma biblioteca. O sistema permite cadastrar e gerenciar usuários, autores, livros e empréstimos, além de possuir autenticação e autorização utilizando **JWT e Spring Security**.

O projeto foi desenvolvido com foco em boas práticas de desenvolvimento de APIs REST, organização em camadas, DTOs, validações, tratamento global de exceções, paginação e controle de acesso por perfil.

---

## 🚀 Tecnologias utilizadas

* ☕ **Java 21**
* 🌱 **Spring Boot**
* 🔐 **Spring Security**
* 🎫 **JWT**
* 🗄️ **PostgreSQL**
* 🔄 **Flyway**
* 🧩 **Spring Data JPA / Hibernate**
* ✅ **Bean Validation**
* 🛠️ **Maven**
* 🏷️ **Lombok**

---

## 📌 Funcionalidades

### 🔐 Autenticação

* Cadastro de usuários
* Login de usuários
* Geração de token JWT
* Autenticação utilizando Bearer Token
* Controle de acesso baseado em roles
* Perfis `USER` e `ADMIN`

### 👤 Usuários

* Listagem de usuários
* Busca de usuário por ID
* Atualização de usuário
* Exclusão de usuário

### ✍️ Autores

* Cadastro de autores
* Listagem paginada
* Busca por ID
* Atualização
* Exclusão

### 📖 Livros

* Cadastro de livros
* Listagem paginada
* Busca por ID
* Busca por título
* Busca por autor
* Consulta de disponibilidade
* Atualização
* Exclusão
* Controle da quantidade disponível

### 📦 Empréstimos

* Realização de empréstimos
* Listagem de empréstimos
* Busca por ID
* Listagem de empréstimos ativos
* Devolução de livros
* Exclusão de empréstimos

---

## 🏗️ Arquitetura

O projeto utiliza uma arquitetura baseada em camadas:

```text
src/main/java/com/example/biblioteca
│
├── config
│   ├── AuthConfig
│   ├── JWTUserData
│   ├── SecurityConfig
│   ├── SecurityFilter
│   └── TokenService
│
├── controller
│   ├── AuthController
│   ├── AutorController
│   ├── EmprestimoController
│   ├── LivroController
│   └── UsuarioController
│
├── dto
│   ├── request
│   └── response
│
├── entity
│   ├── Autor
│   ├── Emprestimo
│   ├── Livro
│   ├── Usuario
│   └── enums
│
├── exception
│   ├── BusinessException
│   ├── EmailAlreadyExistsException
│   ├── GlobalExceptionHandler
│   ├── IsbnAlreadyExistsException
│   └── ResourceNotFoundException
│
├── repository
│   ├── AutorRepository
│   ├── EmprestimoRepository
│   ├── LivroRepository
│   └── UsuarioRepository
│
└── service
    ├── AutorService
    ├── EmprestimoService
    ├── LivroService
    └── UsuarioService
```

### Fluxo da aplicação

```text
Cliente
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
PostgreSQL
```

A autenticação possui um fluxo adicional:

```text
Cliente
   ↓
/auth/login
   ↓
AuthenticationManager
   ↓
JWT
   ↓
Bearer Token
   ↓
SecurityFilter
   ↓
Endpoint protegido
```

---

## 🗄️ Modelo de dados

O banco de dados possui quatro entidades principais:

```text
USUARIOS
   │
   │ 1:N
   ↓
EMPRESTIMOS
   ↑
   │ N:1
   │
LIVROS
   ↑
   │ N:1
   │
AUTORES
```

### Usuário

* `id`
* `nome`
* `email`
* `senha`
* `role`

### Autor

* `id`
* `nome`
* `nacionalidade`

### Livro

* `id`
* `titulo`
* `isbn`
* `ano_publicado`
* `quantidade`
* `autor_id`

### Empréstimo

* `id`
* `data_emprestimo`
* `data_devolucao`
* `devolvido`
* `usuario_id`
* `livro_id`

---

## 🔐 Autorização

A API possui dois níveis de acesso:

| Recurso               | USER | ADMIN |
| --------------------- | :--: | :---: |
| Autenticação          |   ✅  |   ✅   |
| Consultar autores     |   ✅  |   ✅   |
| Gerenciar autores     |   ❌  |   ✅   |
| Consultar livros      |   ✅  |   ✅   |
| Gerenciar livros      |   ❌  |   ✅   |
| Consultar empréstimos |   ✅  |   ✅   |
| Realizar empréstimo   |   ✅  |   ✅   |
| Devolver empréstimo   |   ✅  |   ✅   |
| Excluir empréstimo    |   ❌  |   ✅   |
| Gerenciar usuários    |   ❌  |   ✅   |

Para acessar endpoints protegidos, envie o token JWT no header:

```http
Authorization: Bearer SEU_TOKEN
```

---

# ⚙️ Como executar o projeto

## 1. Pré-requisitos

Antes de executar a aplicação, tenha instalado:

* Java 21 ou superior
* Maven
* PostgreSQL
* Git

---

## 2. Clone o repositório

```bash
git clone <URL_DO_REPOSITORIO>
```

Entre na pasta do projeto:

```bash
cd SPRING-Biblioteca/biblioteca
```

---

## 3. Configure o PostgreSQL

Crie um banco de dados chamado:

```text
biblioteca
```

Exemplo utilizando o PostgreSQL:

```sql
CREATE DATABASE biblioteca;
```

Configure as credenciais do banco no arquivo:

```text
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA
```


---

## 4. Migração do banco

O projeto utiliza **Flyway** para controle das migrações.

A primeira migration está localizada em:

```text
src/main/resources/db/migration/V1__create_biblioteca_schema.sql
```

Ao iniciar a aplicação, o Flyway executará as migrations necessárias.

---

## 5. Execute a aplicação

Utilizando o Maven Wrapper:

### Windows

```bash
./mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

Ou utilizando Maven:

```bash
mvn spring-boot:run
```

A aplicação será iniciada localmente.

```text
http://localhost:8080
```

---

# 📡 Endpoints

## 🔐 Autenticação

### Registrar usuário

```http
POST /auth/registrar
```

Exemplo:

```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456"
}
```

### Login

```http
POST /auth/login
```

Exemplo:

```json
{
  "email": "joao@email.com",
  "senha": "123456"
}
```

O login retorna um token JWT que deve ser utilizado para acessar os endpoints protegidos.

---

# ✍️ Autores

### Criar autor

```http
POST /autores
```

🔒 Requer `ADMIN`.

### Listar autores

```http
GET /autores
```

🔐 Requer autenticação.

Suporta paginação:

```http
GET /autores?page=0&size=5
```

### Buscar autor

```http
GET /autores/{id}
```

### Atualizar autor

```http
PUT /autores/{id}
```

🔒 Requer `ADMIN`.

### Excluir autor

```http
DELETE /autores/{id}
```

🔒 Requer `ADMIN`.

---

# 📖 Livros

### Criar livro

```http
POST /livros
```

🔒 Requer `ADMIN`.

### Listar livros

```http
GET /livros
```

🔐 Requer autenticação.

Exemplo com paginação:

```http
GET /livros?page=0&size=5
```

### Buscar livro por ID

```http
GET /livros/{id}
```

### Buscar por título

```http
GET /livros/titulo?titulo=Java
```

### Buscar livros por autor

```http
GET /livros/autor/{autorId}
```

### Consultar disponibilidade

```http
GET /livros/{id}/disponibilidade
```

### Atualizar livro

```http
PUT /livros/{id}
```

🔒 Requer `ADMIN`.

### Excluir livro

```http
DELETE /livros/{id}
```

🔒 Requer `ADMIN`.

---

# 📦 Empréstimos

### Realizar empréstimo

```http
POST /emprestimos
```

🔐 Requer autenticação.

### Listar empréstimos

```http
GET /emprestimos
```

🔐 Requer autenticação.

### Buscar empréstimo

```http
GET /emprestimos/{id}
```

🔐 Requer autenticação.

### Listar empréstimos ativos

```http
GET /emprestimos/ativos
```

🔐 Requer autenticação.

### Realizar devolução

```http
PUT /emprestimos/{id}/devolucao
```

🔐 Requer autenticação.

### Excluir empréstimo

```http
DELETE /emprestimos/{id}
```

🔒 Requer `ADMIN`.

---

# 👤 Usuários

Os endpoints de gerenciamento de usuários são restritos a administradores.

### Listar usuários

```http
GET /usuarios
```

🔒 Requer `ADMIN`.

### Buscar usuário

```http
GET /usuarios/{id}
```

🔒 Requer `ADMIN`.

### Atualizar usuário

```http
PUT /usuarios/{id}
```

🔒 Requer `ADMIN`.

### Excluir usuário

```http
DELETE /usuarios/{id}
```

🔒 Requer `ADMIN`.

---

# 🧪 Testes

O projeto possui estrutura para testes utilizando o ecossistema de testes do Spring Boot.

Para executar os testes:

```bash
./mvnw test
```

No Windows:

```bash
./mvnw.cmd test
```

---

# 🛡️ Tratamento de exceções

A aplicação possui um tratamento global de exceções através do:

```text
GlobalExceptionHandler
```

Entre as exceções tratadas estão:

* Recurso não encontrado
* E-mail já cadastrado
* ISBN já cadastrado
* Erros de regra de negócio
* Erros de validação

Isso permite que a API retorne respostas HTTP adequadas para diferentes situações.

---

# 📄 DTOs

A aplicação utiliza DTOs para separar os objetos de entrada e saída da API das entidades persistidas no banco.

```text
dto/
├── request/
│   ├── AutorRequest
│   ├── EmprestimoRequest
│   ├── LivroRequest
│   ├── UsuarioLoginRequest
│   └── UsuarioRequest
│
└── response/
    ├── AutorResponse
    ├── EmprestimoResponse
    ├── LivroResponse
    ├── UsuarioLoginResponse
    └── UsuarioResponse
```

Essa abordagem ajuda a evitar a exposição direta das entidades e mantém o contrato da API mais organizado.

---

# 🔄 Migrações com Flyway

As alterações estruturais do banco são controladas pelo Flyway.

As migrations ficam em:

```text
src/main/resources/db/migration
```

Formato utilizado:

```text
V1__create_biblioteca_schema.sql
```

Novas alterações devem seguir a convenção:

```text
V2__descricao_da_alteracao.sql
V3__outra_alteracao.sql
```

---

# 📁 Estrutura do projeto

```text
biblioteca/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/biblioteca/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── exception/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   │
│   │   └── resources/
│   │       ├── db/
│   │       │   └── migration/
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── mvnw
└── mvnw.cmd
```

---

# 🎯 Objetivo do projeto

O projeto tem como objetivo aplicar conceitos de desenvolvimento de APIs REST utilizando o ecossistema Spring, simulando o funcionamento de um sistema de gerenciamento de biblioteca.

Durante o desenvolvimento foram aplicados conceitos como:

* Arquitetura em camadas
* API REST
* CRUD
* Spring Data JPA
* Relacionamentos entre entidades
* DTOs
* Validações
* Tratamento de exceções
* Paginação
* Autenticação
* Autorização
* JWT
* Spring Security
* PostgreSQL
* Versionamento de banco com Flyway

---

# 👨‍💻 Autor

**Massayoshi Bando**

Projeto desenvolvido para fins acadêmicos e de estudo, com foco no desenvolvimento backend utilizando Java e Spring Boot.

---

## ⭐ Considerações

Este projeto está em constante evolução e pode receber novas funcionalidades, melhorias de segurança, testes automatizados e aprimoramentos na arquitetura.

Se este projeto foi útil para você, considere deixar uma ⭐ no repositório!
