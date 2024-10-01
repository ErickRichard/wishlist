<div class="w3-container">
  
<h1 align="left">Wishlist</h1>
Este é um projeto Spring Boot que utiliza Docker e MongoDB como banco de dados, com integração de relatórios de cobertura de código usando JacocoReport. O projeto automatiza a criação do banco de dados e documentos utilizando Mongock, e oferece uma documentação de API acessível através do Swagger UI.

<h2 class="w3-text-green w3-xxlarge"> 1. Pré-requisitos </h2>
Antes de rodar o projeto, você precisará garantir que possui as seguintes ferramentas instaladas:

Docker Desktop
MongoDB Compass
Java 17
Maven

<ul>
        <li>Item 1</li>
        <li>Item 2</li>
        <li>Item 3</li>
        <li>Item 4</li>
    </ul>

<h2 class="w3-text-green w3-xxlarge">2. Instalar Dependências</h2>
Utilize o Maven para fazer o build e instalar as dependências do projeto:

mvn clean install

<h2 class="w3-text-green w3-xxlarge">3. Executar o Docker Compose</h2>
Na raiz do projeto, execute o comando abaixo para subir os containers Docker:

docker-compose up --build

<h2 class="w3-text-green w3-xxlarge">4. Documentação da API</h2>
A documentação completa dos endpoints está disponível através do Swagger UI. Após subir o projeto, acesse a URL abaixo:

http://localhost:8080/swagger-ui/index.html
</div>
