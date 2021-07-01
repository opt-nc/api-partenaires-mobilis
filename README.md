# api-prestataires-mobilis

POC d'intégration d'un service REST SpringBoot sous forme d'image Docker.
Ce service met à disposition la liste des boutiques de prestataires Mobilis.

## Lancement en DEV

`./mvnw spring-boot:run`

## Description technique

Ce service se compose d'une base de donnée mémoire H2 chargé à chaque démarrage via le fichier `data.sql` qui insert le 
`partenaires.csv` dans la table `PARTENAIRE`.

Une base de données spactiale embarquée (http://www.h2gis.org/) permet de faire des interrogations sur des zones géographiques.

Les données sont exposées en REST Json/[GeoJSON](https://geojson.org/) pour pemettre une intégration dans un outil de visu le plus simplement possible.


Exemple : 
```bash
curl http://localhost:8080/api/partenaires
```
```json
{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "properties": {
        "ville": "Bourail",
        "quartier": null,
        "adresse": "5 A rue Simone Dremon - Village",
        "telephone": "44 33 55",
        "nom": "BOURAIL ELECTRONIC"
      },
      "geometry": {
        "type": "Point",
        "coordinates": [
          165.49879,
          -21.56877
        ]
      }
    }
    ...
  ]
}
```

## Docker

Une image docker est généré via le plugin JIB pour maven (qui permet notamment de générer une image Docker sans avoir le client docker installé), elle peut-être lancée de la manière avec la commande suivante :
- `docker run --rm -p 8080:8080 ghcr.io/opt-nc/api-partenaires-mobilis:snapshot` pour la denière snapshot
- `docker run --rm -p 8080:8080 ghcr.io/opt-nc/api-partenaires-mobilis:latest` pour la denière release

Pour tester :
- Soit un `curl http://localhost:8080/api/partenaires`
- Soit https://jsfiddle.net/3t75pke9/2/ (affichage sur une carte à partir des coordonnées GPS)

## Doc de l'API REST

SwaggerUI accessible directement à la raçine du service `http://localhost:8080/` depuis un navigateur
