package raytracer.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import raytracer.math.Color;
import raytracer.math.Point;
import raytracer.geometry.Shape;
import raytracer.light.AbstractLight;

/**
 * Conteneur de toutes les informations de la scène lues depuis le fichier de description.
 */
public class Scene {
    private int width;
    private int height;
    private Camera camera;
    private String output = "output.png";
    private Color ambient = new Color();

    private int maxDepth = 1;

    private final List<AbstractLight> lights = new ArrayList<>();
    private final List<Shape> shapes = new ArrayList<>();
    private final List<Point> vertices = new ArrayList<>();

    private int maxVerts = 0;


    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    public List<AbstractLight> getLights() {
        return Collections.unmodifiableList(lights);
    }

    public void addLight(AbstractLight light) {
        this.lights.add(light);
    }

    public List<Shape> getShapes() {
        return Collections.unmodifiableList(shapes);
    }

    public void addShape(Shape shape) {
        this.shapes.add(shape);
    }

    public List<Point> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public void addVertex(Point vertex) {
        this.vertices.add(vertex);
    }

    public int getMaxVerts() {
        return maxVerts;
    }

    public void setMaxVerts(int maxVerts) {
        this.maxVerts = maxVerts;
    }

    public Color getAmbient() {
        return ambient;
    }

    public void setAmbient(Color ambient) {
        this.ambient = ambient;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * Récupère la profondeur de récursion maximale pour le calcul de la réflexion.
     * @return La profondeur maximale (maxdepth).
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * Définit la profondeur de récursion maximale pour le calcul de la réflexion.
     * @param maxDepth La profondeur maximale, généralement entre 5 et 10[cite: 99].
     */
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }
}