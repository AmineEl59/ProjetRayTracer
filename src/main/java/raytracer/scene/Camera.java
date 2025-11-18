package raytracer.scene;

import raytracer.math.Point;
import raytracer.math.Vector;

/**
 Représente la caméra dans la scène. [cite: 369]
 Contient sa position, sa cible, son orientation et son angle de vue.
**/
public class Camera {

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

    // Getters
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