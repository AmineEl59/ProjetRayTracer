package raytracer.light;

import raytracer.math.Color;
import raytracer.math.Point;

public final class PointLight extends AbstractLight {
    private final Point position;

    public PointLight(Point position, Color color) {
        super(color);
        this.position = position;
    }

    public Point getPosition() { return position; }
}