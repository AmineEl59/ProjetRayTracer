package raytracer.math;

import java.util.Objects;
import java.lang.Math;

/**
 * Représente une couleur en RGB avec des valeurs en double entre 0.0 et 1.0.
 */
public final class Color {
    private static final double EPSILON = 1e-6;

    private final double r, g, b;

    public Color() {
        this(0.0, 0.0, 0.0);
    }

    public Color(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getR() {
        return r;
    }

    public double getG() {
        return g;
    }

    public double getB() {
        return b;
    }

    /** Addition composante par composante. */
    public Color add(Color other) {
        return new Color(r + other.r, g + other.g, b + other.b);
    }

    /** Multiplication par un scalaire. */
    public Color multiply(double scalar) {
        return new Color(r * scalar, g * scalar, b * scalar);
    }

    /** Vérifie si la somme avec une autre couleur dépasse 1.0 (pour contrainte du Jalon 2). */
    public boolean checkSumExceedsOne(Color other) {
        return (this.r + other.r > 1.0) || (this.g + other.g > 1.0) || (this.b + other.b > 1.0);
    }

    /**
     * Produit de Schur (multiplication composante par composante).
     * Utilisé pour combiner la couleur spéculaire de l'objet avec la couleur réfléchie: specular * c'.
     */
    public Color schurProduct(Color other) {
        return new Color(r * other.r, g * other.g, b * other.b);
    }

    /**
     * Borne les composantes de couleur entre 0.0 et 1.0 (Clamping).
     * Essentiel si l'addition de lumière directe et indirecte dépasse l'unité.
     */
    public Color clamp() {
        double clampedR = Math.max(0.0, Math.min(1.0, r));
        double clampedG = Math.max(0.0, Math.min(1.0, g));
        double clampedB = Math.max(0.0, Math.min(1.0, b));
        return new Color(clampedR, clampedG, clampedB);
    }

    public int toRGB() {
        int red = (int) Math.round(Math.max(0, Math.min(1, r)) * 255.0);
        int green = (int) Math.round(Math.max(0, Math.min(1, g)) * 255.0);
        int blue = (int) Math.round(Math.max(0, Math.min(1, b)) * 255.0);

        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Color otherColor = (Color) other;

        return Math.abs(this.r - otherColor.r) < EPSILON &&
                Math.abs(this.g - otherColor.g) < EPSILON &&
                Math.abs(this.b - otherColor.b) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Double.doubleToLongBits(r),
                Double.doubleToLongBits(g),
                Double.doubleToLongBits(b));
    }
}