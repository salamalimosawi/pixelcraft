import java.awt.image.BufferedImage;

public class Brighten extends Converter {

    private int brightnessFactor = 90;

    @Override
    protected void processImage(BufferedImage img) {
        // Iterate over each pixel in the image
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x, y);

                ARGB argb = new ARGB(pixel);

                // Increase the RGB values, ensuring they stay within [0, 255]
                int newRed = Math.min(255, argb.red + brightnessFactor);
                int newGreen = Math.min(255, argb.green + brightnessFactor);
                int newBlue = Math.min(255, argb.blue + brightnessFactor);

                // Create a new ARGB with the adjusted brightness
                ARGB newARGB = new ARGB(argb.alpha, newRed, newGreen, newBlue);

                // Set the new pixel value
                img.setRGB(x, y, newARGB.toInt());
            }
        }
    }
}
