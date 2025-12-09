# Projet RayTracer
## 🐲 Moteur de Rendu par RayTracer en Java

Ce projet est une implémentation complète d'un Ray Tracer en Java, capable de simuler l'illumination, les ombres, les réflexions et les propriétés de matériaux pour des scènes 3D complexes.

---

### 🚀 Démarrage Rapide

Pour compiler le projet, exécuter les tests et lancer le rendu de la scène par défaut (`final.scene`), utilisez la commande suivante :

Si vous voulez tester la scène bonus, il faut remplacer le contenu du fichier final.scene

```bash
mvn clean install && java -jar target/raytracer.jar
```
Le fichier .jar se trouve dans le dossier target après avoir compilé le code, et les fichiers .png se trouvent à la racine.

---

# 📜 Explications des Jalons

Jalon 1 : Mise en place des fondations mathématiques (vecteurs, points, couleurs) et des structures de base de la scène.<br><br>
Jalon 2 : Implémentation du parsing de scène, vérification des contraintes de couleur, et première géométrie : l'intersection Rayon-Sphère.<br><br>
Jalon 3 : Définition du système de caméra ($\vec{u}, \vec{v}, \vec{w}$) et mise en place du lancer de rayon primaire à travers les pixels de l'écran.<br><br>
Jalon 4 : Calcul de la normale, ajout de la lumière ambiante et implémentation de l'illumination Diffuse (modèle de Lambert).<br><br>
Jalon 5 : Implémentation des ombres portées, ajout de l'illumination Spéculaire (modèle de Blinn-Phong) et gestion de la brillance.<br><br>
Jalon 6 (Bonus) : Ajout de la réflexion récursive (effet miroir).

---

# Rendu du final.scene et final_bonus.scene

![sphere](/final.png)
<br>

![Dragon rouge](/dragon3.png)