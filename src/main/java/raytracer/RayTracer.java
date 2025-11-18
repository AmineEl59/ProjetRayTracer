package raytracer;

import java.io.IOException;
import raytracer.parsing.SceneFileParser;
import raytracer.parsing.SceneParseException;
import raytracer.scene.Scene;

/**
 Classe principale du projet Raytracer.
 Pour l'instant, son rôle est de tester le parser de scène.
**/
public class RayTracer {

    public static void main(String[] args) {
        
        // Vérifier qu'on a bien un nom de fichier en argument
        if (args.length != 1) {
            System.err.println("Usage: java raytracer.RayTracer <fichier_scene.txt>");
            return;
        }

        String sceneFilePath = args[0];
        System.out.println("--- Lancement du RayTracer ---");
        System.out.println("Lecture du fichier : " + sceneFilePath);

        try {
            // Créer le parser et l'utiliser
            SceneFileParser parser = new SceneFileParser();
            Scene scene = parser.parse(sceneFilePath); //

            // Si on arrive ici, le parsing a réussi !
            System.out.println("Parsing de la scène réussi !");
            
            // On affiche un résumé pour vérifier que tout est correct
            System.out.println("--- Résumé de la Scène ---");
            System.out.println("Taille image   : " + scene.getWidth() + "x" + scene.getHeight());
            System.out.println("Fichier sortie : " + scene.getOutputName());
            System.out.println("Caméra (position): " + scene.getCamera().getLookFrom());
            System.out.println("Lumière ambiante : " + scene.getAmbientColor());
            System.out.println("Nombre de lumières : " + scene.getLights().size());
            System.out.println("Nombre d'objets  : " + scene.getShapes().size());

        } catch (IOException e) {
            // Erreur si le fichier n'est pas trouvé
            System.err.println(" Erreur d'entrée/sortie : " + e.getMessage());
        } catch (SceneParseException e) {
            // Notre erreur personnalisée !
            System.err.println(" Erreur dans le fichier de scène : " + e.getMessage());
        }
    }
}