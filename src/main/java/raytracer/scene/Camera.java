package raytracer.scene;

import raytracer.math.Point;
import raytracer.math.Vector;

/**
 * Représente la position et l'orientation de la caméra.
 */
public final class Camera {
    private final Point lookFrom;
    private final Point lookAt;
    private final Vector up;
    private final double fov;

    public Camera(Point lookFrom, Point lookAt, Vector up, double fov) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
    }

    public Point getLookFrom() { return lookFrom; }
    public Point getLookAt() { return lookAt; }
    public Vector getUp() { return up; }
    public double getFov() { return fov; }
}