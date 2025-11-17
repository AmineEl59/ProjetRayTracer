package raytracer.scene;

import raytracer.math.Point;
import raytracer.math.Vector;

/**
 
Représente la caméra dans la scène. [cite: 369]
Contient sa position, sa cible, son orientation et son angle de vue.*/
public class Camera {

    private final Point lookFrom; // Position de l'oeil [cite: 266]
    private final Point lookAt;   // Point visé [cite: 266]
    private final Vector up;      // Direction "vers le haut" [cite: 266]
    private final double fov;     // Angle de vue (field of view) en degrés [cite: 266]

    public Camera(Point lookFrom, Point lookAt, Vector up, double fov) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
    }

    // --- Getters (pour la suite) ---
    public Point getLookFrom() { return lookFrom; }
    public Point getLookAt() { return lookAt; }
    public Vector getUp() { return up; }
    public double getFov() { return fov; }
}