package raytracer.trace;

import raytracer.geometry.Shape;
import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.math.Ray;

/**
 * Représente un point d'intersection entre un Ray et une Shape.
 */
public final class Intersection {
    private final double t;
    private final Point point;
    private final Shape shape;
    private static final double RAY_BIAS = 1e-4;

    public Intersection(double t, Point point, Shape shape) {
        this.t = t;
        this.point = point;
        this.shape = shape;
    }

    public double getT() {
        return t;
    }

    public Point getPoint() {
        return point;
    }

    public Shape getShape() {
        return shape;
    }

    /**
     * Récupère la normale au point d'intersection en déléguant le calcul à la forme (Shape).
     */
    public Vector getNormal() {
        return shape.getNormal(this.point);
    }

    /**
     * Génère le Rayon Réfléchi (Reflection Ray) depuis le point d'intersection.
     * Le point de départ est légèrement décalé (biased) le long du vecteur réfléchi.
     * @param incidentRay Le rayon incident (celui qui a frappé l'objet).
     * @return Le nouveau Ray réfléchi.
     */
    public Ray generateReflectedRay(Ray incidentRay) {
        Vector normal = this.getNormal();
        Vector d = incidentRay.getDirection();

        Vector reflectedDirection = d.reflect(normal);

        Point newOrigin = this.point.add(reflectedDirection.scale(RAY_BIAS));

        return new Ray(newOrigin, reflectedDirection);
    }
}