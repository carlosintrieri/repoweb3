# 🚀 AUTOBOTS - SISTEMA DE GESTÃO AUTOMOTIVA

## ✅ PROJETO MAVEN COMPLETO - 100% FUNCIONANDO

## 📦 ESTRUTURA DO PROJETO

**Para começar:** entre no diretório `autobots` e então rode o código!

```
autobots-projeto-completo/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/com/autobots/automanager/
        │   ├── AutomanagerApplication.java (CLASSE PRINCIPAL)
        │   ├── entidades/
        │   │   ├── Credencial.java
        │   │   ├── Documento.java
        │   │   ├── Email.java
        │   │   ├── Empresa.java
        │   │   ├── Endereco.java
        │   │   ├── Mercadoria.java
        │   │   ├── Servico.java
        │   │   ├── Telefone.java
        │   │   ├── Usuario.java
        │   │   ├── Veiculo.java
        │   │   └── Venda.java
        │   ├── enums/
        │   │   ├── TipoUsuario.java
        │   │   └── TipoVeiculo.java
        │   ├── repositorios/
        │   │   ├── CredencialRepositorio.java
        │   │   ├── DocumentoRepositorio.java
        │   │   ├── EmpresaRepositorio.java
        │   │   ├── MercadoriaRepositorio.java
        │   │   ├── ServicoRepositorio.java
        │   │   ├── UsuarioRepositorio.java
        │   │   ├── VeiculoRepositorio.java
        │   │   └── VendaRepositorio.java
        │   └── controles/
        │       ├── EmpresaControle.java
        │       ├── MercadoriaControle.java
        │       ├── ServicoControle.java
        │       ├── UsuarioControle.java
        │       ├── VeiculoControle.java
        │       └── VendaControle.java
        └── resources/
            └── application.properties
```

## 🚀 COMO EXECUTAR

### Opção 1: Maven (RECOMENDADO)

**ATENÇÃO:** Para rodar o projeto:

```bash
# 1. Entrar no diretório correto
cd autobots

# 2. Executar
mvn spring-boot:run
```

### Opção 2: IDE (Eclipse/IntelliJ)

1. Importar como projeto Maven
2. Aguardar download de dependências
3. Executar `AutomanagerApplication.java`

## 📊 ENDPOINTS DISPONÍVEIS

### Empresa - `/empresa`

- `GET /empresa` - Listar todas
- `GET /empresa/{id}` - Buscar por ID
- `POST /empresa` - Cadastrar
- `PUT /empresa/{id}` - Atualizar
- `DELETE /empresa/{id}` - Excluir

### Usuario - `/usuario`

- `GET /usuario` - Listar todos
- `GET /usuario/{id}` - Buscar por ID
- `POST /usuario` - Cadastrar
- `PUT /usuario/{id}` - Atualizar
- `DELETE /usuario/{id}` - Excluir

### Veiculo - `/veiculo`

- `GET /veiculo` - Listar todos
- `GET /veiculo/{id}` - Buscar por ID
- `POST /veiculo` - Cadastrar
- `PUT /veiculo/{id}` - Atualizar
- `DELETE /veiculo/{id}` - Excluir

### Mercadoria - `/mercadoria`

- `GET /mercadoria` - Listar todas
- `GET /mercadoria/{id}` - Buscar por ID
- `POST /mercadoria` - Cadastrar
- `PUT /mercadoria/{id}` - Atualizar
- `DELETE /mercadoria/{id}` - Excluir

### Servico - `/servico`

- `GET /servico` - Listar todos
- `GET /servico/{id}` - Buscar por ID
- `POST /servico` - Cadastrar
- `PUT /servico/{id}` - Atualizar
- `DELETE /servico/{id}` - Excluir

### Venda - `/venda`

- `GET /venda` - Listar todas
- `GET /venda/{id}` - Buscar por ID
- `POST /venda` - Cadastrar
- `PUT /venda/{id}` - Atualizar
- `DELETE /venda/{id}` - Excluir

## 🧪 EXEMPLOS DE TESTE

### 1. Cadastrar Empresa

```http
POST http://localhost:8080/empresa
Content-Type: application/json

{
  "razaoSocial": "Autobots Ltda",
  "nomeFantasia": "Autobots",
  "telefones": [
    {"ddd": "11", "numero": "9999-8888"}
  ],
  "endereco": {
    "estado": "SP",
    "cidade": "São Paulo",
    "bairro": "Centro",
    "rua": "Av. Paulista",
    "numero": "1000",
    "codigoPostal": "01310-100"
  }
}
```

### 2. Cadastrar Usuario

```http
POST http://localhost:8080/usuario
Content-Type: application/json

{
  "nome": "João Silva",
  "dataNascimento": "1990-01-15",
  "tipoUsuario": "CLIENTE"
}
```

### 3. Cadastrar Veiculo

**ATENÇÃO:** AQUI TEM ENUM com 4 tipos de Veículos!

```http
POST http://localhost:8080/veiculo
Content-Type: application/json

{
  "tipo": "PASSEIO",    
  "modelo": "Civic",
  "placa": "ABC-1234"
}
```

### 4. Cadastrar Mercadoria

```http
POST http://localhost:8080/mercadoria
Content-Type: application/json

{
  "nome": "Óleo Castrol 5W30",
  "valor": 89.90,
  "quantidade": 50,
  "descricao": "Óleo sintético"
}
```

### 5. Cadastrar Servico

```http
POST http://localhost:8080/servico
Content-Type: application/json

{
  "nome": "Troca de Óleo",
  "valor": 150.00,
  "descricao": "Troca completa"
}
```

### 6. Cadastrar Venda

```http
POST http://localhost:8080/venda
Content-Type: application/json

{
  "identificacao": "VENDA-001",
  "cliente": {"id": 1},
  "funcionario": {"id": 2},
  "veiculo": {"id": 1},
  "mercadorias": [{"id": 1}],
  "servicos": [{"id": 1}]
}
```

### ⚠️ Para deletar usuário:

Primeiro delete a venda em cujo usuário está, depois delete o usuário!

## 💾 BANCO DE DADOS

### H2 Database (Padrão)

- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:automanager`
- **Username:** `sa`
- **Password:** (vazio)

### MySQL (Opcional)

Editar `application.properties`:

```properties
# Comentar configurações do H2
# Descomentar configurações do MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/automanager
spring.datasource.username=root
spring.datasource.password=SUA_SENHA
```

## ✅ GARANTIAS

- ✅ Todos os GET funcionam
- ✅ Todos os POST funcionam (com validações)
- ✅ Todos os PUT funcionam
- ✅ Todos os DELETE funcionam
- ✅ HATEOAS em todas as respostas
- ✅ Validações de FK
- ✅ `setCadastro()` automático
- ✅ Campos opcionais onde necessário

## 🎯 TECNOLOGIAS

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Spring HATEOAS
- H2 Database (padrão)
- MySQL (opcional)
- Maven

## 🎉 PRONTO PARA USO!

Basta executar `mvn spring-boot:run` e testar todos os endpoints!
