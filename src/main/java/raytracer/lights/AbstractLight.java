package raytracer.lights;

import raytracer.math.Color;

/**
 
Classe "mère" abstraite pour toutes les lumières.
Contient la couleur, commune à toutes les lumières.*/
public abstract class AbstractLight {

    protected final Color color; // Couleur (r, g, b) de la lumière [cite: 289, 294]

    public AbstractLight(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}