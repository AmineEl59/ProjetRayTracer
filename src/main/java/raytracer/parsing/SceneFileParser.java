package raytracer.parsing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.math.Color;
import raytracer.geometry.Shape;
import raytracer.geometry.Sphere;
import raytracer.geometry.Plane;
import raytracer.geometry.Triangle;
import raytracer.scene.Scene;
import raytracer.scene.Camera;
import raytracer.light.AbstractLight;
import raytracer.light.PointLight;
import raytracer.light.DirectionalLight;

/**
 * Lit et interprète le fichier de description de scène.
 * Gère les vérifications de contraintes (couleurs, indices).
 */
public class SceneFileParser {

    private Color lastDiffuse = new Color(0.9, 0.9, 0.9);
    private Color lastSpecular = new Color();
    private double lastShininess = 10.0;

    /**
     * Lit le fichier et retourne un objet Scene peuplé.
     */
    public Scene parse(String filePath) throws IOException, SceneParseException {
        Scene scene = new Scene();
        Path path = Path.of(filePath);

        try {
            Files.lines(path)
                    .forEach(line -> {
                        try {
                            processLine(line, scene);
                        } catch (SceneParseException e) {
                            throw new RuntimeException("Erreur de Parsing: " + e.getMessage(), e);
                        }
                    });
        } catch (RuntimeException e) {
            if (e.getCause() instanceof SceneParseException) {
                throw (SceneParseException) e.getCause();
            }
            throw e;
        }

        checkLightSumConstraint(scene);

        return scene;
    }

    private void processLine(String line, Scene scene) throws SceneParseException {
        line = line.trim();

        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }

        String[] tokens = line.split("\\s+");
        String command = tokens[0];

        try {
            switch (command) {
                case "size":
                    scene.setWidth(Integer.parseInt(tokens[1]));
                    scene.setHeight(Integer.parseInt(tokens[2]));
                    break;
                case "output":
                    scene.setOutput(tokens[1]);
                    break;
                case "camera":
                    Point lookFrom = parsePoint(tokens, 1);
                    Point lookAt = parsePoint(tokens, 4);
                    Vector up = parseVector(tokens, 7);
                    double fov = Double.parseDouble(tokens[10]);
                    scene.setCamera(new Camera(lookFrom, lookAt, up, fov));
                    break;
                case "ambient":
                    Color ambient = parseColor(tokens, 1);
                    if (ambient.checkSumExceedsOne(lastDiffuse)) {
                        throw new SceneParseException("CONTRAINTE DE COULEUR: ambient + diffuse dépasse 1.0. Ligne: " + line);
                    }
                    scene.setAmbient(ambient);
                    break;
                case "diffuse":
                    lastDiffuse = parseColor(tokens, 1);
                    if (scene.getAmbient().checkSumExceedsOne(lastDiffuse)) {
                        throw new SceneParseException("CONTRAINTE DE COULEUR: ambient + diffuse dépasse 1.0. Ligne: " + line);
                    }
                    break;
                case "specular":
                    lastSpecular = parseColor(tokens, 1);
                    break;
                case "shininess":
                    lastShininess = Double.parseDouble(tokens[1]);
                    break;
                case "directional":
                    Vector direction = parseVector(tokens, 1);
                    Color dirColor = parseColor(tokens, 4);
                    scene.addLight(new DirectionalLight(direction, dirColor));
                    break;
                case "point":
                    Point pos = parsePoint(tokens, 1);
                    Color pColor = parseColor(tokens, 4);
                    scene.addLight(new PointLight(pos, pColor));
                    break;
                case "maxverts":
                    scene.setMaxVerts(Integer.parseInt(tokens[1]));
                    break;
                case "vertex":
                    scene.addVertex(parsePoint(tokens, 1));
                    break;
                case "sphere":
                    Point center = parsePoint(tokens, 1);
                    double radius = Double.parseDouble(tokens[4]);
                    Sphere sphere = new Sphere(center, radius);
                    applyLastColors(sphere);
                    scene.addShape(sphere);
                    break;
                case "plane":
                    Point p = parsePoint(tokens, 1);
                    Vector n = parseVector(tokens, 4);
                    Plane plane = new Plane(p, n);
                    applyLastColors(plane);
                    scene.addShape(plane);
                    break;
                case "tri":
                    int a = Integer.parseInt(tokens[1]);
                    int b = Integer.parseInt(tokens[2]);
                    int c = Integer.parseInt(tokens[3]);

                    if (a < 0 || b < 0 || c < 0 || a >= scene.getMaxVerts() || b >= scene.getMaxVerts() || c >= scene.getMaxVerts()) {
                        throw new SceneParseException("CONTRAINTE DE VERTEX: Indices invalides par rapport à maxverts (" + scene.getMaxVerts() + "). Ligne: " + line);
                    }

                    Triangle tri = new Triangle(a, b, c);
                    applyLastColors(tri);
                    scene.addShape(tri);
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException e) {
            throw new SceneParseException("Erreur de format de nombre: La ligne contient un argument non valide. Ligne: " + line);
        }
    }

    private Point parsePoint(String[] tokens, int offset) {
        return new Point(Double.parseDouble(tokens[offset]), Double.parseDouble(tokens[offset + 1]), Double.parseDouble(tokens[offset + 2]));
    }

    private Vector parseVector(String[] tokens, int offset) {
        return new Vector(Double.parseDouble(tokens[offset]), Double.parseDouble(tokens[offset + 1]), Double.parseDouble(tokens[offset + 2]));
    }

    private Color parseColor(String[] tokens, int offset) {
        return new Color(Double.parseDouble(tokens[offset]), Double.parseDouble(tokens[offset + 1]), Double.parseDouble(tokens[offset + 2]));
    }

    private void applyLastColors(Shape shape) {
        shape.setDiffuse(lastDiffuse);
        shape.setSpecular(lastSpecular);
        shape.setShininess(lastShininess);
    }

    private void checkLightSumConstraint(Scene scene) throws SceneParseException {
        Color totalLightColor = new Color();
        for (AbstractLight light : scene.getLights()) {
            totalLightColor = totalLightColor.add(light.getColor());
        }

        if (totalLightColor.getR() > 1.0 || totalLightColor.getG() > 1.0 || totalLightColor.getB() > 1.0) {
            throw new SceneParseException(String.format("CONTRAINTE DE LUMIÈRE: La somme des couleurs des lumières (%.2f, %.2f, %.2f) dépasse 1.0.",
                    totalLightColor.getR(), totalLightColor.getG(), totalLightColor.getB()));
        }
    }
}