package raytracer.math;

/**
 * Représente un Point 3D (position dans l'espace).
 * Implémente Point + Vector = Point et Point - Point = Vector.
 */
public final class Point extends AbstractVec3 {

    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Soustrait un autre point à celui-ci.
     * Résultat : Point - Point = Vector [cite: 93]
     * @param other L'autre point.
     * @return Un nouveau Vector.
     */
    public Vector subtract(Point other) {
        return new Vector(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    /**
     * Ajoute un vecteur à ce point.
     * Résultat : Point + Vector = Point [cite: 93]
     * @param vector Le vecteur à ajouter.
     * @return Un nouveau Point.
     */
    public Point add(Vector vector) {
        return new Point(this.x + vector.getX(), this.y + vector.getY(), this.z + vector.getZ());
    }
}