# Fuzzing-Swagger

##Introduction
Ce projet a pour but de créer un fuzzer adapté pour les API REST développées en Node js.

Swagger nous permet de représenter de manière normalisée les API. La méthode utilisée pour tester l'API sera un fuzzer.

Ce fuzzer va générer des données aléatoires et essayer de détecter les failles du système.


##Installation

Installer swagger : 
```sudo npm install -g swagger```

Créerun projet : 
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
