package raytracer.scene;

import java.util.ArrayList;
import java.util.List;
import raytracer.geometry.Shape;
import raytracer.lights.AbstractLight;
import raytracer.math.Color;

/**
 
Le conteneur principal qui contient tous les éléments de la scène
lus depuis le fichier de description. */
public class Scene {

    // Réglages de l'image
    private int width = 640;
    private int height = 480;
    private String outputName = "output.png"; // [cite: 261]

    // Éléments de la scène
    private Camera camera;
    private Color ambientColor = new Color(0, 0, 0); // [cite: 362]
    private final List<AbstractLight> lights;
    private final List<Shape> shapes;

    public Scene() {
        this.lights = new ArrayList<>(); // [cite: 363]
        this.shapes = new ArrayList<>(); // [cite: 364]
    }

    // --- Méthodes utilisées par le Parser ---

    public void setDimensions(int width, int height) {
        this.width = width; // [cite: 358]
        this.height = height; // [cite: 359]
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName; // [cite: 361]
    }

    public void setCamera(Camera camera) {
        this.camera = camera; // [cite: 360]
    }

    public void setAmbientColor(Color ambientColor) {
        this.ambientColor = ambientColor; // [cite: 362]
    }

    public void addLight(AbstractLight light) {
        this.lights.add(light);
    }

    public void addShape(Shape shape) {
        this.shapes.add(shape);
    }

    //  Getters

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public String getOutputName() { return outputName; }
    public Camera getCamera() { return camera; }
    public Color getAmbientColor() { return ambientColor; }
    public List<AbstractLight> getLights() { return lights; }
    public List<Shape> getShapes() { return shapes; }
}