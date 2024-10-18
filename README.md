
# Painel Admin: Loja de Uniformes - Back-End

Esse projeto foi desenvolvido para a empresa Silvana Uniformes, presente há quase 20 anos no mercado de uniformes. Se trata de um painel de administrador para gerenciamento da empresa, possibilitando registrar empresas parceiras, vendas, controle de estoque e tratamento de dados em relatórios de desempenho.

O objetivo é dar total controle e visibilidade para o usuário sobre o ecossistema do seu negócio, de forma simples e rápida.




## Funcionalidades
- Cadastrar, editar e remover empresas parceiras;
- Cadastrar, editar e remover vendas realizadas para as empresas;
- Controle de estoque, atualização, edição e remoção de vestuário da empresa por categorias;
- Gerar relatórios de vendas e estoque com diferentes parâmetros;
- Autenticação e Autorização dos usuários, sistema de login e separação de usuários por role;
## Instalação

1. Clone o repositório na sua máquina local:

`https://github.com/FabioSigF/admin_loja_uniformes`

2. Faça alterações no arquivo resources/application.properties para incluir o seu banco de dados PostgreSQL, username e password:

```java
spring.application.name=admin_loja_uniformes

spring.datasource.url= jdbc:postgresql://localhost:5432/loja_uniformes_admin
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.jdbc.lab.non_contextual_creation=true
spring.jpa.show-sql=true

api.security.token.secret=${JWT_SECRET:my-secret-key}
```

3. Rode o projeto e aproveite!
## Arquitetura e Planejamento

O projeto foi desenvolvido com base na arquitetura RESTFull para a API. Além disso, foram implementadas algumas soluções de Domain Driven Design e Clean Architecture.

O backend do projeto foi desenvolvido em Java, com a utilização do framework Spring para lidar com API, autenticação e segurança. Os testes são feitos com o JUnit.

O banco de dados utilizado foi o PostgreSQL.

### Diagrama Entidade Relacionamento da API
![Admin Loja (1)](https://github.com/user-attachments/assets/9dd190e6-45fe-4073-ae14-d48a71939f88)

### UML da API
![Admin Loja](https://github.com/user-attachments/assets/8e761a7b-05d7-441c-bfc8-033ed4fda640)


## Documentação da API

### Produtos

#### Cadastra produto de uma empresa

```http
  POST /product
```

O único requesito desse método é o body. Ele deve ser algo como:

```json
{
    "companyId": "3238cdf4-cb6d-42b7-be10-8e96e0b813cf",
    "name": "Camiseta Padrão",
    "description": "Camiseta Padrão",
    "gender": "MASCULINO",
    "features": [
        {
            "color": "Branco",
            "size": "M",
            "price": 39.90,
            "stockQuantity": 5
        },
        {
            "color": "Preto",
            "size": "M",
            "price": 39.90,
            "stockQuantity": 5
        },
        {
            "color": "Rosa",
            "size": "M",
            "price": 39.90,
            "stockQuantity": 5
        }
    ]
}
```

#### Retorna todos os produtos de uma empresa

```http
  GET /product/company/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `UUID` | **Obrigatório**. ID da Empresa |

#### Retorna um produto por ID

```http
  GET /product/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `UUID` | **Obrigatório**. O ID do produto que você quer |

#### Retornar pelo nome produtos de uma empresa

```http
  GET /product/company/{id}/?name={queryParam}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `UUID` | **Obrigatório**. O ID da empresa |
| `queryParam`      | `String` | Nome do(s) produto(s) |


#### Deleta um produto por ID

```http
  DELETE /product/delete/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `UUID` | **Obrigatório**. O ID do produto |

### Company

#### Cadastrar uma empresa

```http
  POST /companies
```

O único requesito desse método é o body. Ele deve ser algo como:

```json
{
    "name": "Escola",
    "cnpj": "85883307000130",
    "category": "EDUCACAO",
    "phones": [
        {
        "number": "32233223",
        "deleted": false
        },
        {
        "number": "32233223",
        "deleted": false
        }
    ]
}
```

**Observação**: O CNPJ possui um validator. Portanto, você deve inserir um CNPJ válido, ou será gerada uma Exception.

#### Retornar todas as empresas do sistema

```http
  GET /companies
```

#### Retornar empresa por ID

```http
  GET /companies/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `UUID` | **Obrigatório**. O ID da empresa que você quer |


#### Retornar lista de empresas por busca de nome

```http
  GET /companies/search/{nome}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `nome`      | `String` | **Obrigatório**. O nome da empresa que você busca |

#### Retornar lista de empresas por busca de categoria

```http
  GET /companies/search-category/{categoria}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `categoria`      | `CategoryEnum` | **Obrigatório**. Categoria de empresa |

#### Deleta uma empresa por ID

```http
  DELETE /companies/delete/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `UUID` | **Obrigatório**. O ID da empresa |


### Sale

#### Cadastrar uma venda

```http
  POST /sale
```

O único requesito desse método é o body. Ele deve ser algo como:

```json
{
    "companyId": "3238cdf4-cb6d-42b7-be10-8e96e0b813cf",
    "saleItems": [
        {
            "productFeatureId": "627d3771-dad6-4429-9315-b274fd085164",
            "amount": 2,
            "price": 199.5
        },
        {
            "productFeatureId": "486d984d-0b40-46c6-8163-701934f0dc41",
            "amount": 5,
            "price": 199.5
        }
    ]
}
```

#### Retornar todas as vendas do sistema

```http
  GET /sale
```

#### Retornar venda por ID

```http
  GET /sale/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `UUID` | **Obrigatório**. O ID da venda que você quer |

#### Retornar todas as vendas entre datas

```http
  GET sale/?startDate={startDate}&endDate={endDate}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `startDate`      | `UUID` | **Obrigatório**. YYYY-MM-DD |
| `endDate`      | `UUID` | **Obrigatório**. YYYY-MM-DD |


