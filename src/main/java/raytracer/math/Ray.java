package raytracer.math;

/**
 * Représente un rayon, défini par son point d'origine et son vecteur de direction.
 */
public final class Ray {
    private final Point origin;
    private final Vector direction;

    public Ray(Point origin, Vector direction) {
        this.origin = origin;
        this.direction = direction.normalize();
    }

    public Point getOrigin() {
        return origin;
    }

    public Vector getDirection() {
        return direction;
    }

    /**
     * Calcule le point sur le rayon à la distance t.
     * Formule: p = o + d * t
     * @param t La distance scalaire le long du rayon.
     * @return Le Point correspondant à cette distance.
     */
    public Point getPointAt(double t) {
        return origin.add(direction.multiply(t));
    }
}