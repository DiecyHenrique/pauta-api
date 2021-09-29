# pauta-api
Projeto de API para gerenciamento de sessões de votação de pautas

<p><strong>FEATURES</strong></p>
Listagem de pautas cadastradas - GET <code>/pauta</code> <br>
Cadastro de pauta - POST <code>/pauta</code> <br>
Abertura da pauta para votação - PUT <code>/pauta</code> <br> 
Realizar voto em uma pauta aberta - POST <code>/votacao/{idPauta}</code> <br> 
Exibir resultado de uma votação - GET <code>/pauta/{idPauta}</code> <br> 
Deleta uma pauta - DELETE <code>/pauta/{idPauta}</code> <br><br> 

<p><strong>TECNOLOGIAS UTILIZADAS</strong></p>
Java 11 <br>
Spring Boot <br>
Spring Data JPA <br>
PostgreSQL <br><br>

<p><strong>CONFIGURAÇÃO DO PROJETO</strong></p>
Criar banco de dados nomeado de <code>bd-pauta</code> <br>
Configurar arquivo <code>src/main/resources/application.properties</code> conforme a instalação do Postgresql <br><br>
