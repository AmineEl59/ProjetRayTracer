package raytracer.geometry;

import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.math.Ray;
import raytracer.trace.Intersection;
import java.util.Optional;

public final class Plane extends Shape {
    private static final double EPSILON = 1e-6;

    private final Point point;
    private final Vector normal;

    public Plane(Point point, Vector normal) {
        super();
        this.point = point;
        this.normal = normal.normalize();
    }

    public Point getPoint() {
        return point;
    }

    public Vector getNormal() {
        return normal;
    }

    /**
     * Implémentation de l'intersection Rayon-Plan.
     */
    @Override
    public Optional<Intersection> intersect(Ray ray) {

        double denom = ray.getDirection().dot(this.normal);

        if (Math.abs(denom) < EPSILON) {
            return Optional.empty();
        }

        Vector qMinusO = this.point.subtract(ray.getOrigin());
        double numer = qMinusO.dot(this.normal);

        double t = numer / denom;

        if (t <= EPSILON) {
            return Optional.empty();
        }

        Point p = ray.pointAt(t);
        return Optional.of(new Intersection(t, p, this));
    }

    /**
     * Calcule la normale unitaire à la surface du plan au point P (constante).
     */
    @Override
    public Vector getNormal(Point p) {
        return this.normal;
    }
}