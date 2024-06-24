# Plane AIR

*GIRAUD Nila*  
*FILIAO Luc*  
*LIEGEON Nathan*

## Sommaire

* [Objectifs du projet](#objectifs-du-projet)
* [Comment utiliser](#comment-utiliser)
  * [Lancement](#lancement)
  * [Menus](#menus)
* [Organisation des packages principaux](#organisation-des-packages-principaux)

# Objectifs du projet

Cette application à pour but d'automatiser la gestion des vols dans les aéroports, en répartissant automatiquement les vols sur des altitudes différentes si ils risquent d'entrer en conflit, et en simulant les déplacements des vols, afin de pouvoir observer le déroulement de la programmation.

Afin de répartir les vols sur des altitudes différentes, des algorithmes de coloration de graphes sont impémentés (les vols étant considérés comme des noeuds).

# Comment utiliser

## Lancement

Lorsque l'application est lancée, deux choix sont proposés :

1. [Charger un **graphe de test**](#1-charger-un-graphe-de-test)
2. [Charger une **simulation**](#2-charger-une-simulation)

### 1. Charger un graphe de test

Il faut premièrement importer le fichier d'un **graphe de test** (au format "*txt*"). Une fois que le fichier est sélectionné, la carte apparaîtra, mais rien ne pourra être affiché sur l'écran (faute d'informations), mais le graphe importé sera visible à la droite de l'écran. Aucune coloration ne sera appliquée par défaut, ce choix est laissé à l'utilisateur.

### 2. Charger une simulation

Il faut dans un premier temps importer le fichier contenant les informations des **aéroports**. Ensuite, il faudra sélectionner le fichier contenant les informations des **vols**. Un algorithme de coloration sera automatiquement appliqué, afin de directement proposer une répartition des vols, mais l'algorithme utilisé pourra bien évidemmment changé par la suite.

## Présentation des différents visuels

> *Nous nous plaçons ici dans le cas d'une simulation*

Une fois les données correctement importées, plusieurs visuels apparîtront à l'écran :

1. **La Map :** En fond de l'application se trouve la carte du monde (centrée sur la France), où les vols seront visualisés
2. **Le graphe :** Le graphe représentant les vols importés. Deux vols liés par une arête signifie qu'ils entrent en collision si ils sont sur la même couche.
3. **Les informations du graphe :** Le panneau montrant les informations du graphe, comme le nombre de noeuds ou d'arêtes, et le **nombre de conflits**
4. **Les menus :** Ils permettent de manipuler les paramètres, et sont décrits plus bas

## Menus

Afin de changer les paramètres utilisés, l'utilisateur à accès à plusieurs menus :

1. [Le Menu Graphe](#menu-graphe)
2. [Le Menu Map](#menu-map)

### Menu Graphe

Le premier est le *Menu Graphe*, qui permet de manipuler les paramètres suivants :

| Section | Action |
| :-: | :- |
| **Changer K-Max** | *Permet de modifier le nombre de couleurs max utilisé pour colorer le graph. Entre autres le **nombre maximum d'altitudes*** |
| **Choix de la couleur** | *Permet de sélectionner une **couleur/altitude** à visualiser* |
| **Changer marge de sécurité** | *Permet de varier le nombre de minutes en dessous de laquelle deux vols sont susceptibles d'**entrer en collision**, et donc mis en relation dans le graphe* |
| **Algorithmes** | *Permet de choisir **l'algorithme de coloration** à utiliser* |

### Menu Map

Le deuxième est le *Menu Map*, qui permet de séélectionner les éléments à afficher sur la carte :

| Élément | Action |
| :-: | :- |
| **Lignes de vol** | *Afficher on non les lignes représentant les trajectoires des vols* |
| **Aéroports utilisés** | *Afficher ou non les aéroports utilisés (en rouge) par les vols importés* |
| **Aéroports inutilisés** | *Afficher ou non les aéroports non utilisés (en gris) par les vols importés* |
| **Vols** | *Afficher ou non les vols sur la carte* |

## Organisation des packages principaux

> main \> java \> planeair

| Package | Détails |
| :-: | :- |
| **components** | *Tous les composants graphiques* |
| **exceptions** | *Les exceptions créées pour le projet* |
| **graph** | *Tout ce qui est lié aux graphes (noeuds, types de graphes, algorithmes de coloration, affichage)* |
| **importation** | *Les algorithmes de coloration* |
| **util** | *Les classes condiérées comme 'utilitaires'* |
