# 📊 Radar Financeiro

> API REST para controle de gastos pessoais e empresariais

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-brightgreen?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Security-red?style=for-the-badge&logo=jsonwebtokens)

---

## 📋 Sobre o Projeto

O **Radar Financeiro** é uma API REST desenvolvida em Java com Spring Boot para gerenciamento financeiro. Permite controlar usuários, títulos (contas a pagar e receber) e centros de custo, oferecendo uma solução completa para organização das finanças.

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| **Java** | 21 | Linguagem de programação |
| **Spring Boot** | 3.5.10 | Framework principal |
| **Spring Security** | - | Autenticação e autorização |
| **Spring Data JPA** | - | Persistência de dados |
| **PostgreSQL** | - | Banco de dados relacional |
| **JWT (jjwt)** | 0.13.0 | Tokens de autenticação |
| **Lombok** | 1.18.36 | Redução de boilerplate |
| **ModelMapper** | 3.2.5 | Mapeamento de objetos |
| **Spring Dotenv** | 4.0.0 | Carregamento de variáveis de ambiente |
| **Springdoc OpenAPI** | 2.8.16 | Documentação Swagger/OpenAPI |
| **Maven** | - | Gerenciador de dependências |

---

## 📁 Estrutura do Projeto

```
📦 radar_financeiro
├── 📂 src/main/java/com/marcelopinotti/radar_financeiro
│   │
│   ├── 📂 common/                    # Utilitários comuns
│   │   └── ConversorData.java        # Conversão de datas
│   │
│   ├── 📂 config/                    # Configurações da aplicação
│   │   └── ApplicationConfig.java    # Beans e configurações gerais
│   │
│   ├── 📂 controller/                # Camada de apresentação (REST)
│   │   ├── UsuarioController.java    # Endpoints de usuário
│   │   ├── TituloController.java     # Endpoints de títulos
│   │   ├── CentroDeCustoController.java
│   │   └── DashBoardController.java  # Endpoints do dashboard
│   │
│   ├── 📂 domain/                    # Camada de domínio
│   │   ├── 📂 Enum/                  # Enumeradores
│   │   │   └── TipoTitulo.java       # A_PAGAR, A_RECEBER
│   │   │
│   │   ├── 📂 exception/             # Exceções customizadas
│   │   │   ├── ApiError.java         # Modelo de erro da API
│   │   │   ├── ResourceNotFoundException.java
│   │   │   └── ResourceBadRequestException.java
│   │   │
│   │   ├── 📂 model/                 # Entidades JPA
│   │   │   ├── Usuario.java          # Entidade de usuário
│   │   │   ├── Titulo.java           # Entidade de título
│   │   │   └── CentroDeCusto.java    # Entidade centro de custo
│   │   │
│   │   ├── 📂 repository/            # Repositórios JPA
│   │   │   ├── UsuarioRepository.java
│   │   │   ├── TituloRepository.java
│   │   │   └── CentroDeCustoRepository.java
│   │   │
│   │   └── 📂 service/               # Regras de negócio
│   │       ├── CRUDService.java      # Interface genérica CRUD
│   │       ├── UsuarioService.java
│   │       ├── TituloService.java
│   │       ├── CentroDeCustoService.java
│   │       └── DashboardService.java
│   │
│   ├── 📂 dto/                       # Data Transfer Objects (Records)
│   │   ├── 📂 usuario/
│   │   │   ├── UsuarioRequestDTO.java
│   │   │   ├── UsuarioResponseDTO.java
│   │   │   ├── LoginRequestDTO.java
│   │   │   └── LoginResponseDTO.java
│   │   │
│   │   ├── 📂 titulo/
│   │   │   ├── TituloRequestDto.java
│   │   │   └── TituloResponseDto.java
│   │   │
│   │   ├── 📂 centro_de_custo/
│   │   │   ├── CentroDeCustoRequestDto.java
│   │   │   └── CentroDeCustoResponseDto.java
│   │   │
│   │   └── 📂 dashboard/
│   │       └── DashboardResponseDto.java
│   │
│   ├── 📂 handler/                   # Tratamento global de exceções
│   │   └── RestExceptionHandler.java
│   │
│   ├── 📂 security/                  # Configurações de segurança
│   │   ├── WebSecurityConfig.java    # Configuração do Spring Security
│   │   ├── JwtUtil.java              # Utilitário para JWT
│   │   ├── JwtAuthenticationFilter.java  # Filtro de autenticação
│   │   ├── JwtAuthorizationFilter.java   # Filtro de autorização
│   │   └── UserDetailsSecurityServer.java
│   │
│   └── RadarFinanceiroApplication.java   # Classe principal
│
├── 📂 src/main/resources/
│   └── application.properties        # Configurações da aplicação
│
├── 📂 src/test/                      # Testes unitários
├── pom.xml                           # Dependências Maven
└── README.md
```

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** para autenticação:

