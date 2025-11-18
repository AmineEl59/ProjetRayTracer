package raytracer.scene;

import java.util.ArrayList;
import java.util.List;
import raytracer.geometry.Shape;
import raytracer.lights.AbstractLight;
import raytracer.math.Color;

/**
 
Le conteneur principal qui contient tous les éléments de la scène
lus depuis le fichier de description. 

*/
public class Scene {

    private int width = 640;
    private int height = 480;
    private String outputName = "output.png";

    private Camera camera;
    private Color ambientColor = new Color(0, 0, 0);
    private final List<AbstractLight> lights;
    private final List<Shape> shapes;

    public Scene() {
        this.lights = new ArrayList<>();
        this.shapes = new ArrayList<>();
    }

    //  Méthodes utilisées par le Parser 

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setAmbientColor(Color ambientColor) {
        this.ambientColor = ambientColor;
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