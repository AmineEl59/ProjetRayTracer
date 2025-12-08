package raytracer.scene;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import raytracer.scene.Camera;
import raytracer.math.Point;
import raytracer.math.Vector;

class CameraTest {
    private static final double EPSILON = 1e-6;

    /**
     * Teste l'instanciation de la caméra et vérifie les valeurs via les accesseurs.
     */
    @Test
    void testCameraInstantiation() {
        Point lookFrom = new Point(1, 2, 3);
        Point lookAt = new Point(4, 5, 6);
        Vector up = new Vector(0, 1, 0);
        double fov = 60.0;

        Camera camera = new Camera(lookFrom, lookAt, up, fov);

        assertEquals(lookFrom, camera.getLookFrom());
        assertEquals(lookAt, camera.getLookAt());
        assertEquals(up, camera.getUp());
        assertEquals(fov, camera.getFov(), EPSILON);
    }
}