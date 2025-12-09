package raytracer.geometry;

import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.math.Ray;
import raytracer.scene.Scene;
import raytracer.trace.Intersection;
import java.util.Optional;

/**
 * Représente un triangle, implémentant l'algorithme d'intersection Möller–Trumbore (Jalon 6 Bonus).
 */
public final class Triangle extends Shape {
    private static final double EPSILON = 1e-6;

    private final int indexA, indexB, indexC;
    private Scene scene;

    public Triangle(int indexA, int indexB, int indexC) {
        super();
        this.indexA = indexA;
        this.indexB = indexB;
        this.indexC = indexC;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private Point getVertexA() {
        return scene.getVertices().get(indexA);
    }

    private Point getVertexB() {
        return scene.getVertices().get(indexB);
    }

    private Point getVertexC() {
        return scene.getVertices().get(indexC);
    }

    /**
     * Implémentation de l'intersection Rayon-Triangle (Algorithme Möller–Trumbore)
     */
    @Override
    public Optional<Intersection> intersect(Ray ray) {
        Point a = getVertexA();
        Point b = getVertexB();
        Point c = getVertexC();

        Vector edge1 = b.subtract(a);
        Vector edge2 = c.subtract(a);
        Vector p = ray.getDirection().cross(edge2);

        double det = edge1.dot(p);

        if (Math.abs(det) < EPSILON) {
            return Optional.empty();
        }

        double invDet = 1.0 / det;

        Vector t = ray.getOrigin().subtract(a);
        double beta = t.dot(p) * invDet;

        if (beta < 0 || beta > 1) {
            return Optional.empty();
        }

        Vector q = t.cross(edge1);
        double gamma = ray.getDirection().dot(q) * invDet;

        if (gamma < 0 || beta + gamma > 1) {
            return Optional.empty();
        }

        double distanceT = edge2.dot(q) * invDet;

        if (distanceT < EPSILON) {
            return Optional.empty();
        }

        Point hitPoint = ray.pointAt(distanceT);

        return Optional.of(new Intersection(distanceT, hitPoint, this));
    }

    /**
     * Calcule la normale unitaire du triangle (produit vectoriel des deux arêtes).
     */
    @Override
    public Vector getNormal(Point p) {
        Vector edge1 = getVertexB().subtract(getVertexA());
        Vector edge2 = getVertexC().subtract(getVertexA());

        Vector normal = edge1.cross(edge2);

        return normal.normalize();
    }
}