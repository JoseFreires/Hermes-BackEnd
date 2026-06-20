<h1 align="center">
  HERMES BACK_END
</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" />
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
  
</p>
<p>
  O Hermes automatiza o controle de entregas em condomínios: registra encomendas, vincula ao morador e envia 
    um token por notificação para validar a retirada.
</p>

## 📋 Sumário

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias](#-tecnologias)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração de Ambiente](#-configuração-de-ambiente)
- [Como Executar](#-como-executar)
- [Documentação da API](#-documentação-da-api)
- [Endpoints](#-endpoints)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Documentação do Projeto](#-documentação-do-projeto)
---

## 📌 Sobre o Projeto
A API Hermes Back-end tem como objetivo central viabilizar a gestão e o controle de recebimentos em condomínios. 
Ela expõe os endpoints necessários para automatizar o processo de check-in das entregas, permitindo o registro de 
encomendas e seu vínculo ao respectivo destinatário (morador). Após o registro, a API é responsável por disparar uma 
notificação automática ao morador, contendo um token de autenticação que deverá ser apresentado para a verificação de 
identidade no ato da retirada.

**Principais funcionalidades:**

- ✅ Registro e controle de encomendas dos moradores
- ✅ Registro e controle de funcionários do Condomínio(Porteiro e Síndico)
- ✅ Registro e controle de Moradores
- ✅ Notificação via E-mail
- ✅ Autenticação com JWT
---

## 🛠 Tecnologias

| Tecnologia      | Versão | Finalidade                     |
|-----------------|--------|--------------------------------|
| Java            | 17+    | Linguagem principal            |
| Spring Boot     | 3.x    | Framework da aplicação         |
| Spring Security | 6.x    | Autenticação e autorização     |
| Spring Data JPA | 3.x    | Persistência de dados          |
| MySQL           | 9.x    | Banco de dados relacional      |
| Flyway          | 9.x    | Migração de banco de dados     |
 
---

## ✅ Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- [Java 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [Git](https://git-scm.com/)
---

## ⚙️ Configuração de Ambiente

**1. Clone o repositório:**

```bash
git clone https://github.com/JoseFreires/Hermes-BackEnd
cd Hermes-BackEnd
```

**2. Configure as variáveis de ambiente:**


```bash
cp .env.example .env
```

Edite o arquivo `.env`:

```env
# Banco de Dados
DB_HOST=localhost
DB_PORT=5432
DB_NAME=nome_banco
DB_USERNAME=usuario
DB_PASSWORD=senha

 
# Aplicação
SERVER_PORT=8080
```

> ⚠️ **Nunca** faça commit do arquivo `.env` com dados reais.
 
---

## 🔗 Endpoints

### Autenticação

| Método | Endpoint           | Descrição                  | Auth |
|--------|--------------------|----------------------------|-----|
| POST   | `/api/auth/entrar` | Login e geração de token   | ❌  |
| POST   | `/api/auth/sair`   | Logout e limpeza de cookie | ✅ |
GET    | `/api/auth/eu`     | Login e geração de token   | ✅    |


### Requisições de Encomenda

| Método | Endpoint                          | Descrição                           | Auth |
|--------|-----------------------------------|-------------------------------------|------|
| GET    | `/api/encomendas`                 | Lista todos as encomendas           | ✅   |
| GET    | `/api/encomendas/{id}`            | Busca encomenda por ID              | ✅   |
| GET    | `/api/encomendas?status=ENTREGUE` | Busca encomenda por status ENTREGUE | ✅   |
| GET    | `/api/encomendas?status=RECEBIDA` | Busca encomenda por status RECEBIDA | ✅   |
| POST   | `/api/encomendas/`                | Cria uma nova encomenda             | ✅   |
| PUT    | `/api/encomendas/{id}`            | Atualiza a encomenda                | ✅   |
 PUT    | `/api/encomendas/{id}/entrega`    | Atualiza a encomenda para ENTREGUE  | ✅   |
| DELETE | `/api/encomendas/{id}`            | Remove a encomenda                  | ✅   |

### Requisições de Morador

| Método | Endpoint                         | Descrição                          | Auth |
|--------|----------------------------------|------------------------------------|------|
| GET    | `/api/moradores`                 | Lista todos os moradores ativos    | ✅   |
| GET    | `/api/moradores/{id}`            | Busca morador por ID               | ✅   |
| GET    | `/api/moradores/{id}/encomendas` | Busca encomendas do morador por ID | ✅   |
| POST   | `/api/moradores`                 | Cria um novo morador               | ✅   |
| PUT    | `/api/moradores/{id}`            | Atualiza morador                   | ✅   |
| DELETE | `/api/moradores/{id}`            | Remove o morador                   | ✅   |


### Requisições de Porteiro

| Método | Endpoint              | Descrição                       | Auth |
|--------|-----------------------|---------------------------------|------|
| GET    | `/api/porteiros`      | Lista todos os porteiros ativos | ✅   |
| GET    | `/api/porteiros/{id}` | Busca porteiro por ID           | ✅   |
| POST   | `/api/porteiros`      | Cria um novo porteiro            | ✅   |
| PUT    | `/api/porteiros/{id}` | Atualiza porteiro                | ✅   |
| DELETE | `/api/porteiros/{id}` | Remove o porteiro                | ✅   |

### Requisições de Consulta Condominial

| Método | Endpoint                                   | Descrição                  | Auth |
|--------|--------------------------------------------|----------------------------|------|
| GET    | `/api/consultacondominial/moradias`                | Lista todos as moradias    | ✅   |
| GET    | `/api/consultacondominial/moradias/{id}`           | Busca moradia por ID       | ✅   |
| GET    | `/api/consultacondominial/moradias/{id}/moradores` | Busca moradores da moradia | ✅   |
| GET    | `/api/consultacondominial/blocos`                  | Lista todos os blocos      | ✅   |
| GET    | `/api/consultacondominial/blocos/{id}`             | Busca bloco por ID         | ✅   |
| GET    | `/api/consultacondominial/blocos/{id}/moradias`    | Busca bloco por ID         | ✅   |

### Requisições de Sindicos

| Método | Endpoint                       | Descrição                     | Auth |
|--------|--------------------------------|-------------------------------|------|
| GET    | `/api/sindicos`                | Lista todos as sindicos       | ✅   |
| GET    | `/api/sindicos/{id}` | Busca sindico por ID    | ✅   |
| POST   | `/api/sindicos`      | Cria um novo sindico          | ✅   |
| PUT    | `/api/sindicos/{id}` | Atualiza sindico              | ✅   |
| DELETE | `/api/sindicos/{id}` | Remove o sindico              | ✅   |


### Requisições de Pessoas Autorizadas

| Método | Endpoint                                 | Descrição                          | Auth |
|--------|------------------------------------------|------------------------------------|------|
| GET    | `/api/pessoasAutorizadas`                | Lista todos as pessoas autorizadas | ✅   |
| GET    | `/api/pessoasAutorizadas/{id}`           | Busca pessoa autorizada por ID     | ✅   |
| GET    | `/api/pessoasAutorizadas/moradores/{id}` | Busca pessoa autorizada por ID     | ✅   |
| POST   | `/api/pessoasAutorizadas`                | Cadastra pessoa autorizada         | ✅   |
| PUT    | `/api/pessoasAutorizadas/{id}`           | Atualiza pessoa autorizada por ID  | ✅   |

**Exemplo de requisição:**

```bash
curl -X POST http://localhost:8080/api/encomendas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu-token}" \
  -d '{
    "nomePacote": "Caixa Mercado Livre",
    "observacao": "Pacote em perfeito estado",
    "foto": "url-da-foto.jpg",
    "idPessoaDestinatario": 3,
    "idUsuarioPorteiro": 2
  }'
```

**Exemplo de resposta:**

```json
{
  "idEncomenda": 1,
  "nomePacote": "Caixa Mercado Livre",
  "status": "RECEBIDA",
  "token": "5467",
  "dataHoraRecebido": "2026-06-20T14:30:00Z",
  "idPessoaDestinatario": 3,
  "idUsuarioPorteiro": 2
}
```
 
---

## 📁 Estrutura do Projeto

```
Hermes-BackEnd/src/
├── main/
│   ├── java/com/hermes/projeto/backend/
│   │   ├── BackendApplication.java
│   │   │
│   │   ├── controller/              # Camada de entrada HTTP / endpoints REST
│   │   │   ├── AutenticacaoController.java
│   │   │   ├── EncomendaController.java
│   │   │   ├── MoradorController.java
│   │   │   ├── PorteiroController.java
│   │   │   └── SindicoController.java
│   │   │
│   │   ├── service/                 # Regras de negócio
│   │   │   ├── AdmService.java
│   │   │   ├── MoradorService.java
│   │   │   ├── PortariaService.java
│   │   │   ├── SindicoService.java
│   │   │   ├── AutenticacaoService.java
│   │   │   ├── TokenService.java
│   │   │   └── LogSistemaService.java
│   │   │
│   │   ├── repository/              # Acesso a dados (Spring Data JPA)
│   │   │   ├── BlocoRepository.java
│   │   │   ├── CondominioRepository.java
│   │   │   ├── ContaAdmRepository.java
│   │   │   ├── EncomendaRepository.java
│   │   │   ├── LogSistemaRepository.java
│   │   │   ├── MoradiaRepository.java
│   │   │   ├── MoradorRepository.java
│   │   │   ├── PapelRepository.java
│   │   │   ├── PessoaAutorizadaRepository.java
│   │   │   ├── PessoaRepository.java
│   │   │   ├── PorteiroRepository.java
│   │   │   └── UsuarioRepository.java
│   │   │
│   │   ├── domain/                  # Entidades e modelos de domínio
│   │   │   ├── Bloco.java
│   │   │   ├── Condominio.java
│   │   │   ├── ContaAdm.java
│   │   │   ├── Encomenda.java
│   │   │   ├── Moradia.java
│   │   │   ├── Morador.java
│   │   │   ├── Papel.java
│   │   │   ├── Pessoa.java
│   │   │   ├── PessoaAutorizada.java
│   │   │   ├── Porteiro.java
│   │   │   ├── Usuario.java
│   │   │   └── enums/
│   │   │       ├── StatusEncomenda.java
│   │   │       ├── TipoRetirada.java
│   │   │       └── TurnoPorteiro.java
│   │   │
│   │   ├── dto/                     # Objetos de transferência de dados
│   │   │   ├── request/             # Dados de entrada (criação e atualização)
│   │   │   │   ├── DadosLoginDTO.java
│   │   │   │   ├── DadosRegistrarEncomendaDTO.java
│   │   │   │   ├── DadosRegistrarMoradorDTO.java
│   │   │   │   ├── DadosRegistrarPessoaAutorizadaDTO.java
│   │   │   │   ├── DadosRegistrarPessoaDTO.java
│   │   │   │   ├── DadosRegistrarPorteiroDTO.java
│   │   │   │   ├── DadosAtualizacaoBlocoDTO.java
│   │   │   │   ├── DadosAtualizacaoCondominioDTO.java
│   │   │   │   ├── DadosAtualizacaoEncomendaDTO.java
│   │   │   │   ├── DadosAtualizacaoMoradiaDTO.java
│   │   │   │   ├── DadosAtualizacaoMoradorDTO.java
│   │   │   │   ├── DadosAtualizacaoPessoaAutorizadaDTO.java
│   │   │   │   ├── DadosAtualizacaoPessoaDTO.java
│   │   │   │   ├── DadosAtualizacaoPorteiroDTO.java
│   │   │   │   └── DadosAtualizarStatusEncomendaDTO.java
│   │   │   └── response/            # Dados de saída (consultas)
│   │   │       ├── DadosConsultaBlocoDTO.java
│   │   │       ├── DadosConsultaCondominioDTO.java
│   │   │       ├── DadosConsultaEncomendaDTO.java
│   │   │       ├── DadosConsultaLogDTO.java
│   │   │       ├── DadosConsultaLoginDTO.java
│   │   │       ├── DadosConsultaMoradiaDTO.java
│   │   │       ├── DadosConsultaMoradorDTO.java
│   │   │       ├── DadosConsultaPessoaAutorizadaDTO.java
│   │   │       ├── DadosConsultaPessoaDTO.java
│   │   │       └── DadosConsultaPorteiroDTO.java
│   │   │
│   │   ├── security/                # Configuração de autenticação e filtros JWT
│   │   │   ├── SecurityConfig.java
│   │   │   └── SecurityFilter.java
│   │   │
│   │   ├── infra/
│   │   │   └── aop/                 # Aspectos de log com Spring AOP
│   │   │       ├── LogAspect.java
│   │   │       └── LogSistema.java
│   │   │
│   │   └── util/                    # Utilitários gerais
│   │       └── Gerador.java
│   │
│   └── resources/
│       ├── application.properties
│       └── db/
│           └── migration/           # Migrações Flyway
│               ├── V1__schema-definitivo-hermes.sql
│               └── V2__update-table-conta_adm.sql
│
└── test/
    └── java/com/hermes/projeto/backend/
        └── BackendApplicationTests.java
```

### Responsabilidades por camada

| Pacote | Responsabilidade |
|---|---|
| `controller` | Recebe requisições HTTP e delega para os serviços |
| `service` | Contém as regras de negócio da aplicação |
| `repository` | Interfaces de acesso ao banco de dados via Spring Data JPA |
| `domain` | Entidades JPA e enums do domínio |
| `dto/request` | Objetos de entrada para criação e atualização de recursos |
| `dto/response` | Objetos de saída retornados nas consultas |
| `security` | Configuração do Spring Security e filtro JWT |
| `infra/aop` | Aspectos transversais de log via Spring AOP |
| `util` | Classes utilitárias sem vínculo com o domínio |


## Documentação do Projeto

📄 [Documentação Completa do Projeto (PDF)](./docs/Projeto_Hermes.pdf)

> Inclui: requisitos funcionais e não-funcionais, regras de negócio, diagramas UML
> (casos de uso, classes, objetos e sequência), modelo de banco de dados,
> dicionário de dados e protótipos de interface.

<p>Feito por: </p>

<table align="center">
  <tr>
    <td align="center">
      <a href="https://github.com/JoseFreires">
        <img src="https://github.com/JoseFreires.png" width="80" style="border-radius:50%;" /><br />
        <sub><b>José Freires</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Caiopolis">
        <img src="https://github.com/Caiopolis.png" width="80" style="border-radius:50%;" /><br />
        <sub><b>Caio Henrique</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/SilvaEng7">
        <img src="https://github.com/SilvaEng7.png" width="80" style="border-radius:50%;" /><br />
        <sub><b>Rian Silva</b></sub>
      </a>
    </td>
  </tr>
</table>
