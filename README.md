
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

O projeto foi desenvolvido com base na arquitetura RESTFull para a API. Além disso, foram implementadas soluções de Domain Driven Design e Clean Code.

O backend do projeto foi desenvolvido em Java, com a utilização do framework Spring para lidar com API, autenticação e segurança. Os testes são feitos com o JUnit.

O banco de dados utilizado foi o PostgreSQL.

Em construção...
