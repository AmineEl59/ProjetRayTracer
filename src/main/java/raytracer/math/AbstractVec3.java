package math;

/**
 * Classe de base abstraite pour les éléments à 3 composantes (Vector, Point, Color).
 * Contient les composantes x, y, z (qui peuvent être r, g, b pour les couleurs)
 * et la logique de comparaison pour les 'double'. 
 */
public abstract class AbstractVec3 {

    // Epsilon pour la comparaison des 'double' [cite: 127, 128]
    public static final double EPSILON = 0.00001; 

    protected final double x, y, z;

    public AbstractVec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    /**
     * Compare deux 'double' avec une petite marge d'erreur (EPSILON).
     */
    protected boolean essentiallyEquals(double a, double b) {
        return Math.abs(a - b) <= EPSILON; // [cite: 128]
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        AbstractVec3 other = (AbstractVec3) obj;
        
        return essentiallyEquals(this.x, other.x) &&
               essentiallyEquals(this.y, other.y) &&
               essentiallyEquals(this.z, other.z);
    }

    @Override
    public String toString() {
        // Méthode pratique pour déboguer et voir ce qu'il y a dedans
        return getClass().getSimpleName() + " [x=" + x + ", y=" + y + ", z=" + z + "]";
    }
}