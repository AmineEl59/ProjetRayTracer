package raytracer.scene;

import raytracer.math.Vector;
import raytracer.math.Point;

/**
 * Calcule le repère orthonormé (u, v, w) de la caméra.
 * u: axe horizontal (droite/gauche)
 * v: axe vertical (haut/bas)
 * w: axe de profondeur (inverse de la direction de vue)
 */
public final class Orthonormal {
    private final Vector u;
    private final Vector v;
    private final Vector w;

    private static final double EPSILON = 1e-6;

    public Orthonormal(Camera camera) {
        Point lookFrom = camera.getLookFrom();
        Point lookAt = camera.getLookAt();
        Vector up = camera.getUp();

        /**
         * 1. Calcul de W (axe de profondeur négatif, car il va de la scène vers l'oeil)
         * w = (lookFrom - lookAt).normalize(
         */
        Vector wUnnormalized = lookFrom.subtract(lookAt);
        this.w = wUnnormalized.normalize();

        /**
         * 2. Calcul de U (axe horizontal, normale au plan (up, w))
         * u = (up x w).normalize()
         */
        Vector uUnnormalized = up.cross(this.w);

        if (uUnnormalized.length() < EPSILON) {
            Vector tempUp = up.add(new Vector(0.0001, 0.0, 0.0));
            this.u = tempUp.cross(this.w).normalize();
        } else {
            this.u = uUnnormalized.normalize();
        }


        /**
         * 3. Calcul de V (axe vertical, complète le repère orthonormé)
         * v = (w x u).normalize()
         * Ceci garantit que v est perpendiculaire à w et u.
         */
        this.v = this.w.cross(this.u).normalize();
    }

    public Vector getU() { return u; }
    public Vector getV() { return v; }
    public Vector getW() { return w; }
}