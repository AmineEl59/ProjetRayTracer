package raytracer.lights;

import raytracer.math.Color;
import raytracer.math.Point;

/**
 
Une source de lumière ponctuelle (type "ampoule"). [cite: 291, 292]
A une couleur et une position.*/
public class PointLight extends AbstractLight {

    private final Point position; // Position d'origine (x, y, z) [cite: 293]

    public PointLight(Point position, Color color) {
        super(color);
        this.position = position;
    }

    public Point getPosition() { return position; }
}