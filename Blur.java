import java.awt.image.BufferedImage;

public class Blur extends Converter {

    @Override
    protected void processImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        
        
        BufferedImage temp = new BufferedImage(width, height, img.getType());
       
        applyRecursiveBlur(img, temp, 0, 0, width, height);
        
    }

    private void applyRecursiveBlur(BufferedImage img, BufferedImage temp, int x, int y, int width, int height) {
        if (width <= 2 || height <= 2) {
            return;
        }

        applyBoxBlur(img, temp, x, y, width, height);

        copyRegion(temp, img, x, y, width, height);

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        applyRecursiveBlur(img, temp, x, y, halfWidth, halfHeight);
        applyRecursiveBlur(img, temp, x + halfWidth, y, halfWidth, halfHeight);
        applyRecursiveBlur(img, temp, x, y + halfHeight, halfWidth, halfHeight);
        applyRecursiveBlur(img, temp, x + halfWidth, y + halfHeight, halfWidth, halfHeight);
    }

    private void applyBoxBlur(BufferedImage img, BufferedImage temp, int x, int y, int width, int height) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                if (i >= img.getWidth() || j >= img.getHeight()) {
                    continue;
                }

                int sumAlpha = 0, sumRed = 0, sumGreen = 0, sumBlue = 0;
                int count = 0;

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = i + dx;
                        int ny = j + dy;

                        if (nx >= 0 && nx < img.getWidth() && ny >= 0 && ny < img.getHeight()) {
                            ARGB pixel = new ARGB(img.getRGB(nx, ny));
                            sumAlpha += pixel.alpha;
                            sumRed += pixel.red;
                            sumGreen += pixel.green;
                            sumBlue += pixel.blue;
                            count++;
                        }
                    }
                }

                ARGB blurredPixel = new ARGB(
                    sumAlpha / count,
                    sumRed / count,
                    sumGreen / count,
                    sumBlue / count
                );

                temp.setRGB(i, j, blurredPixel.toInt());
            }
        }
    }

    private void copyRegion(BufferedImage src, BufferedImage dest, int x, int y, int width, int height) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                if (i < dest.getWidth() && j < dest.getHeight()) {
                    dest.setRGB(i, j, src.getRGB(i, j));
                }
            }
        }
    }
}



