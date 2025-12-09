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

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int correctedJ = height - 1 - j;

                Color color = rayTracer.getPixelColor(i, correctedJ);

                image.setRGB(i, j, color.toRGB());
            }
        }

        File outputFile = new File(outputFileName);
        ImageIO.write(image, "png", outputFile);

        System.out.println("Rendu terminé. Image sauvegardée sous : " + outputFileName);
    }
}