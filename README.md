# AulasGes

Um projeto desenvolvido na faculdade que consiste em uma aplicação para gestão de aulas. É disponibilizado 2 tipos de cliente (uma aplicação recorrendo a um navegador web e outra aplicação cliente desktop) permitindo assim a utilização dos serviços do sistema a partir de diversos dispositivos com ligações à internet.

## Um pouco técnico
A camada de negócio suporta pedidos concorrentes a ser acedida por vários tipos de clientes.
A aplicação web interage com a camada de negócio tirando partido do Java EE Web Container.
A aplicação desktop disponibiliza uma interface GUI que acede à cada de negócio utilizando RMI, recorrendo ao Java EE Application Client Container.

### Este Projeto Enterprise Java Beans(do tipo maven) é composto por cinco sub-projetos:
* **aulasges**, que agrega a informaçãodos restantes
* **aulasges-ear**, que contem informação de como empacotar a aplicação empresarial Java a instalar no servidor
* **aulasges-business**, que contem a camada de negócio organizada de acordo com o padrão __Domain Model__ e __Data Mapper JPA__
* **aulasges-web-client**, que é um cliente Web desenvolvido de acordo com o padrão __Model-View-Controller__ aplicado à web, que  usa JSP para a visualizaçãode informação (padrões __Server Side Template__ e __Template View__) e Servlets para implementar o controlador (padrão __Front controller__). Este cliente acede à camada de negócio utilizando __EJB de sessão remotos__
* **aulasges-gui-client**, que é um cliente com uma GUI programada em JavaFX que acede à camada de negócio via __RMI__ recorrendo ao __Java EE Application Client Container__

--------------------------------------
## Necessário:
* [*Maven*](http://maven.apache.org)
* [Java 8 - JDK Oracle](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [*Wildfly*](https://download.jboss.org/wildfly/10.0.0.Final/wildfly-10.0.0.Final.zip) & [*JBossTools*](https://www.baeldung.com/eclipse-wildfly-configuration)

--------------------------------------
## Antes do build
* é necessário ir a aulasges-business/src/main/resources/META-INF/aulasges-ee-ds.xml e alterar:
	* connection-url: colocar o URL para a sua MYSQL database
	* user-name: colocar o username da DB
	* password: colocar a passworda da DB
* é necessário ir a aulasges-business/login.properties e configurar/alterar o necessário.

## Truque para fazer o BUILD em caso de erro:
FAZER TODOS OS PASSOS AQUI DESCRITOS:
1. Clicar em 'Run As > Maven build...' e introduzir no goals 'clean package' -> IRÁ DAR ERRO
2. Clicar na pasta do projeto e 'Maven > Update Project...' selecionar FORCE e fazer o update de todo o projeto
3. Clicar em 'Run As > Maven build...' e introduzir no goals APENAS 'package'

--------------------------------------
## Correr Cliente WEB:
(depois de fazer o BUILD)
RUN:
No eclipse fazer 'Run on a server' e depois escolher o Wildfly


## Correr Cliente desktop:
(depois de fazer o BUILD e necessita de o cliente Web correr pois este coloca o servidor "em cima")
Nota: confirmar que na pasta do projeto aulages tem-se ulasges-ear/target/aulasges-ear-1.0.ear; pode-se obter uma cópia de PATH-TO-WILDFLY/standalone/deployments ou pode-se produzir atraves do eclipse usando 'export from the context menu,over aulas-ges-ear project'

RUN:
PATH-TO-WILDFLY/bin/appclient.sh aulasges-ear/target/aulasges-ear-1.0.ear#aulasges-gui-client.jar
