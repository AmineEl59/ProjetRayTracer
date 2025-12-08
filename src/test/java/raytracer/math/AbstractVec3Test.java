package raytracer.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour AbstractVec3.
 * Utilise une classe concrète interne pour tester la logique de la classe abstraite.
 */
class AbstractVec3Test {

    private static final double DELTA = 1e-9;

    /**
     * Classe concrète de test utilisée uniquement pour instancier et tester la logique de AbstractVec3.
     */
    private static class ConcreteVec3 extends AbstractVec3 {
        public ConcreteVec3(double x, double y, double z) {
            super(x, y, z);
        }
    }

    /** Teste la construction et les accesseurs (getters). */
    @Test
    void testCreationAndAccessors() {
        AbstractVec3 v = new ConcreteVec3(5.5, -2.0, 1.1);

        assertEquals(5.5, v.getX(), DELTA);
        assertEquals(-2.0, v.getY(), DELTA);
        assertEquals(1.1, v.getZ(), DELTA);
    }

    /** Teste la multiplication par un scalaire implémentée dans la classe de base. */
    @Test
    void testMultiplyScalar() {
        AbstractVec3 v = new ConcreteVec3(2.0, 4.0, 6.0);
        double scalar = 0.5;

        AbstractVec3 result = v.multiply(scalar);

        assertEquals(1.0, result.getX(), DELTA);
        assertEquals(2.0, result.getY(), DELTA);
        assertEquals(3.0, result.getZ(), DELTA);
    }

    /** Teste la division par un scalaire implémentée dans la classe de base. */
    @Test
    void testDivideScalar() {
        AbstractVec3 v = new ConcreteVec3(10.0, 5.0, 2.0);
        double scalar = 2.0;

        AbstractVec3 result = v.divide(scalar);

        assertEquals(5.0, result.getX(), DELTA);
        assertEquals(2.5, result.getY(), DELTA);
        assertEquals(1.0, result.getZ(), DELTA);
    }

    /** Teste la gestion de la division par zéro. */
    @Test
    void testDivideByZero() {
        AbstractVec3 v = new ConcreteVec3(1, 2, 3);

        assertThrows(IllegalArgumentException.class, () -> {
            v.divide(0.0);
        }, "La division par zéro devrait lancer une IllegalArgumentException.");
    }

    /** Teste le produit de Schur (Multiplication composante par composante). */
    @Test
    void testSchurProduct() {
        AbstractVec3 v1 = new ConcreteVec3(1.0, 2.0, 3.0);
        AbstractVec3 v2 = new ConcreteVec3(2.0, 3.0, 4.0);

        AbstractVec3 result = v1.schurProduct(v2);

        assertEquals(2.0, result.getX(), DELTA);
        assertEquals(6.0, result.getY(), DELTA);
        assertEquals(12.0, result.getZ(), DELTA);
    }
}