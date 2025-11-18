package raytracer.geometry;

import raytracer.math.Color;
import raytracer.math.Point;

/**
Représente un objet Triangle.
Défini par 3 points (sommets).
**/
public class Triangle extends Shape {

    private final Point p1; // Sommet a
    private final Point p2; // Sommet b 
    private final Point p3; // Sommet c

    public Triangle(Point p1, Point p2, Point p3, Color diffuse, Color specular) {
        super(diffuse, specular); // 
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Point getP1() { return p1; }
    public Point getP2() { return p2; }
    public Point getP3() { return p3; }
}