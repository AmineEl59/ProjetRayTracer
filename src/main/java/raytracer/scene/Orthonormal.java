package raytracer.scene;

import raytracer.math.Vector;
import raytracer.math.Point;

/**
 * Calcule le repère orthonormé (u, v, w) de la caméra.
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

        this.w = lookFrom.subtract(lookAt).normalize();

        Vector uUnnormalized = up.cross(this.w);

        if (uUnnormalized.length() < EPSILON) {
            Vector tempUp = up.add(new Vector(0.0001, 0.0, 0.0));
            this.u = tempUp.cross(this.w).normalize();
        } else {
            this.u = uUnnormalized.normalize();
        }

        this.v = this.w.cross(this.u).normalize();
    }

    public Vector getU() {
        return u;
    }

    public Vector getV() {
        return v;
    }

    public Vector getW() {
        return w;
    }
}