package raytracer.geometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vector;
import raytracer.trace.Intersection;

class SphereTest {
    private static final double EPSILON = 1e-6;

    @Test
    void testIntersect_HitInFront() {
        Sphere sphere = new Sphere(new Point(0, 0, -5), 1.0);
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, -1));

        Intersection intersection = sphere.intersect(ray).orElse(null);

        assertNotNull(intersection);
        assertEquals(4.0, intersection.getT(), EPSILON);
    }

    @Test
    void testIntersect_Miss() {
        Sphere sphere = new Sphere(new Point(0, 0, -5), 1.0);
        Ray ray = new Ray(new Point(10, 10, 0), new Vector(0, 0, -1));

        assertNull(sphere.intersect(ray).orElse(null));
    }

    @Test
    void testGetNormal() {
        Sphere sphere = new Sphere(new Point(0, 0, -5), 1.0);
        Point hitPoint = new Point(0.0, 0.0, -4.0);

        Vector normal = sphere.getNormal(hitPoint);
        Vector expectedNormal = new Vector(0, 0, 1);

        assertEquals(expectedNormal.getX(), normal.getX(), EPSILON);
        assertEquals(expectedNormal.getY(), normal.getY(), EPSILON);
        assertEquals(expectedNormal.getZ(), normal.getZ(), EPSILON);
    }
}