1. Faça login no endpoint `/api/auth/login`
2. Receba o token Bearer
3. Inclua o token no header das requisições:
   ```
   Authorization: Bearer <seu_token>
   ```

---

## 📌 Endpoints

### 👤 Usuário
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/usuarios/cadastrar` | Criar usuário |
| `GET` | `/api/usuarios/obter` | Listar todos |
| `GET` | `/api/usuarios/obter/{id}` | Buscar por ID |
| `PATCH` | `/api/usuarios/atualizar/{id}` | Atualizar |
| `DELETE` | `/api/usuarios/deletar/{id}` | Deletar (soft delete) |

### 🔑 Autenticação
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/auth/login` | Fazer login |

### 💰 Título
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/titulo/cadastrar` | Criar título |
| `GET` | `/api/titulo/obter` | Listar todos |
| `GET` | `/api/titulo/obter/{id}` | Buscar por ID |
| `PATCH` | `/api/titulo/atualizar/{id}` | Atualizar |
| `DELETE` | `/api/titulo/deletar/{id}` | Deletar |

### 🏷️ Centro de Custo
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/centro-de-custo/cadastrar` | Criar centro de custo |
| `GET` | `/api/centro-de-custo/obter` | Listar todos |
| `GET` | `/api/centro-de-custo/obter/{id}` | Buscar por ID |
| `PATCH` | `/api/centro-de-custo/atualizar/{id}` | Atualizar |
| `DELETE` | `/api/centro-de-custo/deletar/{id}` | Deletar |

### 📈 Dashboard
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/dashboard?periodoInicial=YYYY-MM-DD&periodoFinal=YYYY-MM-DD` | Fluxo de caixa por periodo |

---

## ⚙️ Configuração

### Pré-requisitos
- Java 21+
- Maven 3.8+
- PostgreSQL 14+

### Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
DB_URL=jdbc:postgresql://localhost:5432/radar_financeiro
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_secreta_com_pelo_menos_256_bits
JWT_EXPIRATION=86400000
```

### Executando o Projeto

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/radar_financeiro.git

# Entre na pasta
cd radar_financeiro

# Instale as dependências
mvn clean install

# Execute a aplicação
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

---

## 📊 Modelo de Dados

```
┌───────────────┐          ┌──────────────────┐          ┌───────────────────┐
│    Usuario    │          │      Titulo      │          │   CentroDeCusto   │
├───────────────┤          ├──────────────────┤          ├───────────────────┤
│ id_usuario PK │──┐       │ id_titulo    PK  │       ┌──│ idCentroDeCusto PK│
│ nome          │  │       │ descricao        │       │  │ descricao         │
│ email (unique)│  │       │ valor            │       │  │ observacao        │
│ senha         │  ├──────▶│ id_usuario   FK  │       │  │ id_usuario    FK  │
│ celular       │  │       │ tipo             │       │  └───────────────────┘
│ dataCadastro  │  │       │ dataVencimento   │       │           │
│ dataInativacao│  │       │ dataPagamento    │       │           │
└───────────────┘  │       └──────────────────┘       │           │
                   │                │                 │           │
                   │                │                 │           │
                   │                ▼                 │           │
                   │       ┌──────────────────┐       │           │
                   │       │titulo_centro_custo│      │           │
                   │       ├──────────────────┤       │           │
                   │       │ id_titulo     FK ─┼──────┘           │
                   │       │ id_centro_custo FK┼──────────────────┘
                   │       └──────────────────┘
                   │
                   └──────────────────────────────────▶ CentroDeCusto.id_usuario
```

---

## 🎯 Tipos de Título

| Enum | Descrição |
|------|-----------|
| `A_PAGAR` | Contas a pagar |
| `A_RECEBER` | Contas a receber |

---

## 🛠️ Funcionalidades

- ✅ CRUD completo de Usuários
- ✅ CRUD completo de Títulos
- ✅ CRUD completo de Centros de Custo
- ✅ Dashboard de fluxo de caixa por periodo
- ✅ Autenticação JWT
- ✅ Criptografia de senhas (BCrypt)
- ✅ Soft delete de usuários
- ✅ Validação de campos obrigatórios
- ✅ Tratamento global de exceções
- ✅ DTOs com Java Records
- ✅ Documentação OpenAPI (Swagger)

---

## 👨‍💻 Autor

**Marcelo Pinotti**

---

<p align="center">
  Feito com ❤️ usando Spring Boot
</p>
