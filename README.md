# 🩺 VollMed API – Spring Boot

API REST desenvolvida em **Java + Spring Boot** para o gerenciamento de **médicos** e **pacientes** da plataforma Voll.med.
O projeto implementa CRUD completo, soft delete, validações, migrations com Flyway e integração com banco de dados MySQL.

---

## 📌 Sumário

* [Visão Geral](#-visão-geral)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Arquitetura do Projeto](#-arquitetura-do-projeto)
* [Modelagem das Entidades](#-modelagem-das-entidades)
* [Migrations (Flyway)](#-migrations-flyway)
* [Endpoints da API](#-endpoints-da-api)
* [Como Executar o Projeto](#-como-executar-o-projeto)
* [Testes](#-testes)
* [Sugestões de Melhorias](#-sugestões-de-melhorias)
* [Licença](#-licença)

---

## 💡 Visão Geral

A **VollMed API** oferece uma estrutura simples, robusta e organizada para realizar operações de cadastro e gerenciamento de profissionais de saúde e pacientes.
O projeto foi desenvolvido com foco em:

* Boas práticas de REST
* Validações com Java Bean Validation
* Mapeamento JPA/Hibernate
* Migrations automáticas com Flyway
* Separação clara de responsabilidades (Controller, DTOs, Repository, Entity)

Funcionalidades principais:

✔ Cadastro de médicos
✔ Cadastro de pacientes
✔ Listagem paginada
✔ Atualização parcial
✔ Soft delete usando campo `ativo`
✔ Validações personalizadas
✔ Versionamento do banco com Flyway

---

## 🛠 Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 4**

  * Web MVC
  * Data JPA
  * Bean Validation
  * Flyway
* **MySQL** (via `mysql-connector-j`)
* **Hibernate**
* **Lombok**
* **Maven**

Dependências principais definidas no `pom.xml`.

---

## 🏗 Arquitetura do Projeto

Organização de pacotes:

```text
med.voll.api
 ├─ ApiApplication.java
 ├─ controller
 │   ├─ MedicoController.java
 │   └─ PacienteController.java
 ├─ medico
 │   ├─ Medico.java
 │   ├─ MedicoRepository.java
 │   ├─ Especialidade.java
 │   ├─ DadosCadastroMedico.java
 │   ├─ DadosAtualizacaoMedico.java
 │   ├─ DadosListagemMedico.java
 │   └─ DadosDetalhamentoMedico.java
 ├─ paciente
 │   ├─ Paciente.java
 │   ├─ PacienteRepository.java
 │   ├─ DadosCadastroPaciente.java
 │   ├─ DadosAtualizacaoPaciente.java
 │   ├─ DadosListagemPaciente.java
 │   └─ DadosDetalhamentoPaciente.java
 └─ endereco
     ├─ Endereco.java
     └─ DadosEndereco.java
```

### ✔ Camadas

| Camada             | Função                                                                 |
| ------------------ | ---------------------------------------------------------------------- |
| **Controller**     | Recebe e retorna dados via REST                                        |
| **DTOs (records)** | Entrada e saída da API (cadastro, listagem, atualização, detalhamento) |
| **Service**        | Regras de negócio (não utilizado ainda, mas recomendado futuramente)   |
| **Repository**     | Acesso ao banco via Spring Data JPA                                    |
| **Entity**         | Mapeamento JPA das tabelas                                             |

---

## 🧬 Modelagem das Entidades

### 👨‍⚕️ Médico (`Medico`)

| Campo           | Tipo                                       |
| --------------- | ------------------------------------------ |
| `id`            | Long                                       |
| `nome`          | String                                     |
| `email`         | String                                     |
| `telefone`      | String                                     |
| `crm`           | String                                     |
| `especialidade` | Enum (`CARDIOLOGIA`, `DERMATOLOGIA`, etc.) |
| `endereco`      | Embeddable (`Endereco`)                    |
| `ativo`         | Boolean (soft delete)                      |

### 🧑‍🦽 Paciente (`Paciente`)

| Campo      | Tipo                    |
| ---------- | ----------------------- |
| `id`       | Long                    |
| `nome`     | String                  |
| `email`    | String                  |
| `telefone` | String                  |
| `cpf`      | String                  |
| `endereco` | Embeddable (`Endereco`) |
| `ativo`    | Boolean                 |

### 📍 Endereço (`Endereco`)

| Campo         | Tipo   |
| ------------- | ------ |
| `logradouro`  | String |
| `bairro`      | String |
| `cep`         | String |
| `cidade`      | String |
| `uf`          | String |
| `complemento` | String |
| `numero`      | String |

---

## 🐘 Migrations (Flyway)

As migrações estão em:

```
src/main/resources/db/migration
```

Arquivos existentes:

```
V1__create-table-medicos.sql
V2__alter-table-medicos-add-column-telefone.sql
V3__alter-table-medicos-add-column-ativo.sql
V4__create-table-pacientes.sql
V5__alter-table-pacientes-add-column-telefone.sql
V6__alter-table-paciente-add-column-ativo.sql
```

Flyway executa automaticamente essas versões ao iniciar a aplicação.

---

## 🔗 Endpoints da API

### 👨‍⚕️ Médicos – `/medicos`

| Método   | Endpoint        | Descrição                        |
| -------- | --------------- | -------------------------------- |
| `POST`   | `/medicos`      | Cadastrar novo médico            |
| `GET`    | `/medicos`      | Listar médicos ativos (paginado) |
| `GET`    | `/medicos/{id}` | Detalhar médico                  |
| `PUT`    | `/medicos`      | Atualizar dados                  |
| `DELETE` | `/medicos/{id}` | Soft delete (inativação)         |

---

### 🧑‍🦽 Pacientes – `/pacientes`

| Método   | Endpoint          | Descrição                          |
| -------- | ----------------- | ---------------------------------- |
| `POST`   | `/pacientes`      | Cadastrar paciente                 |
| `GET`    | `/pacientes`      | Listar pacientes ativos (paginado) |
| `GET`    | `/pacientes/{id}` | Detalhar paciente                  |
| `PUT`    | `/pacientes`      | Atualizar dados                    |
| `DELETE` | `/pacientes/{id}` | Inativar paciente                  |

---

## ▶️ Como Executar o Projeto

### 1️⃣ Criar banco no MySQL

```sql
CREATE DATABASE vollmed_api;
```

### 2️⃣ Configurar `application.properties`

```
spring.datasource.url=jdbc:mysql://localhost/vollmed_api
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 3️⃣ Rodar a aplicação

Com Maven:

```bash
mvn spring-boot:run
```

Ou via JAR:

```bash
mvn clean package
java -jar target/api-0.0.1-SNAPSHOT.jar
```

Servidor disponível em:

```
http://localhost:8080
```
