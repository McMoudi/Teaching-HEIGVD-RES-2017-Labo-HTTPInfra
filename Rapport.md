# Rapport RES Labo HTTP Infra

## Etape 1: Serveur HTTP statique avec apache httpd

Pour cette première étape nous avons préparé un **Dockerfile** à partir de l'image `php 7.0 apache`.
nous avons ajouté une instruction `COPY` afin de copier notre contenu dans le répertoire approprié. Le contenu se compose d'un site web élaboré à l'aide du template **Bootstrap** "Greyscale".

## Etape 2: Serveur HTTP dynamique avec express.js

Pour cette étape, nous avons préparé un **Dockerfile** à partir de l'image `node 6.10.3`.
Nous avons ajouté une instruction `COPY` afin de copier les fichiers de notre application dans le répertoire approprié. Puis nous avons ajouté une instruction `CMD` afin d'exécuter notre application **Node**.

Notre application **Node** se compose d'un serveur **express.js** qui génère des adresses aléatoires à l'aide du module **Chance**. Le serveur se met à l'écoute sur le port 3000 et lorsqu'un client se connecte il lui envoie entre 0 et 10 adresses aléatoires au format **JSON**.

## Etape 3: Reverse proxy avec apache (configuration statique)
un reverse proxy a été configuré dans le fichier `001-reverse-proxy.conf`. Il faut ajuster les adresses ip à chaque lancement manuellement dans ce fichier pour l'instant. La configuration du container a légprement changé pour inclure le nouveau ficher de configuration ainsi que les dépendances requises.

## Etape 4: AJAX
Un petit script a été concocté par nos soin dans le dossier `js`. Il est chargé tout en bas de la page statique principale et est appelé tout en haut, une fois loadé, pour afficher une nouvelle adresse toutes les quelques secondes.


## Etape 5: configuration du reverse proxy dynamique

Pour rendre le proxy dynamique nous avons ajouté l'option `--net=res` à toutes les images pour qu'elles se retrouvent dans un même subnet. Ceci a comme effet que le proxy peut atteindre les serveurs avec leurs noms de containeur. (le containeur apache statique 1 s'appelle `apache_static1`, le deuxième `apache_static2` etc).

## Etapes additionnels

### Load balancing
  Le load balancing s'est fait dans un fichier de configuration de virtualhost différent (002-*).
  Il y a 2 balancer, un pour la partie statique, l'autre pour la partie dynamique. LE setting `lbmethod=byrequests` indique que c'est du round robin.
  Pour vérifier notre bonne configuration, l'adresse ip locale du serveur est affiché au milieu de la page web statique. Du côté dynamique, on voit qu'en chargeant qu'une seule page et en attendant quelques requêtes ajax, plusieurs (voir tous!) serveurs dynamiques ont été contacté.
  
### Sticky sessions

pour cette partie, nous avons ajouté la ligne suivante à notre fichier de configuration:

     Header add Set-Cookie "ROUTEID=.%{BALANCER_WORKER_ROUTE}e; path=/" env=BALANCER_ROUTE_CHANGED
     
Elle fait en sorte que le proxy mette un cookie `ROUTEID` avec la valeur de la route empruntée. ` ProxySet stickysession=ROUTEID` quand à lui, indique qu'il faut se baser sur la valeur du cookie `ROUTEID` pour choisir le serveur à contacter.


  
