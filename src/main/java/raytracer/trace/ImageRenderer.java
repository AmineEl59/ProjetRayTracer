package raytracer.trace;

import raytracer.scene.Scene;
import raytracer.math.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Gère le processus de rendu : boucle sur les pixels et écriture du fichier PNG.
 */
public class ImageRenderer {

    public void render(Scene scene) throws IOException {
        int width = scene.getWidth();
        int height = scene.getHeight();
        String outputFileName = scene.getOutput();

        RayTracer rayTracer = new RayTracer(scene);

        // Crée une image en mémoire pour stocker les pixels
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        System.out.println("Début du rendu de l'image " + width + "x" + height + "...");

        // Boucle principale du lanceur de rayons
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                // CORRECTION DES COORDONNÉES Y :
                // L'axe Y de Java (BufferedImage) commence en haut (j=0).
                // La convention du Ray Tracer veut que j=0 soit le bas de l'image.
                // On inverse donc les coordonnées Y pour le calcul.
                int correctedJ = height - 1 - j;

                Color color = rayTracer.getPixelColor(i, correctedJ);

                // Peindre le pixel (i, j) dans l'image
                image.setRGB(i, j, color.toRGB());
            }
        }

        // Sauvegarder l'image
        File outputFile = new File(outputFileName);
        // Utilise ImageIO, nécessite les imports
        ImageIO.write(image, "png", outputFile);

        System.out.println("✅ Rendu terminé. Image sauvegardée sous : " + outputFileName);
    }
}