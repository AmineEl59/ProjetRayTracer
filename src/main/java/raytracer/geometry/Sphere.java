package raytracer.geometry;

import raytracer.math.Color;
import raytracer.math.Point;

/**
 
Représente un objet Sphère. [cite: 301, 305]
Hérite de Shape (pour les couleurs) et ajoute un centre et un rayon.*/
public class Sphere extends Shape {

    private final Point center; // Centre (x, y, z) 
    private final double radius; // Rayon r 

    public Sphere(Point center, double radius, Color diffuse, Color specular) {
        super(diffuse, specular); // 
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() { return center; }
    public double getRadius() { return radius; }
}