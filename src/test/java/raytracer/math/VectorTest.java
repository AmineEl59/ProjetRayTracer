package raytracer.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    private static final double DELTA = 1e-9; // Précision pour les comparaisons en double

    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        Vector result = v1.add(v2);

        assertEquals(5.0, result.getX(), DELTA);
        assertEquals(7.0, result.getY(), DELTA);
        assertEquals(9.0, result.getZ(), DELTA);
    }

    @Test
    void testSubtract() {
        Vector v1 = new Vector(5, 7, 9);
        Vector v2 = new Vector(4, 5, 6);
        Vector result = v1.subtract(v2);

        assertEquals(1.0, result.getX(), DELTA);
        assertEquals(2.0, result.getY(), DELTA);
        assertEquals(3.0, result.getZ(), DELTA);
    }

    @Test
    void testMultiplyScalar() {
        Vector v = new Vector(2.5, -1.0, 4.0);
        double scalar = 2.0;
        Vector result = v.multiply(scalar);

        assertEquals(5.0, result.getX(), DELTA);
        assertEquals(-2.0, result.getY(), DELTA);
        assertEquals(8.0, result.getZ(), DELTA);
    }

    @Test
    void testSchurProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 3, 4);
        Vector result = v1.schurProduct(v2);

        assertEquals(2.0, result.getX(), DELTA);
        assertEquals(6.0, result.getY(), DELTA);
        assertEquals(12.0, result.getZ(), DELTA);
    }

    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        // (1*4) + (2*5) + (3*6) = 32
        assertEquals(32.0, v1.dot(v2), DELTA);
    }

    @Test
    void testCrossProduct() {
        Vector i = new Vector(1, 0, 0);
        Vector j = new Vector(0, 1, 0);
        Vector k = new Vector(0, 0, 1);

        Vector result = i.cross(j);
        assertEquals(k.getX(), result.getX(), DELTA);
        assertEquals(k.getY(), result.getY(), DELTA);
        assertEquals(k.getZ(), result.getZ(), DELTA);
    }

    @Test
    void testLengthAndNormalize() {
        Vector v = new Vector(3, 0, 4); // Longueur 5
        assertEquals(25.0, v.lengthSquared(), DELTA);
        assertEquals(5.0, v.length(), DELTA);

        Vector normalized = v.normalize();

        assertEquals(1.0, normalized.length(), DELTA);
        assertEquals(0.6, normalized.getX(), DELTA); // 3/5
        assertEquals(0.0, normalized.getY(), DELTA); // 0/5
        assertEquals(0.8, normalized.getZ(), DELTA); // 4/5
    }
}