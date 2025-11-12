package imgcompare;


import java.awt.image.BufferedImage;
import java.awt.Color;

public class ImageComparator {

    public class ComparisonResult {
        private final int diffPixelCount;
        private final BufferedImage diffImage;

        public ComparisonResult(int diffPixelCount, BufferedImage diffImage) {
            this.diffPixelCount = diffPixelCount;
            this.diffImage = diffImage;
        }

        public int getDiffPixelCount() {
            return diffPixelCount;
        }

        public BufferedImage getDiffImage() {
            return diffImage;
        }
    }

    public class ImageDimensionException extends Exception {
        public ImageDimensionException(String message) {
            super(message);
        }
    }

    public ComparisonResult compareImages(BufferedImage img1, BufferedImage img2) throws ImageDimensionException {
        int width1 = img1.getWidth();
        int height1 = img1.getHeight();
        int width2 = img2.getWidth();
        int height2 = img2.getHeight();

        if (width1 != width2 || height1 != height2) {
            throw new ImageDimensionException("Les images n'ont pas les mêmes dimensions !");
        }

        int diffPixelCount = 0;
        BufferedImage diffImage = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {

                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                if (rgb1 == rgb2) {
                    diffImage.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    diffPixelCount++;

                    int r1 = (rgb1 >> 16) & 0xFF;
                    int g1 = (rgb1 >> 8) & 0xFF;
                    int b1 = rgb1 & 0xFF;

                    int r2 = (rgb2 >> 16) & 0xFF;
                    int g2 = (rgb2 >> 8) & 0xFF;
                    int b2 = rgb2 & 0xFF;

                    int diffR = Math.abs(r1 - r2);
                    int diffG = Math.abs(g1 - g2);
                    int diffB = Math.abs(b1 - b2);

                    int diffColor = (0xFF << 24) | (diffR << 16) | (diffG << 8) | diffB;
                    diffImage.setRGB(x, y, diffColor);
                }
            }
        }
        return new ComparisonResult(diffPixelCount, diffImage);
    }
}