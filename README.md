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



<h2 class="w3-text-green w3-xxlarge">4. Documentação da API</h2>
A documentação completa dos endpoints está disponível através do Swagger UI. Após subir o projeto, acesse a URL abaixo:

http://localhost:8080/swagger-ui/index.html
</div>



<ul>
  <li>Clone o projeto para a sua máquina local.</li>
  <li>Execute o comando <pre style="display: inline-block; background-color: #f3f3f3; padding: 2px 5px; border-radius: 3px; border: 1px solid #d1d1d1; font-family: 'Courier New', Courier, monospace; width: auto; white-space: pre-wrap; margin: 0;"><code>mvn clean install</code></pre> para construir o projeto.</li>
  <li>Na pasta onde o projeto foi clonado, execute o comando <pre style="display: inline-block; background-color: #f3f3f3; padding: 2px 5px; border-radius: 3px; border: 1px solid #d1d1d1; font-family: 'Courier New', Courier, monospace; width: auto; white-space: pre-wrap; margin: 0;"><code>docker-compose up --build</code></pre> para rodar os containers com Docker.</li>
  <li>Execute o MongoDB Compass, clique em "Add New Connection" e coloque um nome para sua conexão, ex: <b>development</b>. Configure a conexão conforme necessário.</li>
  <li>Os documentos serão criados automaticamente, pois o projeto está usando <b>Mongock</b>.</li>
  <li>A documentação Swagger para os endpoints estará disponível em: <a href="http://localhost:8080/swagger-ui/index.html#">http://localhost:8080/swagger-ui/index.html#/</a></li>
</ul>
