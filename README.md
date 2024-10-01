<div class="w3-container">
  
<h1 align="left">Wishlist</h1>
Este é um projeto Spring Boot que utiliza Docker e MongoDB como banco de dados, com integração de relatórios de cobertura de código usando JacocoReport. O projeto automatiza a criação do banco de dados e documentos utilizando Mongock, e oferece uma documentação de API acessível através do Swagger UI.

<h2 class="w3-text-green w3-xxlarge"> 1. Pré-requisitos </h2>
Antes de rodar o projeto, você precisará garantir que possui as seguintes ferramentas instaladas:
<ul>
<li>Docker Desktop</li>
<li>MongoDB Compass</li>
<li>Java 17</li>
<li>Maven</li>
</ul>

<h2 class="w3-text-green w3-xxlarge">2. Instalar Dependências</h2>
Utilize o Maven para fazer o build e instalar as dependências do projeto:

<div style="text-align: left;">
<pre style="background-color: #f3f3f3; padding: 10px; border-radius: 5px; border: 1px solid #d1d1d1; font-family: 'Courier New', Courier, monospace; white-space: pre-wrap; margin-left: 0; width: fit-content; height: auto; overflow: auto;">
    <code>mvn clean install</code>
  </pre>



<h2 class="w3-text-green w3-xxlarge">3. Executar o Docker Compose</h2>
Na raiz do projeto, execute o comando abaixo para subir os containers Docker:


<pre style="background-color: #f3f3f3; padding: 10px; border-radius: 5px; border: 1px solid #d1d1d1; font-family: 'Courier New', Courier, monospace; white-space: pre-wrap; margin-left: 0; width: fit-content; height: auto; overflow: auto;">
<code>
    docker-compose up --build
</code>
</pre>
</div>


<h2 class="w3-text-green w3-xxlarge">4. Documentação da API</h2>
A documentação completa dos endpoints está disponível através do Swagger UI. Após subir o projeto, acesse a URL abaixo:

http://localhost:8080/swagger-ui/index.html
</div>
