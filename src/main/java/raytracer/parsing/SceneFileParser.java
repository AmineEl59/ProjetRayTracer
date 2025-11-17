// Mettre ce fichier dans: src/main/java/raytracer/parsing/SceneFileParser.java
package raytracer.parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import raytracer.geometry.*;
import raytracer.lights.*;
import raytracer.math.*;
import raytracer.scene.Camera;
import raytracer.scene.Scene;

/**
 * Le "Parser" : lit un fichier de description de scène (.txt)
 * et construit un objet Scene avec tous les éléments. 
 */
public class SceneFileParser {

    private Scene scene;
    private int lineNumber = 0;

    // États courants (gardés en mémoire pendant la lecture)
    private Color currentDiffuse = new Color(0, 0, 0);
    private Color currentSpecular = new Color(0, 0, 0);
    private Color totalLightColor = new Color(0, 0, 0);
    private List<Point> vertices = new ArrayList<>();
    private int maxVerts = 0;

    // Drapeaux pour valider les commandes obligatoires
    private boolean sizeSet = false;
    private boolean cameraSet = false;

    /**
     * Méthode principale : lit le fichier et renvoie la Scène.
     */
    public Scene parse(String filePath) throws IOException, SceneParseException {
        this.scene = new Scene();
        this.lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim(); // 

                // Ignorer les lignes vides ou les commentaires 
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // Sépare la ligne par des espaces (un ou plusieurs)
                String[] parts = line.split("\\s+"); // 
                String command = parts[0];

                // Gère les commandes avec un switch 
                processCommand(command, parts);
            }
        }

        // --- Validation après lecture ---
        if (!sizeSet) {
            throw new SceneParseException("La commande 'size' est obligatoire."); // [cite: 256]
        }
        if (!cameraSet) {
            throw new SceneParseException("La commande 'camera' est obligatoire."); // 
        }
        // Valide la somme des lumières [cite: 297, 298]
        if (totalLightColor.getR() > 1.0 || totalLightColor.getG() > 1.0 || totalLightColor.getB() > 1.0) {
            throw new SceneParseException("La somme des couleurs des lumières dépasse 1.0.");
        }

        return this.scene;
    }

    /**
     * Aiguille vers la bonne méthode de parsing en fonction de la commande.
     */
    private void processCommand(String command, String[] parts) throws SceneParseException {
        try {
            switch (command) {
                // Commandes principales
                case "size":
                    parseSize(parts);
                    sizeSet = true;
                    break;
                case "output":
                    parseOutput(parts);
                    break;
                case "camera":
                    parseCamera(parts);
                    cameraSet = true;
                    break;

                // Matériaux et couleurs
                case "ambient":
                    parseAmbient(parts);
                    break;
                case "diffuse":
                    this.currentDiffuse = parseColor(parts);
                    break;
                case "specular":
                    this.currentSpecular = parseColor(parts);
                    break;

                // Lumières
                case "directional":
                    parseDirectionalLight(parts);
                    break;
                case "point":
                    parsePointLight(parts);
                    break;

                // Géométrie
                case "maxverts":
                    this.maxVerts = pInt(parts[1]); // [cite: 312]
                    break;
                case "vertex":
                    this.vertices.add(parsePoint(parts, 1));
                    break;
                case "sphere":
                    parseSphere(parts);
                    break;
                case "plane":
                    parsePlane(parts);
                    break;
                case "tri":
                    parseTriangle(parts);
                    break;

                default:
                    System.err.println("Commande inconnue ignorée (ligne " + lineNumber + "): " + command);
            }
        } catch (NumberFormatException e) {
            throw new SceneParseException("Format de nombre invalide.", lineNumber);
        } catch (IndexOutOfBoundsException e) {
            throw new SceneParseException("Nombre d'arguments incorrect pour la commande '" + command + "'.", lineNumber);
        } catch (SceneParseException e) {
            // Re-lancer l'exception si elle vient d'une méthode de validation
            throw e;
        }
    }

    // --- Méthodes de Parsing Détaillées ---

    private void parseSize(String[] parts) {
        scene.setDimensions(pInt(parts[1]), pInt(parts[2])); // [cite: 258]
    }

    private void parseOutput(String[] parts) {
        scene.setOutputName(parts[1]); // [cite: 262]
    }

    private void parseCamera(String[] parts) {
        Point lookFrom = new Point(pD(parts[1]), pD(parts[2]), pD(parts[3])); // [cite: 266]
        Point lookAt = new Point(pD(parts[4]), pD(parts[5]), pD(parts[6])); // [cite: 266]
        Vector up = new Vector(pD(parts[7]), pD(parts[8]), pD(parts[9])); // [cite: 266]
        double fov = pD(parts[10]); // [cite: 266]
        scene.setCamera(new Camera(lookFrom, lookAt, up, fov));
    }

    private void parseAmbient(String[] parts) {
        scene.setAmbientColor(parseColor(parts)); // [cite: 271]
    }

    private void parseDirectionalLight(String[] parts) throws SceneParseException {
        Vector dir = new Vector(pD(parts[1]), pD(parts[2]), pD(parts[3])); // [cite: 289]
        Color color = new Color(pD(parts[4]), pD(parts[5]), pD(parts[6])); // [cite: 289]
        validateLightColor(color); // 
        scene.addLight(new DirectionalLight(dir, color));
    }

    private void parsePointLight(String[] parts) throws SceneParseException {
        Point pos = new Point(pD(parts[1]), pD(parts[2]), pD(parts[3])); // [cite: 293]
        Color color = new Color(pD(parts[4]), pD(parts[5]), pD(parts[6])); // [cite: 294]
        validateLightColor(color); // 
        scene.addLight(new PointLight(pos, color));
    }

    private void parseSphere(String[] parts) throws SceneParseException {
        Point center = new Point(pD(parts[1]), pD(parts[2]), pD(parts[3])); // 
        double radius = pD(parts[4]); // 
        validateAmbientDiffuseSum(scene.getAmbientColor(), this.currentDiffuse); // [cite: 274, 275]
        scene.addShape(new Sphere(center, radius, this.currentDiffuse, this.currentSpecular));
    }

    private void parsePlane(String[] parts) throws SceneParseException {
        Point point = new Point(pD(parts[1]), pD(parts[2]), pD(parts[3])); // 
        Vector normal = new Vector(pD(parts[4]), pD(parts[5]), pD(parts[6])); // 
        validateAmbientDiffuseSum(scene.getAmbientColor(), this.currentDiffuse); // [cite: 274, 275]
        scene.addShape(new Plane(point, normal, this.currentDiffuse, this.currentSpecular));
    }

    private void parseTriangle(String[] parts) throws SceneParseException {
        int i1 = pInt(parts[1]); // [cite: 324]
        int i2 = pInt(parts[2]); // [cite: 324]
        int i3 = pInt(parts[3]); // [cite: 324]

        // Valider les indices [cite: 329]
        if (i1 >= maxVerts || i2 >= maxVerts || i3 >= maxVerts || i1 < 0 || i2 < 0 || i3 < 0) {
            throw new SceneParseException("Indice de sommet invalide (maxverts=" + maxVerts + ").", lineNumber);
        }
        
        Point p1 = this.vertices.get(i1);
        Point p2 = this.vertices.get(i2);
        Point p3 = this.vertices.get(i3);

        validateAmbientDiffuseSum(scene.getAmbientColor(), this.currentDiffuse); // [cite: 274, 275]
        scene.addShape(new Triangle(p1, p2, p3, this.currentDiffuse, this.currentSpecular));
    }

    // --- Méthodes Utilitaires ---

    private Color parseColor(String[] parts) {
        return new Color(pD(parts[1]), pD(parts[2]), pD(parts[3]));
    }

    private Point parsePoint(String[] parts, int offset) {
        return new Point(pD(parts[offset]), pD(parts[offset+1]), pD(parts[offset+2]));
    }

    // Raccourci pour Double.parseDouble 
    private double pD(String s) {
        return Double.parseDouble(s);
    }

    // Raccourci pour Integer.parseInt [cite: 352]
    private int pInt(String s) {
        return Integer.parseInt(s);
    }

    // --- Méthodes de Validation ---

    private void validateLightColor(Color newLight) {
        // Ajoute la nouvelle couleur de lumière au total 
        this.totalLightColor = this.totalLightColor.add(newLight);
        // La validation finale (si > 1.0) se fait à la fin du parsing
    }

    private void validateAmbientDiffuseSum(Color ambient, Color diffuse) throws SceneParseException {
        // Vérifie que (ambient + diffuse) <= 1.0 [cite: 274, 275, 279]
        if ((ambient.getR() + diffuse.getR() > 1.0) ||
            (ambient.getG() + diffuse.getG() > 1.0) ||
            (ambient.getB() + diffuse.getB() > 1.0)) {
            throw new SceneParseException("La somme de 'ambient' et 'diffuse' dépasse 1.0.", lineNumber);
        }
    }
}