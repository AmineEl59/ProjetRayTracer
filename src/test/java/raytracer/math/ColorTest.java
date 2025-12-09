package raytracer.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    private static final double DELTA = 1e-9;

    @Test
    void testDefaultConstructor() {
        Color c = new Color();
        assertEquals(0.0, c.getR(), DELTA);
        assertEquals(0.0, c.getG(), DELTA);
        assertEquals(0.0, c.getB(), DELTA);
    }

    @Test
    void testAdd() {
        Color c1 = new Color(0.2, 0.3, 0.4);
        Color c2 = new Color(0.1, 0.1, 0.1);
        Color result = c1.add(c2);

        assertEquals(0.3, result.getR(), DELTA);
        assertEquals(0.4, result.getG(), DELTA);
        assertEquals(0.5, result.getB(), DELTA);
    }

    @Test
    void testMultiplyScalar() {
        Color c = new Color(0.5, 0.25, 1.0);
        double scalar = 2.0;
        Color result = c.multiply(scalar);

        assertEquals(1.0, result.getR(), DELTA);
        assertEquals(0.5, result.getG(), DELTA);
        assertEquals(2.0, result.getB(), DELTA);
    }

    @Test
    void testSchurProduct() {
        Color c1 = new Color(0.5, 0.5, 0.5);
        Color c2 = new Color(0.2, 0.4, 0.6);
        Color result = c1.schurProduct(c2);
        assertEquals(0.1, result.getR(), DELTA);
        assertEquals(0.2, result.getG(), DELTA);
        assertEquals(0.3, result.getB(), DELTA);
    }

    @Test
    void testClamp() {
        Color c = new Color(1.5, -0.5, 0.8);
        Color result = c.clamp();
        assertEquals(1.0, result.getR(), DELTA);
        assertEquals(0.0, result.getG(), DELTA);
        assertEquals(0.8, result.getB(), DELTA);
    }

    @Test
    void testToRGB_ClampingAndScaling() {
        Color color = new Color(0.5, 1.5, -0.1);
        int r = 128;
        int g = 255;
        int b = 0;
        int expectedRGB = 0xFF000000 | (r << 16) | (g << 8) | b;
        assertEquals(expectedRGB, color.toRGB());
    }
}