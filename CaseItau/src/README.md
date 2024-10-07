# DESAFIO

## Descrição
Este projeto é uma API REST que permite o cadastro de clientes, listagem de clientes, consulta por número de conta, e realização de transferências entre contas com validação de saldo.

## Funcionalidades
- Cadastrar cliente com ID único, nome, número de conta e saldo.
- Listar todos os clientes cadastrados.
- Buscar cliente pelo número de conta.
- Realizar transferências entre duas contas, garantindo que o saldo seja suficiente e que o valor não ultrapasse R$ 100,00.

## Pré-requisitos
Antes de começar, você precisará ter as seguintes ferramentas instaladas:
- **[Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)**
- **[Maven 3.6+](https://maven.apache.org/download.cgi)**

## Instalação
1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/seu-repositorio.git
    ```

2. Navegue até o diretório do projeto:
    ```bash
    cd seu-repositorio
    ```

3. Compile o projeto:
    ```bash
    mvn clean install
    ```

4. Execute a aplicação:
    ```bash
    mvn spring-boot:run
    ```

## Configuração
A aplicação usa configurações de ambiente para definir o banco de dados e outros parâmetros. Verifique o arquivo `application.properties` no diretório `src/main/resources` para personalizar.

## Como Usar

## Endpoints Principais

1. Cadastrar cliente
- POST /clientes
- - Exemplo de resposta (200 OK)
- {
"id": 1,
"nome": "João Silva",
"numeroConta": "12345",
"saldo": 500.00 }

2. Listar todos os clientes
- GET /clientes
- - Exemplo de resposta (200 OK)
- 
  {
  "id": 1,
  "nome": "João Silva",
  "numeroConta": "12345",
  "saldo": 500.00 }

3. Buscar cliente por número de conta
- GET /clientes/{numeroConta}
- - Exemplo de resposta (200 OK)
- {
  "id": 1,
  "nome": "João Silva",
  "numeroConta": "12345",
  "saldo": 500.00
  }

4. Realizar transferência
- POST /transferencia
- Parâmetros:
- numeroContaOrigem: Número da conta de origem.
- numeroContaDestino: Número da conta de destino.
- valor: Valor da transferência (máximo R$ 100,00).
- Exemplo de corpo da requisição:
- - {
    "numeroContaOrigem": "12345",
    "numeroContaDestino": "54321",
    "valor": 50.00
    }
- Exemplo de resposta (200 OK)
- {
  "mensagem": "Transferência realizada com sucesso!"
  }

## Regras de Negócio
- A conta de origem deve ter saldo suficiente.
- O valor da transferência não pode ultrapassar R$ 100,00.

## Testes
Para rodar os testes unitários, use o seguinte comando:

```bash
    mvn clean install
    