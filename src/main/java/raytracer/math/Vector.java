package raytracer.math;

public class Vector extends AbstractVec3 {

    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Addition de deux vecteurs.
     */
    public Vector add(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    /**
     * Soustraction de deux vecteurs.
     */
    public Vector subtract(Vector v) {
        return new Vector(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    /**
     * Multiplication par un scalaire
     */
    public Vector multiply(double scalar) {
        return new Vector(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    /**
     * Produit scalaire (Dot product). Renvoie un nombre, pas un vecteur.
     */
    public double dot(Vector v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    /**
     * Produit vectoriel.
     */
    public Vector cross(Vector v) {
        return new Vector(
            this.y * v.z - this.z * v.y,
            this.z * v.x - this.x * v.z,
            this.x * v.y - this.y * v.x
        );
    }
    
    /**
     Produit de Schur (multiplication composante par composante).
    **/
    public Vector schur(Vector v) {
        return new Vector(this.x * v.x, this.y * v.y, this.z * v.z);
    }

    /**
     Calcule la longueur (magnitude) du vecteur.
    **/
    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
      Normalise le vecteur (le rend de longueur 1).
    **/
    public Vector normalize() {
        double len = this.length();
        if (len == 0) {
            // Évite une division par zéro si le vecteur est (0,0,0)
            return new Vector(0, 0, 0); 
        }
        double invLength = 1.0 / len;
        return new Vector(this.x * invLength, this.y * invLength, this.z * invLength);
    }
}