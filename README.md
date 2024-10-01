<h1 align="center">Wishlist</h1>
Este é um projeto Spring Boot que utiliza Docker e MongoDB como banco de dados, com integração de relatórios de cobertura de código usando JacocoReport. O projeto automatiza a criação do banco de dados e documentos utilizando Mongock, e oferece uma documentação de API acessível através do Swagger UI.

1. Pré-requisitos
Antes de rodar o projeto, você precisará garantir que possui as seguintes ferramentas instaladas:

Docker Desktop
MongoDB Compass
Java 17
Maven

2. Instalar Dependências
Utilize o Maven para fazer o build e instalar as dependências do projeto:

mvn clean install

3. Executar o Docker Compose
Na raiz do projeto, execute o comando abaixo para subir os containers Docker:

docker-compose up --build

4. Documentação da API
A documentação completa dos endpoints está disponível através do Swagger UI. Após subir o projeto, acesse a URL abaixo:

http://localhost:8080/swagger-ui/index.html
