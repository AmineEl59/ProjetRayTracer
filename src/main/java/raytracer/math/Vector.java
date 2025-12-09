package raytracer.math;

import java.lang.Math;

/**
 * Représente un vecteur 3D (direction, normale).
 * Permet toutes les opérations vectorielles.
 */
public final class Vector extends AbstractVec3 {

    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    /** Ajoute un autre vecteur à celui-ci (Addition vectorielle). */
    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    /** Soustrait un autre vecteur à celui-ci (Soustraction vectorielle). */
    public Vector subtract(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    /** Calcule le produit scalaire (dot product). */
    public double dot(Vector other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    /** Calcule le produit vectoriel (cross product). */
    public Vector cross(Vector other) {
        return new Vector(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    @Override
    public Vector multiply(double scalar) {
        return new Vector(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    /** Multiplie le vecteur par un scalaire (alias de multiply). */
    public Vector scale(double scalar) {
        return this.multiply(scalar);
    }

    @Override
    public Vector divide(double scalar) {
        return new Vector(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    @Override
    public Vector schurProduct(AbstractVec3 other) {
        return new Vector(this.x * other.getX(), this.y * other.getY(), this.z * other.getZ());
    }

    /** Calcule la norme (longueur) du vecteur au carré. */
    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    /** Calcule la norme (longueur) du vecteur. */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /** Normalise le vecteur pour obtenir un vecteur unitaire (de longueur 1). */
    public Vector normalize() {
        double len = this.length();
        if (len == 0.0) {
            return new Vector(0, 0, 0);
        }
        return this.divide(len);
    }

    /**
     * Calcule le vecteur réfléchi.
     * Formule : R = D + 2 * (N . (-D)) * N
     * @param normal La normale unitaire (N) au point d'impact.
     * @return Un nouveau Vector représentant la direction réfléchie (R), normalisé.
     */
    public Vector reflect(Vector normal) {
        Vector d = this;

        double dotProduct = normal.dot(d.multiply(-1.0));

        Vector scaledNormal = normal.scale(2.0 * dotProduct);

        return d.add(scaledNormal).normalize();
    }
}