package raytracer;

import raytracer.parsing.SceneFileParser;
import raytracer.parsing.SceneParseException;
import raytracer.scene.Scene;
import raytracer.trace.ImageRenderer;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String sceneFilePath;

        if (args.length == 1) {
            sceneFilePath = args[0];
            System.out.println("Fichier de scène lu depuis les arguments: " + sceneFilePath);
        } else if (args.length == 0) {
            sceneFilePath = "src/main/resources/final.scene";
            System.out.println("Aucun argument fourni. Utilisation du chemin par défaut: " + sceneFilePath);
        } else {
            System.err.println("Usage: java -jar raytracer.jar <path_to_scene_file>");
            System.err.println("Ou sans argument pour utiliser le fichier par défaut: src/main/resources/final.scee");
            System.exit(1);
            return;
        }

        SceneFileParser parser = new SceneFileParser();
        ImageRenderer renderer = new ImageRenderer();

        try {
            System.out.println("Lecture de la scène");
            Scene scene = parser.parse(sceneFilePath);
            System.out.println("Lecture de la scène réussie.");

            if (scene.getCamera() == null) {
                throw new SceneParseException("La caméra n'est pas définie dans le fichier de scène.");
            }

            System.out.println("Rendu de l'image");
            renderer.render(scene); // Lancement du rendu

        } catch (IOException e) {
            System.err.println("Erreur: Impossible de lire le fichier de scène (" + sceneFilePath + ") ou d'écrire l'image.");
            e.printStackTrace();
            System.exit(1);
        } catch (SceneParseException e) {
            System.err.println("Erreur fatale lors du parsing ou contrainte non respectée: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Erreur inattendue durant le rendu: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}