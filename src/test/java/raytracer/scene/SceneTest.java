package raytracer.scene;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import raytracer.scene.Scene;
import raytracer.math.Color;
import raytracer.light.PointLight;
import raytracer.geometry.Sphere;
import raytracer.math.Point;

public class SceneTest {
    private static final double EPSILON = 1e-6;

    @Test
    void testInitialSetup() {
        Scene scene = new Scene();
        assertEquals(0, scene.getWidth());
        assertEquals(0, scene.getHeight());
        assertEquals(new Color(0, 0, 0), scene.getAmbient());
    }

    @Test
    void testAddShapeAndGetShapes() {
        Scene scene = new Scene();
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1.0);
        scene.addShape(sphere);
        assertEquals(1, scene.getShapes().size());
        assertTrue(scene.getShapes().contains(sphere));
    }

    @Test
    void testAddLight() {
        Scene scene = new Scene();
        PointLight light = new PointLight(new Point(10, 10, 10), new Color(0.5, 0.5, 0.5));
        scene.addLight(light);
        assertEquals(1, scene.getLights().size());
    }
}