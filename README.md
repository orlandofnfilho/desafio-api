# Desafio MVC GFT Start #4

## 📋  Sobre

Projeto com o desafio de API durante o programa Start #4

----------

## 🚀  Tecnologias utilizadas

O projeto foi desenvolvido utilizando as seguintes tecnologias


 1. [Java](https://www.java.com/pt-BR/)
 2. [Maven](https://maven.apache.org/)
 3. [Spring Boot](https://spring.io/projects/spring-boot)
 4. [Swagger](https://swagger.io/)
 5. [Lombok](https://projectlombok.org/)
 6. [MySQL](https://www.mysql.com/)
 7. [The Dog API](https://thedogapi.com/)
 8. [JUnit5](https://junit.org/junit5/docs/current/user-guide/)
 8. [Mockito](https://site.mockito.org/)

----------

## 📁 Informações

Importar o projeto como projeto Maven, verificar as informações de usuário de senha do banco de dados após importar o projeto. Ao realiza o primeiro acesso, a aplicação tem por padrão 2 perfis de privilégios, ADMIN e USUARIO que já serão salvas no banco ao iniciar. Sendo também necessário o envio do token JWT que é retornado ao fazer autenticação no controller Auth.
#### Credenciais para acesso de ADMIN:
email: admin@gft.com
password: Gft@1234

#### Credenciais para acesso de USUÁRIO:
email: usuario@gft.com
password: Gft@1234

## Endpoints:
- Autenticação: 
    | Método | URL                                        | Perfil(s) Autorizado(s) | 
    | ------ | ---                                        | ----------------------- |
    | GET    | http://localhost:8080/vetApi/v1/auth       | Público                 |

- Usuários:
    | Método | URL                                             | Perfil(s) Autorizado(s) | 
    | ------ | ---                                             | ----------------------- |
    | POST   | http://localhost:8080/vetApi/v1/users           | ADMIN, USUARIO          |
    | GET    | http://localhost:8080/vetApi/v1/users           | ADMIN                   |
    | GET    | http://localhost:8080/vetApi/v1/users/{id}      | ADMIN                   |
    | PUT    | http://localhost:8080/vetApi/v1/users/{id}      | ADMIN, USUARIO          |
    | PATCH  | http://localhost:8080/vetApi/v1/users/{id}      | ADMIN                   |
    | DELETE | http://localhost:8080/vetApi/v1/users/{id}      | ADMIN                   |

- Clientes:
    | Método | URL                                                 | Perfil(s) Autorizado(s) | 
    | ------ | ---                                                 | ----------------------- |
    | POST   | http://localhost:8080/vetApi/v1/clients             | ADMIN                   |
    | GET    | http://localhost:8080/vetApi/v1/clients             | ADMIN                   |
    | GET    | http://localhost:8080/vetApi/v1/clients/{id}        | ADMIN                   |
    | PUT    | http://localhost:8080/vetApi/v1/clients/{id}        | ADMIN                   |
    | DELETE | http://localhost:8080/vetApi/v1/clients/{id}        | ADMIN                   |

- Veterinários:
    | Método | URL                                                | Perfil(s) Autorizado(s) | 
    | ------ | ---                                                | ----------------------- |
    | POST   | http://localhost:8080/vetApi/v1/veterinarians      | ADMIN                   |
    | GET    | http://localhost:8080/vetApi/v1/veterinarians      | ADMIN, USUARIO          |
    | GET    | http://localhost:8080/vetApi/v1/veterinarians/{id} | ADMIN                   |
    | PUT    | http://localhost:8080/vetApi/v1/veterinarians/{id} | ADMIN                   |
    | DELETE | http://localhost:8080/vetApi/v1/veterinarians/{id} | ADMIN                   |

- Raças:
    | Método | URL                                                | Perfil(s) Autorizado(s) | 
    | ------ | ---                                                | ----------------------- |
    | POST   | http://localhost:8080/vetApi/v1/breeds             | ADMIN                   |
    | GET    | http://localhost:8080/vetApi/v1/breeds             | ADMIN, USUARIO          |
    | GET    | http://localhost:8080/vetApi/v1/breeds/{id}        | ADMIN                   |
    | PUT    | http://localhost:8080/vetApi/v1/breeds/{id}        | ADMIN                   |
    | DELETE | http://localhost:8080/vetApi/v1/breeds/{id}        | ADMIN                   |

- Cachorros:
    | Método | URL                                                | Perfil(s) Autorizado(s) | 
    | ------ | ---                                                | ----------------------- |
    | POST   | http://localhost:8080/vetApi/v1/dogs               | ADMIN                   |
    | GET    | http://localhost:8080/vetApi/v1/dogs               | ADMIN                   |
    | GET    | http://localhost:8080/vetApi/v1/dogs/{id}          | ADMIN                   |
    | PUT    | http://localhost:8080/vetApi/v1/dogs/{id}          | ADMIN                   |
    | DELETE | http://localhost:8080/vetApi/v1/dogs/{id}          | ADMIN                   |

- Atendimentos:
    | Método | URL                                                | Perfil(s) Autorizado(s) | 
    | ------ | ---                                                | ----------------------- |
    | POST   | http://localhost:8080/vetApi/v1/appointments       | ADMIN                   |
    | GET    | http://localhost:8080/vetApi/v1/appointments       | ADMIN                   |
    | GET    | http://localhost:8080/vetApi/v1/appointments/{id}  | ADMIN                   |
    | GET    | http://localhost:8080/vetApi/v1/appointments/dog/{regCod}| ADMIN, USUARIO                   |
     | GET    | http://localhost:8080/vetApi/v1/appointments/dog/{crmv}| ADMIN                   |
    | PUT    | http://localhost:8080/vetApi/v1/appointments/{id}  | ADMIN                   |
    | DELETE | http://localhost:8080/vetApi/v1/appointments/{id}  | ADMIN                   |

- The Dog API:
    | Método | URL                                                | Perfil(s) Autorizado(s) | 
    | ------ | ---                                                | ----------------------- |
    | POST   | http://localhost:8080/vetApi/v1/thedogapi               | ADMIN, USUARIO                   |
    | GET    | http://localhost:8080/vetApi/v1/dogs               | ADMIN, USUARIO                   |
    | DELETE | http://localhost:8080/vetApi/v1/dogs/{vote_id}          | ADMIN, USUARIO                   |


A documentação completa dos Endpoints estará disponível através do Swagger acessível pelo link: http://localhost:8080/swagger-ui.html  

## Sobre o sistema:
* O perfil ADMIN tem a maior parte de acesso aos CRUD da API sendo assim disponibilizado aos veterinários da aplicação, já o perfil ADMIN que seria de acesso dos clientes. Os clientes tem podem fazer as consultas das raças cadastradas no sistema, como também dos atendimentos realizados por seus cachorros através do regCod de cada um deles.


* Também é possível fazer a consulta de todos os atendimentos realizados por cada médico, esse por sua vez só com o perfil ADMIN e pelo CRMV do médico.

* O regCod de cada cachorro é gerado automaticamente no seu cadastro. Sendo um código único de 6 digitos com letras contidas no nome do seu tutor e números.

* A data do atentimento também é gerada no momento do seu cadastro.

* CPF, CRMV(5 numeros) e E-mail dos usuários são registros únicos.

* Para CRMV foi criado uma Annotation personalizada, à qual necessita de ser 5 números. 

* Caso tente a exclusão de algum recurso associado a outro, o delete será barrado por uma exceção retornando erro 400.

* Ao cadastrar uma nova raça o sistema faz a pesquisa no banco de dados pelo nome, caso não tenha o registro, fará a consulta na The Dog API(sendo importante passar o nome exato conforme consta na mesma). Resgatando assim as informações já disponibilizadas por eles de origem, temperamento, tempo de vida e etc.

* O controller TheDogApi faz requisições na The Dog API, o GET(findAll) retorna a lista com todas as raças cadastradas. O POST(createVote) vota numa imagem através do id passado no corpo da requisição, sendo 1 para Up Vote e 0 Down Vote. Já o DELETE(deleteVote) deleta o voto através do id do voto retornado na requisição createVote.


IMPORTANTE: Como é uma aplicação que possui checagem de permissão de acesso, é necessário que se utilize token para as requisições através do Endpoint descrito na tabela de Autenticação. 
### Autenticando e utilizando o token no Swagger
Para fazer a autenticação e utilizar o token no Swagger siga os passos:  
1- Na página do Swagger (http://localhost:8080/swagger-ui.html ) procure por "1. Auth Manage auth", depois "/vetApi/v1/auth
Token creation operation" e clique em "Try it out"  
![Swagger Authentication Step 01](docs/swagger_auth01.png?raw=true "Swagger Authentication Step 01")  
2- Irá ter um campo de texto (textarea) com um JSON de modelo, altere o email e password para email e senha de usuário já cadastrado (exemplo: admin@gft.com), depois é só clicar em "Execute"  
![Swagger Authentication Step 02](docs/swagger_auth02.png?raw=true "Swagger Authentication Step 02")  
3- Após a execução irá aparecer a resposta abaixo na seção "Server response", o token estará no "Response body", copiar somente o token sem as aspas (Caso usuário e senha seja inválido, será retornado erro 401.)  
![Swagger Authentication Step 03](docs/swagger_auth03.png?raw=true "Swagger Authentication Step 03")  
4- Clicar no botão "Authorize" que fica logo abaixo do cabeçalho da página  
![Swagger Authentication Step 04](docs/swagger_auth04.png?raw=true "Swagger Authentication Step 04")  
5- Na janela que abrir, informar o token gerado e copiado no passo 3.  
**IMPORTANTE:** É necessário a adição do prefixo "Bearer " (sem aspas), com espaço entre o Bearer e o token para que ele funcione corretamente. É só clicar em "authorize" e seguir para o Swagger normalmente que já estará autorizado conforme o perfil do usuário que usou no passo 2 para gerar o token. 
![Swagger Authentication Step 05](docs/swagger_auth05.png?raw=true "Swagger Authentication Step 05")  
