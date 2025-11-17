package raytracer;

import java.io.IOException;
import raytracer.parsing.SceneFileParser;
import raytracer.parsing.SceneParseException;
import raytracer.scene.Scene;

/**
 * Classe principale du projet Raytracer.
 * Pour l'instant, son rôle est de tester le parser de scène (TP2).
 */
public class RayTracer {

    public static void main(String[] args) {
        
        // 1. Vérifier qu'on a bien un nom de fichier en argument
        if (args.length != 1) {
            System.err.println("Usage: java raytracer.RayTracer <fichier_scene.txt>");
            return;
        }

        String sceneFilePath = args[0];
        System.out.println("--- Lancement du RayTracer ---");
        System.out.println("Lecture du fichier : " + sceneFilePath);

        try {
            // 2. Créer le parser et l'utiliser
            SceneFileParser parser = new SceneFileParser();
            Scene scene = parser.parse(sceneFilePath); //

            // 3. Si on arrive ici, le parsing a réussi !
            System.out.println("Parsing de la scène réussi !");
            
            // 4. On affiche un résumé pour vérifier que tout est correct
            System.out.println("--- Résumé de la Scène ---");
            System.out.println("Taille image   : " + scene.getWidth() + "x" + scene.getHeight()); // [cite: 387]
            System.out.println("Fichier sortie : " + scene.getOutputName()); // [cite: 389]
            System.out.println("Caméra (position): " + scene.getCamera().getLookFrom()); // [cite: 391, 401]
            System.out.println("Lumière ambiante : " + scene.getAmbientColor()); // [cite: 393]
            System.out.println("Nombre de lumières : " + scene.getLights().size()); // [cite: 395]
            System.out.println("Nombre d'objets  : " + scene.getShapes().size()); // [cite: 396]

        } catch (IOException e) {
            // Erreur si le fichier n'est pas trouvé
            System.err.println(" Erreur d'entrée/sortie : " + e.getMessage());
        } catch (SceneParseException e) {
            // Notre erreur personnalisée !
            System.err.println(" Erreur dans le fichier de scène : " + e.getMessage());
        }
    }
}