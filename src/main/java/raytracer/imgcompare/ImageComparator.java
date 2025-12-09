package raytracer.imgcompare;

import java.awt.image.BufferedImage;

/**
 * Classe utilitaire pour comparer deux images BufferedImage.
 * Elle permet de compter le nombre de pixels différents et de générer
 * une image représentant les différences.
 */
public class ImageComparator {

    private static final int THRESHOLD_DIFF = 1000;

    /**
     * Compare deux images BufferedImage pixel par pixel et retourne le nombre
     * de pixels différents. Les images doivent avoir la même taille[cite: 127].
     *
     * @param img1 La première image.
     * @param img2 La deuxième image.
     * @return Le nombre de pixels dont la couleur diffère.
     * @throws IllegalArgumentException si les images n'ont pas la même taille.
     */
    public int countDifferentPixels(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            throw new IllegalArgumentException("Les images n'ont pas la même taille.");
        }

        int diffCount = 0;
        int width = img1.getWidth();
        int height = img1.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                if (rgb1 != rgb2) {
                    diffCount++;
                }
            }
        }
        return diffCount;
    }

    /**
     * Détermine si deux images sont considérées comme identiques[cite: 76].
     *
     * @param diffCount Le nombre de pixels différents.
     * @return true si la différence est inférieure au seuil de 1000 pixels, false sinon.
     */
    public boolean areIdentical(int diffCount) {
        return diffCount < THRESHOLD_DIFF;
    }

    /**
     * Génère une image différentielle représentant les différences entre les deux images[cite: 77, 78].
     *
     * @param img1 La première image.
     * @param img2 La deuxième image.
     * @return Une BufferedImage représentant l'image différentielle.
     * @throws IllegalArgumentException si les images n'ont pas la même taille.
     */
    public BufferedImage generateDifferentialImage(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            throw new IllegalArgumentException("Les images n'ont pas la même taille.");
        }

        int width = img1.getWidth();
        int height = img1.getHeight();
        BufferedImage diffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                if (rgb1 == rgb2) {
                    diffImage.setRGB(x, y, 0x000000); // Noir
                } else {
                    int diffRGB = calculateColorDifference(rgb1, rgb2);
                    diffImage.setRGB(x, y, diffRGB);
                }
            }
        }
        return diffImage;
    }

    /**
     * Calcule la différence de couleur entre deux pixels.
     * Utilise la différence absolue des composantes R, G, B.
     *
     * @param rgb1 La valeur RGB du premier pixel.
     * @param rgb2 La valeur RGB du deuxième pixel.
     * @return La valeur RGB représentant la différence de couleur.
     */
    private int calculateColorDifference(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xFF;
        int g1 = (rgb1 >> 8) & 0xFF;
        int b1 = (rgb1) & 0xFF;

        int r2 = (rgb2 >> 16) & 0xFF;
        int g2 = (rgb2 >> 8) & 0xFF;
        int b2 = (rgb2) & 0xFF;

        int rDiff = Math.abs(r1 - r2);
        int gDiff = Math.abs(g1 - g2);
        int bDiff = Math.abs(b1 - b2);

        return (0xFF << 24) | (rDiff << 16) | (gDiff << 8) | bDiff;
    }
}