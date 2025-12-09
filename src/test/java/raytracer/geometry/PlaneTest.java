package raytracer.geometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vector;
import raytracer.trace.Intersection;
import java.util.Optional;

class PlaneTest {
    private static final double EPSILON = 1e-6;

    @Test
    void testIntersect_PerpendicularHit() {
        Plane plane = new Plane(new Point(0, 0, -5), new Vector(0, 0, 1));
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, -1));

        Optional<Intersection> optionalIntersection = plane.intersect(ray);
        assertTrue(optionalIntersection.isPresent(), "L'intersection perpendiculaire devrait être trouvée.");

        Intersection intersection = optionalIntersection.get();

        assertEquals(5.0, intersection.getT(), EPSILON);

        Point expectedP = new Point(0, 0, -5);
        assertEquals(expectedP.getX(), intersection.getPoint().getX(), EPSILON);
        assertEquals(expectedP.getY(), intersection.getPoint().getY(), EPSILON);
        assertEquals(expectedP.getZ(), intersection.getPoint().getZ(), EPSILON);
    }

    @Test
    void testIntersect_ParallelRay() {
        Plane plane = new Plane(new Point(0, 0, -5), new Vector(0, 0, 1));
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));

        assertNull(plane.intersect(ray).orElse(null), "Le rayon parallèle ne devrait pas intersecter le plan.");
    }

    @Test
    void testGetNormal() {
        Vector normalDirection = new Vector(0, 1, 0);
        Plane plane = new Plane(new Point(0, 0, 0), normalDirection);
        Point hitPoint = new Point(5, 5, 5);
        Vector normal = plane.getNormal(hitPoint);

        assertEquals(normalDirection.getX(), normal.getX(), EPSILON);
        assertEquals(normalDirection.getY(), normal.getY(), EPSILON);
        assertEquals(normalDirection.getZ(), normal.getZ(), EPSILON);
    }
}