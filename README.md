# MforMonitoring

> Monitoring des applications via Spring Boot Actuator

### Prérequis

* NodeJS
* Maven
* Git

### Installation

Configuration dans le fichier src/resources/application.yml

Installation des plugins NodeJS :

    npm install -g grunt-cli bower
    npm install

Récupération des librairies :

    bower install
    
Création des ressources statiques :

    grunt

Construction de l'archive :
    
    mvn package

Exécution de l'archive :

    java -jar target/MforMonitoring.war
