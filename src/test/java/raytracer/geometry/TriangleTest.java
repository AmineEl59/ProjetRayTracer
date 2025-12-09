package raytracer.geometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vector;
import raytracer.trace.Intersection;
import raytracer.scene.Scene;
import java.util.Optional;

class TriangleTest {
    private static final double EPSILON = 1e-6;

    private Scene setupScene() {
        Scene scene = new Scene();
        scene.addVertex(new Point(0, 1, -2));  // Index 0: a (Top)
        scene.addVertex(new Point(1, -1, -2)); // Index 1: b (Bottom Right)
        scene.addVertex(new Point(-1, -1, -2)); // Index 2: c (Bottom Left)
        return scene;
    }

    @Test
    void testIntersect_CenterHit() {
        Scene scene = setupScene();
        Triangle triangle = new Triangle(0, 1, 2);
        triangle.setScene(scene);

        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, -1));

        Optional<Intersection> optionalIntersection = triangle.intersect(ray);
        assertTrue(optionalIntersection.isPresent(), "L'intersection au centre du triangle devrait être trouvée.");

        Intersection intersection = optionalIntersection.get();

        assertEquals(2.0, intersection.getT(), EPSILON);

        Point expectedP = new Point(0, 0, -2);
        assertEquals(expectedP.getX(), intersection.getPoint().getX(), EPSILON);
        assertEquals(expectedP.getY(), intersection.getPoint().getY(), EPSILON);
        assertEquals(expectedP.getZ(), intersection.getPoint().getZ(), EPSILON);
    }

    @Test
    void testIntersect_MissOutside() {
        Scene scene = setupScene();
        Triangle triangle = new Triangle(0, 1, 2);
        triangle.setScene(scene);

        Ray ray = new Ray(new Point(2, 2, 0), new Vector(0, 0, -1));

        assertNull(triangle.intersect(ray).orElse(null), "Le rayon tiré en dehors des limites du triangle ne devrait pas intersecter.");
    }

    @Test
    void testGetNormal() {
        Scene scene = setupScene();
        Triangle triangle = new Triangle(0, 1, 2);
        triangle.setScene(scene);

        Point hitPoint = new Point(0, 0, -2); // Point sur le triangle

        Vector normal = triangle.getNormal(hitPoint);
        Vector expectedNormal1 = new Vector(0, 0, 1);
        Vector expectedNormal2 = new Vector(0, 0, -1);

        boolean matchesNormal1 = Math.abs(normal.getX() - expectedNormal1.getX()) < EPSILON &&
                Math.abs(normal.getY() - expectedNormal1.getY()) < EPSILON &&
                Math.abs(normal.getZ() - expectedNormal1.getZ()) < EPSILON;

        boolean matchesNormal2 = Math.abs(normal.getX() - expectedNormal2.getX()) < EPSILON &&
                Math.abs(normal.getY() - expectedNormal2.getY()) < EPSILON &&
                Math.abs(normal.getZ() - expectedNormal2.getZ()) < EPSILON;

        assertTrue(matchesNormal1 || matchesNormal2, "La normale doit être unitaire et perpendiculaire au plan du triangle.");
    }
}