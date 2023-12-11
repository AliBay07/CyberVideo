# CyberVideo Project

Participants:
AZZOUZI Nizar
BAYDOUN Ali
CHOUITER Skander
GUEGAN Nicolas
LI Jiawei
PELLEGRIN Noémie

## Project Structure:

.gitignore: Fichier pour la gestion des exclusions dans Git.
README.md: Document principal de présentation et d'explication du projet.
jars: Répertoire pour les bibliothèques Java externes (fichiers JAR).
bd_files: Contient le fichier sql de notre base de donnée ainsi que les triggers.

Source Code (src):

beans: Contient des classes Java Bean pour modéliser des entités comme Account, Film, etc.
coo: Répertoire pour la logique métier spécifique au projet.
dao (Data Access Object): Gère l'interaction avec la base de données.
classes: Classes pour les opérations de base de données.
tests: Tests unitaires pour les DAO.
tools: Outils pour la manipulation des données.

facade: Fournit des interfaces simplifiées pour les opérations complexes.
bd: Façade pour les interactions avec la base de données.
ui: Façade pour l'interface utilisateur.

machine: Contient Machine.java, un composant central du projet.

ui (User Interface): Classes et éléments pour l'interface utilisateur.
Images: Ressources graphiques pour l'UI.

## Running the Program:

Pour compiler et exécuter le programme, suivez ces étapes :

Assurez-vous d'avoir Java installé sur votre système.
Ouvrez un terminal ou une invite de commande.
Accédez au répertoire racine du projet.
Exécutez le programme compilé avec :
 java SysAL2000.java

Cela compilera le programme et exécutera le fichier SysAL2000.java, permettant ainsi au système CyberVideo de fonctionner.
