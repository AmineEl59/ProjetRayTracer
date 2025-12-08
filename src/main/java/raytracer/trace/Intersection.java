package raytracer.trace;

import raytracer.geometry.Shape;
import raytracer.math.Point;
import raytracer.math.Vector;

/**
 * Représente un point d'intersection entre un Ray et une Shape.
 */
public final class Intersection {
    private final double t;
    private final Point point;
    private final Shape shape;

    public Intersection(double t, Point point, Shape shape) {
        this.t = t;
        this.point = point;
        this.shape = shape;
    }

    public double getT() { return t; }
    public Point getPoint() { return point; }
    public Shape getShape() { return shape; }

    /**
     * JALON 4: Récupère la normale au point d'intersection en déléguant le calcul à la forme (Shape).
     */
    public Vector getNormal() {
        return shape.getNormal(this.point);
    }
}