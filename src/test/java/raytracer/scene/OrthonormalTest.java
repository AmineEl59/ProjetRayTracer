package raytracer.scene;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import raytracer.math.Vector;
import raytracer.math.Point;
// La classe Orthonormal est définie dans le package raytracer.scene dans votre code source
import raytracer.scene.Orthonormal;

class OrthonormalTest {
    private static final double EPSILON = 1e-6;

    /** Méthode utilitaire pour construire la base à partir des paramètres de caméra. */
    private Orthonormal createBasis(Point lookFrom, Point lookAt, Vector up) {
        Camera camera = new Camera(lookFrom, lookAt, up, 90.0);
        return new Orthonormal(camera);
    }

    @Test
    void testBasisCreation_ZAxis() {
        Point lookFrom = new Point(0, 0, 0);
        Point lookAt = new Point(0, 0, -1);
        Vector up = new Vector(0, 1, 0);

        Orthonormal basis = createBasis(lookFrom, lookAt, up);

        assertEquals(0.0, basis.getW().getX(), EPSILON);
        assertEquals(0.0, basis.getW().getY(), EPSILON);
        assertEquals(1.0, basis.getW().getZ(), EPSILON);

        assertEquals(1.0, basis.getU().getX(), EPSILON);
        assertEquals(0.0, basis.getU().getY(), EPSILON);
        assertEquals(0.0, basis.getU().getZ(), EPSILON);

        assertEquals(0.0, basis.getV().getX(), EPSILON);
        assertEquals(1.0, basis.getV().getY(), EPSILON);
        assertEquals(0.0, basis.getV().getZ(), EPSILON);
    }

    @Test
    void testBasisCreation_ArbitraryVector() {
        Point lookFrom = new Point(10, 0, 0);
        Point lookAt = new Point(0, 0, 0);
        Vector up = new Vector(0, 1, 0);

        Orthonormal basis = createBasis(lookFrom, lookAt, up);

        assertEquals(1.0, basis.getW().getX(), EPSILON);

        assertEquals(0.0, basis.getU().dot(basis.getW()), EPSILON);
        assertEquals(0.0, basis.getV().dot(basis.getW()), EPSILON);
        assertEquals(0.0, basis.getU().dot(basis.getV()), EPSILON);

        assertEquals(1.0, basis.getU().length(), EPSILON);
        assertEquals(1.0, basis.getV().length(), EPSILON);
        assertEquals(1.0, basis.getW().length(), EPSILON);
    }
}