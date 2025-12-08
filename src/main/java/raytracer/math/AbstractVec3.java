package raytracer.math;

/**
 * Classe de base abstraite pour tous les éléments 3D du raytracer (Vector, Point, Color).
 * Contient les composantes x, y, z en double.
 */
public abstract class AbstractVec3 {
    protected final double x;
    protected final double y;
    protected final double z;

    /**
     * Constructeur protégé pour initialiser les composantes.
     * @param x Composante X.
     * @param y Composante Y.
     * @param z Composante Z.
     */
    protected AbstractVec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // --- Accesseurs ---

    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    // --- Méthodes Utilitaires ---

    /**
     * Multiplie les composantes par un scalaire d.
     * Formule : d * (x1, y1, z1) = (d*x1, d*y1, d*z1) [cite: 86]
     * @param scalar Le facteur scalaire.
     * @return Un nouveau AbstractVec3 mis à l'échelle.
     */
    public AbstractVec3 multiply(double scalar) {
        return new Vector(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    /**
     * Divise les composantes par un scalaire.
     * @param scalar Le facteur scalaire.
     * @return Un nouveau AbstractVec3 divisé.
     */
    public AbstractVec3 divide(double scalar) {
        if (scalar == 0.0) {
            throw new IllegalArgumentException("Division par zéro impossible.");
        }
        return this.multiply(1.0 / scalar);
    }

    /**
     * Calcule le produit de Schur (multiplication composante par composante).
     * Formule : (x1, y1, z1) * (x2, y2, z2) = (x1*x2, y1*y2, z1*z2) [cite: 89]
     * @param other L'autre AbstractVec3.
     * @return Un nouveau AbstractVec3 résultat de la multiplication.
     */
    public AbstractVec3 schurProduct(AbstractVec3 other) {
        return new Vector(this.x * other.x, this.y * other.y, this.z * other.z);
    }

}