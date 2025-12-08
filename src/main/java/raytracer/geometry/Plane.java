package raytracer.geometry;

import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.math.Ray;
import raytracer.trace.Intersection;
import java.util.Optional;

public final class Plane extends Shape {
    private final Point point;
    private final Vector normal;

    public Plane(Point point, Vector normal) {
        super();
        this.point = point;
        this.normal = normal.normalize();
    }

    public Point getPoint() { return point; }
    public Vector getNormal() { return normal; }

    /**
     * JALON 3/6: Implémentation de l'intersection Rayon-Plan.
     * Maintenue comme placeholder pour l'instant (Jalon 4 ne concerne que les sphères).
     */
    @Override
    public Optional<Intersection> intersect(Ray ray) {
        // L'implémentation viendra aux jalons suivants.
        return Optional.empty();
    }

    /**
     * JALON 4: Calcule la normale unitaire à la surface du plan au point P.
     * Pour un plan, la normale est constante partout.
     * Le point P est ignoré.
     */
    @Override
    public Vector getNormal(Point p) {
        return this.normal;
    }
}