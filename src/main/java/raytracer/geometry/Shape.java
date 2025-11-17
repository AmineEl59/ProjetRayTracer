package raytracer.geometry;

import raytracer.math.Color;

/**
 
Classe "mère" abstraite pour toutes les formes géométriques.
Elle contient les propriétés de matériaux communes (couleurs). [cite: 366, 367]*/
public abstract class Shape {

    protected final Color diffuse;   // Couleur de base de l'objet 
    protected final Color specular;  // Couleur du reflet "miroir" [cite: 273]

    public Shape(Color diffuse, Color specular) {
        this.diffuse = diffuse;
        this.specular = specular;
    }

    public Color getDiffuse() { return diffuse; }
    public Color getSpecular() { return specular; }
}