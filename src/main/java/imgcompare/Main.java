package imgcompare;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java imgcompare.Main <image1.png> <image2.png>");
            return;
        }

        try {
            File file1 = new File(args[0]);
            File file2 = new File(args[1]);

            BufferedImage img1 = ImageIO.read(file1);
            BufferedImage img2 = ImageIO.read(file2);

            if (img1 == null || img2 == null) {
                System.err.println("Erreur: Impossible de lire une ou les deux images. Vérifiez les chemins.");
                return;
            }

            ImageComparator comparator = new ImageComparator();
            ImageComparator.ComparisonResult result = comparator.compareImages(img1, img2);

            int diffPixelCount = result.getDiffPixelCount();

            if (diffPixelCount < 1000) {
                System.out.println("OK");
            } else {
                System.out.println("KO");
            }

            System.out.println("Les deux images diffèrent de " + diffPixelCount + " pixels.");

            if (diffPixelCount > 0) {
                File outputDiffFile = new File("difference.png");
                ImageIO.write(result.getDiffImage(), "png", outputDiffFile); // 
                System.out.println("L'image des différences a été sauvegardée sous 'difference.png'");
            }

        } catch (IOException e) {
            System.err.println("Erreur d'entrée/sortie: " + e.getMessage());
        } catch (ImageComparator.ImageDimensionException e) {
            System.err.println("Erreur de comparaison: " + e.getMessage());
        }
    }
}