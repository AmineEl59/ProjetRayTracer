package raytracer.light;

import raytracer.math.Color;
import raytracer.math.Vector;

public final class DirectionalLight extends AbstractLight {
    private final Vector direction;

    public DirectionalLight(Vector direction, Color color) {
        super(color);
        this.direction = direction.normalize();
    }

    public Vector getDirection() { return direction; }
}