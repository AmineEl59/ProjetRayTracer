package raytracer.light;

import raytracer.math.Color;

/**
 * Classe abstraite de base pour toutes les sources de lumière.
 * Contient la couleur (r,g,b).
 */
public abstract class AbstractLight {
    protected final Color color;

    protected AbstractLight(Color color) {
        this.color = color;
    }

    public Color getColor() { return color; }
}