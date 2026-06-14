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