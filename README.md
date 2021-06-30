# api-prestataires-mobilis

POC d'intégration d'un service REST SpringBoot sous forme d'image Docker.
Ce service met à disposition la liste des boutiques de prestataires Mobilis.

## Lancement en DEV

`./mvnw spring-boot:run`

## Description technique

Ce service se compose d'une base de donnée mémoire H2 chargé à chaque démarrage via le fichier `data.sql` qui insert le 
`partenaires.csv` dans la table `PARTENAIRE`.

Les données sont exposées en REST directement par le repository via [spring-boot-starter-data-rest](https://docs.spring.io/spring-data/rest/docs/current/reference/htm) et sont accessibles directement par `curl http://localhost:8080/api/partenaires`.

## Docker

Une image docker est généré via le plugin JIB pour maven (qui permet notamment de générer une image Docker sans avoir le client docker installé), elle peut-être lancée de la manière avec la commande suivante :
- `docker run --rm -p 8080:8080 ghcr.io/opt-nc/api-partenaires-mobilis:snapshot` pour la denière snapshot
- `docker run --rm -p 8080:8080 ghcr.io/opt-nc/api-partenaires-mobilis:latest` pour la denière release

Pour tester :
- Soit un `curl http://localhost:8080/api/partenaires`
- Soit https://jsfiddle.net/3t75pke9/2/ (affichage sur une carte à partir des coordonnées GPS)

## Doc de l'API REST

SwaggerUI accessible directement à la raçine du service `http://localhost:8080/` depuis un navigateur
