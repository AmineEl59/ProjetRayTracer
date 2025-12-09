package raytracer.geometry;

import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vector;
import raytracer.trace.Intersection;
import java.util.Optional;
import java.lang.Math;

public final class Sphere extends Shape {
    private final Point center;
    private final double radius;

    public Sphere(Point center, double radius) {
        super();
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        Point o = ray.getOrigin();
        Vector d = ray.getDirection();
        Point c = this.center;
        double r = this.radius;

        Vector oc = o.subtract(c);

        double a = d.dot(d);
        double b = 2.0 * oc.dot(d);
        double C = oc.dot(oc) - r * r;

        double delta = b * b - 4.0 * a * C;

        if (delta < 0) {
            return Optional.empty();
        }

        double sqrtDelta = Math.sqrt(delta);

        double t1 = (-b - sqrtDelta) / (2.0 * a);
        double t2 = (-b + sqrtDelta) / (2.0 * a);

        double tClosest = Double.MAX_VALUE;

        if (t1 > 0) {
            tClosest = t1;
        }

        if (t2 > 0 && t2 < tClosest) {
            tClosest = t2;
        }

        if (tClosest == Double.MAX_VALUE) {
            return Optional.empty();
        }

        Point p = ray.pointAt(tClosest);
        return Optional.of(new Intersection(tClosest, p, this));
    }

    /**
     * JALON 4: Calcule la normale unitaire pour une sphère au point P.
     * Formule: n = (P - Centre) / ||P - Centre||
     */
    @Override
    public Vector getNormal(Point p) {
        Vector normalUnnormalized = p.subtract(this.center);
        return normalUnnormalized.normalize();
    }
}