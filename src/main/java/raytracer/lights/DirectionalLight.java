package raytracer.lights;

import raytracer.math.Color;
import raytracer.math.Vector;

/**
Une source de lumière directionnelle (type "soleil").
A une couleur et une direction.
**/
public class DirectionalLight extends AbstractLight {

    private final Vector direction; // Vecteur de direction (x, y, z)

    public DirectionalLight(Vector direction, Color color) {
        super(color);
        this.direction = direction.normalize(); // On stocke le vecteur normalisé
    }

    public Vector getDirection() { return direction; }
}