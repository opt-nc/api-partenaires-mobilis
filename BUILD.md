# Developpement

## Spring-native

La doc est [ici](https://docs.spring.io/spring-native/docs/0.11.2/reference/htmlsingle/)

### Lancement de la génération native en local (pour le débogage)

Des prérequis sont nécessaires, tout est décrit [ici]https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/#getting-started-native-build-tools()

```bash
mvn clean -Pnative package
```

### Configuration

En cas d'erreurs de classes manquantes lors de l'éxecution depuis le binaire natif, c'est probalement liée à des mécanismes de réflection Java. [Un outil](https://docs.spring.io/spring-native/docs/0.11.2/reference/htmlsingle/#tracing-agent) est mise à disposition pour analyser les classes utiliées par réflection et sortir les fichiers de config `reflect-config.json` et `resource-config.json`utiles lors de la génération du binaire.

Cet outil peut-être utilisé lors de l'éxecution des tests unitaires (si la couverture est suffisante) : 
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <argLine>-agentlib:native-image-agent=access-filter-file=src/main/resources/META-INF/native-image/access-filter.json,config-output-dir=target/classes/META-INF/native-image</argLine>
    </configuration>
</plugin>
```

Pour affiner l'analyse en excluant ce qui est déjà géré par Spring Boot, il faut configurer un fichier `access-filter.json` : 
```json
{
  "rules": [
    { "excludeClasses": "org.apache.maven.surefire.**" },
    { "excludeClasses": "net.bytebuddy.**" },
    { "excludeClasses": "org.apiguardian.**" },
    { "excludeClasses": "org.junit.**" },
    { "excludeClasses": "org.mockito.**" },
    { "excludeClasses": "org.springframework.**" },
    { "excludeClasses": "org.springdoc.**" },
    { "excludeClasses": "com.fasterxml.**" },
    { "excludeClasses": "java.**" },
    { "excludeClasses": "javax.**" },
    { "excludeClasses": "org.hibernate.**" },
    { "excludeClasses": "ch.qos.logback.**" },
    { "excludeClasses": "com.sun.**" },
    { "excludeClasses": "sun.**" },
    { "excludeClasses": "jdk.**" },
    { "excludeClasses": "com.zaxxer.hikari.**" },
    { "excludeClasses": "io.micrometer.**" },
    { "excludeClasses": "io.swagger.**" },
    { "excludeClasses": "nonapi.**" },
    { "excludeClasses": "org.webjars.**" },
    { "excludeClasses": "org.yaml.**" },
    { "excludeClasses": "org.apache.logging.log4j.**" },
    { "excludeClasses": "org.apache.catalina.**" },
    { "excludeClasses": "nc.opt.**" }
  ]
}
```
Il faut ensuite lancer les tests et retravailler les fichiers généreux qui sont assez verbeux
