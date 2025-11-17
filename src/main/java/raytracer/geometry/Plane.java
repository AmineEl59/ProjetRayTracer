package raytracer.geometry;

import raytracer.math.Color;
import raytracer.math.Point;
import raytracer.math.Vector;

/**
 
Représente un objet Plan (infini). [cite: 303, 331]
Défini par un point et un vecteur normal.*/
public class Plane extends Shape {

    private final Point point;    // Un point (x, y, z) sur le plan 
    private final Vector normal;  // Le vecteur normal (u, v, w) au plan 

    public Plane(Point point, Vector normal, Color diffuse, Color specular) {
        super(diffuse, specular); // 
        this.point = point;
        this.normal = normal;
    }

    public Point getPoint() { return point; }
    public Vector getNormal() { return normal; }
}