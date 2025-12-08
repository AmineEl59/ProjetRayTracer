package raytracer.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Point, validant les opérations Point-Point=Vector et Point+Vector=Point.
 */
class PointTest {

    private static final double DELTA = 1e-9;

    /** Teste la soustraction : Point - Point = Vector */
    @Test
    void testSubtractPoint() {
        Point p1 = new Point(10.0, 5.0, 0.0);
        Point p2 = new Point(4.0, 3.0, 1.0);

        // p1 - p2 = (10-4, 5-3, 0-1) = (6, 2, -1)
        Vector v = p1.subtract(p2);

        // Vérifie le type de retour
        assertTrue(v instanceof Vector, "Le résultat de Point - Point doit être un Vector.");

        // Vérifie les composantes
        assertEquals(6.0, v.getX(), DELTA);
        assertEquals(2.0, v.getY(), DELTA);
        assertEquals(-1.0, v.getZ(), DELTA);
    }

    /** Teste l'addition : Point + Vector = Point */
    @Test
    void testAddVector() {
        Point p = new Point(1.0, 2.0, 3.0);
        Vector v = new Vector(5.0, -2.0, 1.0);

        // p + v = (1+5, 2-2, 3+1) = (6, 0, 4)
        Point result = p.add(v);

        // Vérifie le type de retour
        assertTrue(result instanceof Point, "Le résultat de Point + Vector doit être un Point.");

        // Vérifie les composantes
        assertEquals(6.0, result.getX(), DELTA);
        assertEquals(0.0, result.getY(), DELTA);
        assertEquals(4.0, result.getZ(), DELTA);
    }
}