# Seguradora de veículos - API

## Sobre este Projeto
Este é o back-end do sistema [Seguradora de veículos - Aplicação](https://github.com/filiperdt/seguradora-aplicacao).

O objetivo desta API REST é gerenciar uma seguradora de veículos.

O projeto faz parte do meu portfólio pessoal.

## Começando

### Pré-requisitos
- Java 11.
- O banco de dados PostgreSQL deve estar instalado e em execução na porta padrão, 5432. A versão do PostgreSQL utilizada no desenvolvimento foi a 12.1.
- Deve ser criado um banco de dados chamado 'seguradora'. Dentro dele, deve ser criado um schema, também chamado 'seguradora'.

## Rotas

> O caminho base para a API é /seguradora

### Rota de Teste

- **Esta é a rota que você pode usar para verificar se a API está funcionando corretamente.**

> http://localhost:8080/seguradora

## Cliente

| ENDPOINT | Método | Parâmetros | Parâmetros de URL | Resposta de Sucesso | Resposta de Erro
|--|--|--|--|--|--|
| /clientes | `GET`  | - | - |**Code:** 200 - OK<br>**Content:** [ [Cliente](#valor-de-exemplo-de-resposta-de-cliente) ]  | - |
| /clientes | `POST` | [Cliente](#parâmetros-de-cliente) | - |**Code:** 201 - CREATED <br> **Content:** [Cliente](#valor-de-exemplo-de-resposta-de-cliente) |  **Code:** 400 - BAD REQUEST<br>**Content:** [Mensagem de erro 400](#parâmetros-de-resposta-do-erro-400-de-cliente)|
| /clientes/:cpf | `GET` | - | cpf=String |**Code:** 200 - OK <br> **Content:** [Cliente](#valor-de-exemplo-de-resposta-de-cliente) |  **Code:** 404 - NOT FOUND<br>**Content:** [Mensagem de erro 404](#parâmetros-de-resposta-do-erro-404-de-cliente)|
| /clientes/:cpf | `PUT` | [Cliente](#parâmetros-de-cliente) | cpf=String |**Code:** 200 - OK <br> **Content:** [Cliente](#valor-de-exemplo-de-resposta-de-cliente) | **Code:** 400 - BAD REQUEST<br>**Content:** [Mensagem de erro 400](#parâmetros-de-resposta-do-erro-400-de-cliente)<br>*or*<br>**Code:** 404 - NOT FOUND<br>**Content:** [Mensagem de erro 404](#parâmetros-de-resposta-do-erro-404-de-cliente)|
| /clientes/:cpf | `DELETE` | - | cpf=String |**Code:** 200 - OK <br> **Content:** [Sucesso na exclusão de Cliente](#parâmetros-de-sucesso-na-exclusão-de-cliente) |  **Code:** 404 - NOT FOUND<br>**Content:** [Mensagem de erro 404](#parâmetros-de-resposta-do-erro-404-de-cliente)<br>*or*<br>**Code:** 409 - CONFLICT<br>**Content:** [Mensagem de erro 409](#parâmetros-de-resposta-do-erro-409-de-cliente)|

### CRUD Cliente
> - GET /clientes - Listar Clientes.
> - POST /clientes - Criar Cliente (Necessário fornecer [Cliente](#parâmetros-de-cliente), dentro do corpo da solicitação).
> - GET /clientes/:cpf - Exibir Cliente.
> - PUT /clientes/:cpf - Atualizar Cliente (Necessário fornecer os campos de [Cliente](#parâmetros-de-cliente) a ser atualizado, dentro do corpo da solicitação).
> - DELETE /clientes/:cpf - Excluir Cliente.

#### Parâmetros de Cliente
- cpf
- nome
- cidade
- uf

#### Modelo para requisição de Cliente
```
Cliente {
  cpf (String, required, unique),
  nome (String, required),
  cidade (String, required),
  uf (String, required)
}
```

#### Valor de exemplo de requisição de Cliente
```json
{
  "cpf": "String",
  "nome": "String",
  "cidade": "String",
  "uf": "String"
}
```

#### Valor de exemplo de resposta de Cliente
```json
{
  "cpf": "String",
  "nome": "String",
  "cidade": "String",
  "uf": "String"
}
```

### Conteúdo das respostas de Sucesso e de Erro de Cliente
#### Parâmetros de sucesso na exclusão de Cliente
> *erro*: false

> *message*: "Cliente CPF *cpf* excluído com sucesso!"

#### Parâmetros de resposta do erro 400 de Cliente
> *erro*: true

Os parâmetros abaixo existirão apenas se houver erro no preenchimento de seus respectivos campos no formulário:
> *cpf*: Seu valor pode ser:
> - "CPF inválido"; *ou*
> - "Descrição é obrigatório"; *ou*
> - "CPF *cpf* já existe no banco!".

> *nome*: Seu valor pode ser:
> - "Nome não pode ser null"; *ou*
> - "Nome é obrigatório"; *ou*
> - "Nome inválido!".

> *cidade*: Seu valor pode ser:
> - "Cidade não pode ser null"; *ou*
> - "Cidade é obrigatório".

> *uf*: Seu valor pode ser:
> - "UF não pode ser null"; *ou*
> - "UF é obrigatório".

#### Parâmetros de resposta do erro 404 de Cliente
> *erro*: true

> *message*: Seu valor será:
> - "Cliente CPF *cpf* não encontrado no banco de dados!".

#### Parâmetros de resposta do erro 409 de Cliente
> *erro*: true.

> *message*: Seu valor será:
> - "Falha na exclusão! Certifique-se de que o cliente CPF *cpf* não esteja relacionado em nenhuma apólice! Verifique a apólice #*numero*.

#### Possíveis parâmetros das respostas de Sucesso e de Erro de Cliente
```
"erro": "boolean",
"message": "String",
"cpf": "String",
"nome": "String",
"cidade": "String",
"uf": "String"
```

## Apólice

| ENDPOINT | Método | Parâmetros | Parâmetros de URL | Resposta de Sucesso | Resposta de Erro
|--|--|--|--|--|--|
| /apolices | `GET`  | - | - |**Code:** 200 - OK<br>**Content:** [ [Apólice](#valor-de-exemplo-de-resposta-de-apólice) ]  | - |
| /apolices | `POST` | [Apólice](#parâmetros-de-apólice) | - |**Code:** 201 - CREATED <br> **Content:** [Apólice](#valor-de-exemplo-de-resposta-de-apólice) |  **Code:** 400 - BAD REQUEST<br>**Content:** [Mensagem de erro 400](#parâmetros-de-resposta-do-erro-400-de-apólice)<br>*or*<br>**Code:** 404 - NOT FOUND<br>**Content:** [Mensagem de erro 404](#parâmetros-de-resposta-do-erro-404-de-apólice)|
| /apolices/:numero | `GET` | - | numero=Long |**Code:** 200 - OK <br> **Content:** [Apólice](#valor-de-exemplo-de-resposta-de-apólice) |  **Code:** 404 - NOT FOUND<br>**Content:** [Mensagem de erro 404](#parâmetros-de-resposta-do-erro-404-de-apólice)|
| /apolices/:numero | `PUT` | [Apólice](#parâmetros-de-apólice) | numero=Long |**Code:** 200 - OK <br> **Content:** [Apólice](#valor-de-exemplo-de-resposta-de-apólice) | **Code:** 400 - BAD REQUEST<br>**Content:** [Mensagem de erro 400](#parâmetros-de-resposta-do-erro-400-de-apólice)<br>*or*<br>**Code:** 404 - NOT FOUND<br>**Content:** [Mensagem de erro 404](#parâmetros-de-resposta-do-erro-404-de-apólice)|
| /apolices/:numero | `DELETE` | - | numero=Long |**Code:** 200 - OK <br> **Content:** [Sucesso na exclusão de Apólice](#parâmetros-de-sucesso-na-exclusão-de-apólice) |  **Code:** 404 - NOT FOUND<br>**Content:** [Mensagem de erro 404](#parâmetros-de-resposta-do-erro-404-de-apólice)|
| /apolices/consultar-por-numero/:numero | `GET` | - | numero=Long |**Code:** 200 - OK <br> **Content:** [Apólice](#valor-de-exemplo-de-resposta-de-apólice) |  **Code:** 404 - NOT FOUND<br>**Content:** [Mensagem de erro 404](#parâmetros-de-resposta-do-erro-404-de-apólice)|

### CRUD Apólice
> - GET /apolices - Listar Apólices.
> - POST /apolices - Criar Apólice (Necessário fornecer [Apólice](#parâmetros-de-apólice), dentro do corpo da solicitação).
> - GET /apolices/:numero - Exibir Apólice.
> - PUT /apolices/:numero - Atualizar Apólice (Necessário fornecer os campos de [Apólice](#parâmetros-de-apólice) a ser atualizado, dentro do corpo da solicitação).
> - DELETE /apolices/:numero - Excluir Apólice.
> - GET /apolices/consultar-por-numero/:numero - Consultar Apólice pelo número.

#### Parâmetros de Apólice
- fimVigencia
- placaVeiculo
- valor
- cliente

#### Modelo para requisição de Apólice
```
Apolice {
  fimVigencia (LocalDate "yyyy-MM-dd", required),
  placaVeiculo (String, required),
  valor (BigDecimal, required),
  cliente (String, required)
}
```

#### Valor de exemplo de requisição de Apólice
```json
{
  "fimVigencia": "LocalDate",
  "placaVeiculo": "String",
  "valor": "BigDecimal",
  "cliente": "String"
}
```

#### Valor de exemplo de resposta de Apólice
```json
{
  "numero": "Long",
  "inicioVigencia": "LocalDate",
  "fimVigencia": "LocalDate",
  "placaVeiculo": "String",
  "valor": "String",
  "cliente": {
    "cpf": "String",
    "nome": "String",
    "cidade": "String",
    "uf": "String"
  }
}
```

### Conteúdo das respostas de Sucesso e de Erro de Apólice
#### Parâmetros de sucesso na exclusão de Apólice
> *erro*: false

> *message*: "Apólice #*numero* excluída com sucesso!"

#### Parâmetros de resposta do erro 400 de Apólice
> *erro*: true

Os parâmetros abaixo existirão apenas se houver erro no preenchimento de seus respectivos campos no formulário:
> *fimVigencia*: Seu valor pode ser:
> - "Fim da vigência não pode ser null"; *ou*
> - "A data do fim da vigência deve estar à frente da data do início da vigência".

> *placaVeiculo*: Seu valor pode ser:
> - "Placa do veículo não pode ser null"; *ou*
> - "Placa do veículo é obrigatório".

> *valor*: Seu valor pode ser:
> - "Valor não pode ser null"; *ou*
> - "Valor não pode ser negativo".

> *cliente*: Seu valor pode ser:
> - "Cliente não pode ser null"; *ou*
> - "Cliente é obrigatório".

#### Parâmetros de resposta do erro 404 de Apólice
> *erro*: true

> *message*: Seu valor será:
> - "Apólice #*numero* não encontrada no banco de dados!".

Os parâmetros abaixo existirão apenas se houver erro no preenchimento de seus respectivos campos no formulário:
> *numero*: Seu valor será:
> - "Apólice #*numero* não encontrada no banco de dados!".

> *cliente*: Seu valor será:
> - "Cliente CPF *clienteCpf* não encontrado no banco de dados!".

#### Possíveis parâmetros das respostas de Sucesso e de Erro de Apólice
```
"erro": "boolean",
"message": "String",
"fimVigencia": "String",
"placaVeiculo": "String",
"valor": "String",
"cliente": "String"
```
