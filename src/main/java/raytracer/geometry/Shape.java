package raytracer.geometry;

import raytracer.math.Color;
import raytracer.math.Ray;
import raytracer.math.Vector;
import raytracer.math.Point;
import raytracer.trace.Intersection;
import java.util.Optional;

/**
 * Classe abstraite de base pour toutes les formes géométriques de la scène.
 */
public abstract class Shape {

    private Color diffuse = new Color(0.9, 0.9, 0.9);
    private Color specular = new Color();
    private double shininess = 10.0;

    public Shape() {
    }

    public Color getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Color diffuse) {
        this.diffuse = diffuse;
    }

    public Color getSpecular() {
        return specular;
    }

    public void setSpecular(Color specular) {
        this.specular = specular;
    }

    public double getShininess() {
        return shininess;
    }

    public void setShininess(double shininess) {
        this.shininess = shininess;
    }

    public abstract Optional<Intersection> intersect(Ray ray);

    public abstract Vector getNormal(Point p);
}