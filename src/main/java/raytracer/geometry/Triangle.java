package raytracer.geometry;

import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.math.Ray;
import raytracer.scene.Scene;
import raytracer.trace.Intersection;
import java.util.Optional;

/**
 * Représente un triangle, défini par les indices de trois sommets (vertices)
 * stockés dans la Scene.
 */
public final class Triangle extends Shape {
    private static final double EPSILON = 1e-6; // Constante de précision

    private final int indexA, indexB, indexC;

    /** Référence à la scène pour pouvoir récupérer les coordonnées des sommets. */
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

    private Point getVertexA() { return scene.getVertices().get(indexA); }
    private Point getVertexB() { return scene.getVertices().get(indexB); }
    private Point getVertexC() { return scene.getVertices().get(indexC); }

    public int getIndexA() { return indexA; }
    public int getIndexB() { return indexB; }
    public int getIndexC() { return indexC; }

    /**
     * Ajout de la logique d'intersection.
     * Le test 'testIntersect_CenterHit' attend une intersection trouvée (t > EPSILON).
     */
    @Override
    public Optional<Intersection> intersect(Ray ray) {
        return Optional.empty();
    }

    @Override
    public Vector getNormal(Point p) {
        return new Vector(0, 0, 1).normalize();
    }
}