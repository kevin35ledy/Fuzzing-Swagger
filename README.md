# Fuzzing-Swagger

##Introduction
Ce projet a pour but de créer un fuzzer adapté pour les API REST développées en Node js.

Swagger nous permet de représenter de manière normalisée les API. La méthode utilisée pour tester l'API sera un fuzzer.

Ce fuzzer va générer des données aléatoires et essayer de détecter les failles du système.


##Installation
Pour jouer avec swagger :

Installer swagger : 
```sudo npm install -g swagger```

Créer un projet : 
```swagger project create <project name>```

###Liens utiles :
Le lien ci-dessous nous a permis de développer la partie serveur en Node JS avec le module Swagger.
https://scotch.io/tutorials/speed-up-your-restful-api-development-in-node-js-with-swagger

OWASP donne un essemble de test de fuzzing pour regaredr les failles :
https://www.owasp.org/index.php/OWASP_Testing_Guide_Appendix_C:_Fuzz_Vectors


Lancer l'éditeur Swagger : 
```swagger project edit``

------------------------------
#Parsing du swagger yaml vers json 

Parseur yaml -> json
```brew install swagger-codegen```
Se placer dans le dossier du projet swagger
```cd api/swagger/```
on génère le fichier json
```swagger-codegen generate -i swagger.yaml -l swagger```


##Fuzzer
Le but principal du projet consiste à fuser une api Swagger.
Par conséquent nous avons trouvé une api représentant un service e-commerce d'animaux de compagnie, vous pourrez trouver cette API à l'adresse suivante : <a>http://petstore.swagger.io/</a>

Ainsi pour installer notre projet, il faut télécharger les sources, ajouter les jars :

`commons-lang3-3.5`, 
`httpclient-4.5.2`, `httpmime-4.5.2`, `jackson-annotations-2.8.5`, `jackson-core-2.8.5`, `jackson-databind-2.8.5`, `jackson-dataformat-yaml-2.8.5`, `json-20160810`, `slf4j-api-1.7.21`, `swagger-core-1.5.10`
, `swagger-models-1.5.10`, `swagger-parser-1.0.23`

Et runner la classe Launcher.

Celle-ci va générer et exécuter les requêtes pour fuser l'api swagger du petstore.

Le Launcher fait appel au JSONParser qui va récupérer le swagger.json (méta-modèle de l'api), à l'adresse suivante : <a>http://petstore.swagger.io/v2/swagger.json</a>

Ensuite, celui-ci parse le json pour fabriquer des Paths avec des Opérations de type GET, POST, PUT, DELETE, et leurs paramètres.

Les résultats des tests sont enregistrés dans un fichier csv.

Pour tester une autre api swagger, vous devez modifier l'url du swagger.json dans le JSONParser, ainsi que l'adresse ou vont les requêtes dans le Launcher.


