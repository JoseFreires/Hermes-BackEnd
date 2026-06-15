<h1 align="center">
  HERMES BACK_END
</h1>


## 🔗 Endpoints

### Autenticação

| Método | Endpoint     | Descrição                  | Auth |
|--------|--------------|----------------------------|------|
| POST   | `/api/login` | Login e geração de token   | ❌   |


### Requisições de Encomenda

| Método | Endpoint                          | Descrição                           | Auth |
|--------|-----------------------------------|-------------------------------------|------|
| GET    | `/api/encomendas`                 | Lista todos as encomendas           | ✅   |
| GET    | `/api/encomendas/{id}`            | Busca encomenda por ID              | ✅   |
| GET    | `/api/encomendas?status=ENTREGUE` | Busca encomenda por status ENTREGUE | ✅   |
| GET    | `/api/encomendas?status=RECEBIDA` | Busca encomenda por status RECEBIDA | ✅   |
| POST   | `/api/encomendas/`                | Cria uma nova encomenda             | ✅   |
| PUT    | `/api/encomendas/{id}`            | Atualiza a encomenda                | ✅   |
| DELETE | `/api/encomendas/{id}`            | Remove a encomenda                  | ✅   |

### Requisições de Morador

| Método | Endpoint              | Descrição                       | Auth |
|--------|-----------------------|---------------------------------|------|
| GET    | `/api/moradores`      | Lista todos os moradores ativos | ✅   |
| GET    | `/api/moradores/{id}` | Busca morador por ID            | ✅   |
| POST   | `/api/moradores`      | Cria um novo morador            | ✅   |
| PUT    | `/api/moradores/{id}` | Atualiza morador                | ✅   |
| DELETE | `/api/moradores/{id}` | Remove o morador                | ✅   |


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