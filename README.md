API de Locadora de Itens

Este projeto é uma API feita para a disciplina de Programação 3. A ideia é simular o funcionamento de uma locadora, permitindo cadastrar itens, fazer empréstimos, renovar, devolver e calcular multas quando houver atraso.

-Tecnologias usadas

Java 17

Spring Boot

Spring Web

Spring Data JPA

Banco H2

Maven

-Como rodar o projeto Requisitos

JDK 17 instalado

Maven instalado

IDE (usei IntelliJ IDEA)

-Passo a passo

Baixe ou clone o projeto.

Abra o projeto no IntelliJ.

Execute a classe ApiApplication.

A API sobe na porta 8080 automaticamente.

-Banco de Dados (H2)

O projeto já vem configurado com banco H2 em memória. Não precisa instalar nada.

Console do banco:

http://localhost:8080/h2-console

-Dados de acesso:

JDBC URL: jdbc:h2:mem:locadora

Usuário: sa

Senha: (deixe em branco)

Endpoints principais (base: /api/v1)

--Itens

POST /itens — cadastra item

GET /itens/{id} — busca item

GET /itens/{id}/disponibilidade — mostra estoque disponível

--Empréstimos

POST /emprestimos — cria empréstimo

POST /emprestimos/{id}/renovar — renova empréstimo

POST /emprestimos/{id}/devolver — devolve item

--Usuários

GET /usuarios/{id}/dividas — mostra total de dívidas do usuário

-Infraestrutura

A infraestrutura é simples e já vem pronta:

Spring Boot sobe o servidor

Banco H2 é criado automaticamente

-Observação

O projeto foi desenvolvido para fins acadêmicos e segue as regras exigidas no trabalho.
