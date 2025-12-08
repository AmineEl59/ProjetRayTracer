package raytracer.math;

/**
 * Représente un vecteur 3D (direction, normale).
 * Permet toutes les opérations vectorielles.
 */
public final class Vector extends AbstractVec3 {

    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Ajoute un autre vecteur à celui-ci (Addition vectorielle).
     * Formule : (x1,y1,z1) + (x2,y2,z2) = (x1+x2, y1+y2, z1+z2) [cite: 76]
     * @param other L'autre vecteur.
     * @return Un nouveau Vector résultat de l'addition.
     */
    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    /**
     * Soustrait un autre vecteur à celui-ci (Soustraction vectorielle).
     * Formule : (x1,y1,z1) - (x2,y2,z2) = (x1-x2, y1-y2, z1-z2) [cite: 78]
     * @param other L'autre vecteur.
     * @return Un nouveau Vector résultat de la soustraction.
     */
    public Vector subtract(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    /**
     * Calcule le produit scalaire (dot product).
     * Formule : (x1,y1,z1).(x2,y2,z2) = x1*x2 + y1*y2 + z1*z2 [cite: 87]
     * @param other L'autre vecteur.
     * @return Le résultat scalaire.
     */
    public double dot(Vector other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    /**
     * Calcule le produit vectoriel (cross product).
     * Formule : (x1,y1,z1) x (x2,y2,z2) = (y1*z2-z1*y2, z1*x2-x1*z2, x1*y2-y1*x2) [cite: 88]
     * @param other L'autre vecteur.
     * @return Un nouveau Vector perpendiculaire aux deux vecteurs.
     */
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

    @Override
    public Vector divide(double scalar) {
        super.divide(scalar); // Lancer l'exception si division par zéro
        return new Vector(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    @Override
    public Vector schurProduct(AbstractVec3 other) {
        return new Vector(this.x * other.getX(), this.y * other.getY(), this.z * other.getZ());
    }

    /**
     * Calcule la norme (longueur) du vecteur au carré.
     * (Optimisation : utilisé pour éviter l'opération Math.sqrt si non nécessaire).
     * @return La norme au carré.
     */
    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    /**
     * Calcule la norme (longueur) du vecteur.
     * Formule : ||(x,y,z)|| = sqrt(x*x + y*y + z*z) [cite: 90]
     * @return La norme (longueur) du vecteur.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Normalise le vecteur pour obtenir un vecteur unitaire (de longueur 1).
     * Formule : norm(V) = (1/||V||) * V [cite: 91]
     * @return Un nouveau Vector normalisé.
     */
    public Vector normalize() {
        double len = this.length();
        if (len == 0.0) {
            return new Vector(0, 0, 0);
        }
        return this.divide(len);
    }
}