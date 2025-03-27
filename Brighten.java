import java.awt.image.BufferedImage;

/**
 * The Brighten class is a subclass of the Converter class. It is responsible for 
 * increasing the brightness of an image by a given factor.
 * The class iterates over all pixels in the image, extracts the RGB values, 
 * and adds a brightness factor to each of them, ensuring that the new RGB 
 * values stay within the valid range of [0, 255].
 */

public class Brighten extends Converter {

    // Brightness factor to increase each pixel's RGB values
    private int brightnessFactor = 90;

    /**
     * Processes the image by iterating through each pixel and adjusting 
     * the brightness of each pixel's color.
     * 
     * @param img The BufferedImage object representing the image to be processed.
     */
    
    @Override
    protected void processImage(BufferedImage img) {
        // Iterate over each pixel in the image by going through rows (y) and columns (x)
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                // Get the RGB value of the current pixel at (x, y)
                int pixel = img.getRGB(x, y);

                // Create an ARGB object to extract the individual components (alpha, red, green, blue)
                ARGB argb = new ARGB(pixel);

                // Increase the RGB values by the brightness factor, ensuring the value is capped at 255
                int newRed = Math.min(255, argb.red + brightnessFactor);
                int newGreen = Math.min(255, argb.green + brightnessFactor);
                int newBlue = Math.min(255, argb.blue + brightnessFactor);

                // Create a new ARGB object with the adjusted RGB values
                ARGB newARGB = new ARGB(argb.alpha, newRed, newGreen, newBlue);

                // Set the new RGB value for the pixel at (x, y) in the image
                img.setRGB(x, y, newARGB.toInt());
            }
        }
    }
}
