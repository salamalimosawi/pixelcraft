import java.awt.image.BufferedImage;

public class EdgeDetection extends Converter {

    @Override
    protected void processImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int gx = 0;
                int gy = 0;

                gx += -1 * getGrayValue(img, x - 1, y - 1);
                gx +=  0 * getGrayValue(img, x, y - 1);
                gx +=  1 * getGrayValue(img, x + 1, y - 1);
                gx += -2 * getGrayValue(img, x - 1, y);
                gx +=  0 * getGrayValue(img, x, y);
                gx +=  2 * getGrayValue(img, x + 1, y);
                gx += -1 * getGrayValue(img, x - 1, y + 1);
                gx +=  0 * getGrayValue(img, x, y + 1);
                gx +=  1 * getGrayValue(img, x + 1, y + 1);

                gy += -1 * getGrayValue(img, x - 1, y - 1);
                gy += -2 * getGrayValue(img, x, y - 1);
                gy += -1 * getGrayValue(img, x + 1, y - 1);
                gy +=  1 * getGrayValue(img, x - 1, y + 1);
                gy +=  2 * getGrayValue(img, x, y + 1);
                gy +=  1 * getGrayValue(img, x + 1, y + 1);

                int magnitude = (int) Math.min(255, Math.sqrt(gx * gx + gy * gy));

                int originalPixel = img.getRGB(x, y);
                ARGB argb = new ARGB(originalPixel);
                int newPixel = new ARGB(argb.alpha, magnitude, magnitude, magnitude).toInt();

                img.setRGB(x, y, newPixel);
            }
        }
    }

    private int getGrayValue(BufferedImage img, int x, int y) {
        ARGB argb = new ARGB(img.getRGB(x, y));
        return (int) (0.2126 * argb.red + 0.7152 * argb.green + 0.0722 * argb.blue);
    }
}


