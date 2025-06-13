# TP Spark - Manipulation de Données avec Scala et SBT

Ce projet est un exercice pratique de manipulation de données avec Apache Spark, réalisé dans le cadre d'un TP pour EPSI. L'application charge un fichier CSV, effectue des transformations de base sur les données (filtrage, agrégation) et sauvegarde les résultats au format Parquet.

## ✨ Fonctionnalités

- **Chargement de données** : Lit un fichier `ventes_fictives.csv`.
- **Affichage du Schéma** : Affiche la structure et les types de données du DataFrame.
- **Filtrage** : Sélectionne les enregistrements où la quantité vendue est supérieure à 30.
- **Agrégation** : Calcule le revenu total par région.
- **Sauvegarde** : Enregistre les DataFrames filtrés et agrégés au format Parquet dans le dossier `output/`.

## 🛠️ Technologies Utilisées

- **Scala** : `2.12.18`
- **SBT** : `1.11.2`
- **Apache Spark** : `3.5.0`

## 📂 Structure du Projet

```
.
├── build.sbt                   # Fichier de configuration SBT avec les dépendances
├── data
│   └── ventes_fictives.csv       # Données d'entrée
├── output/                     # Dossier de sortie pour les fichiers Parquet
├── project
│   └── build.properties        # Propriétés du build SBT
├── src/main/scala
│   └── ScalaApp.scala          # Code source de l'application Spark
└── .sbtopts                    # Options de la JVM pour la compatibilité Java
```

## 🚀 Comment l'exécuter

### Prérequis

- Java 11 ou supérieur (ce projet a été testé avec Java 17)
- SBT (Simple Build Tool)

### Lancer l'application

Pour compiler et exécuter l'application, utilisez la commande suivante à la racine du projet :

```bash
sbt run
```

Le script s'exécutera, affichera les différents DataFrames dans la console et créera les fichiers Parquet dans le répertoire `output/`.