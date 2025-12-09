package raytracer.scene;

import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.math.Ray;
import java.lang.Math;

/**
 * Représente la position et l'orientation de la caméra.
 */
public final class Camera {
    private final Point lookFrom;
    private final Point lookAt;
    private final Vector up;
    private final double fov;

    private final Orthonormal basis;
    private final double halfHeight;
    private final double halfWidth;

    public Camera(Point lookFrom, Point lookAt, Vector up, double fov) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;

        this.basis = new Orthonormal(this);

        double theta = Math.toRadians(fov);
        this.halfHeight = Math.tan(theta / 2.0);
        this.halfWidth = halfHeight;
    }

    /**
     * Génère un rayon partant de la caméra et traversant le centre d'un pixel (col, row).
     */
    public Ray generateRay(int col, int row, int width, int height) {

        double u = (((double)col + 0.5) / width) * 2.0 * halfWidth - halfWidth;
        double v = (1.0 - (((double)row + 0.5) / height)) * 2.0 * halfHeight - halfHeight;

        Vector direction = basis.getU().scale(u)
                .add(basis.getV().scale(v))
                .subtract(basis.getW());

        return new Ray(this.lookFrom, direction.normalize());
    }

    public Point getLookFrom() {
        return lookFrom;
    }

    public Point getLookAt() {
        return lookAt;
    }

    public Vector getUp() {
        return up;
    }

    public double getFov() {
        return fov;
    }
}