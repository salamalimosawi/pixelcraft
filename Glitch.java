import java.awt.image.BufferedImage;
import java.util.Random;

public class Glitch extends Converter {
    private final Random rand = new Random();

    @Override
    protected void processImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int shiftAmount = rand.nextInt(10) + 1; // Random shift between 1 and 10 pixels

        BufferedImage tempImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            int rShift = rand.nextInt(shiftAmount) - shiftAmount / 2;
            int gShift = rand.nextInt(shiftAmount) - shiftAmount / 2;
            int bShift = rand.nextInt(shiftAmount) - shiftAmount / 2;

            for (int x = 0; x < width; x++) {
                int newX_R = Math.min(Math.max(x + rShift, 0), width - 1);
                int newX_G = Math.min(Math.max(x + gShift, 0), width - 1);
                int newX_B = Math.min(Math.max(x + bShift, 0), width - 1);

                ARGB original = new ARGB(img.getRGB(x, y));
                int r = new ARGB(img.getRGB(newX_R, y)).red;
                int g = new ARGB(img.getRGB(newX_G, y)).green;
                int b = new ARGB(img.getRGB(newX_B, y)).blue;

                ARGB newColor = new ARGB(original.alpha, r, g, b);
                tempImage.setRGB(x, y, newColor.toInt());
            }
        }

        img.getGraphics().drawImage(tempImage, 0, 0, null);
    }
}

