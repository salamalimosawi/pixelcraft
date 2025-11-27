import java.awt.image.BufferedImage;

/**
 * Increases the brightness of images by adding a fixed value to each RGB channel.
 * This class implements additive brightness adjustment. Each pixel's red, green,
 * and blue values are increased by a constant factor, with automatic clamping
 * to prevent overflow beyond the valid 0-255 range.
 */
public class Brighten extends Converter {
    
    /**
     * The brightness factor added to each RGB channel.
     * Default value is 90.
     */
    private int brightnessFactor = 90;
    
    /**
     * Processes an image to increase its brightness.
     * Creates a new image where each pixel's RGB values are increased by the
     * brightness factor. The alpha channel is preserved. RGB values are clamped at 255.
     * 
     * @param img The input image to brighten
     * @return A new BufferedImage with increased brightness
     */
    @Override
    protected BufferedImage processImage(BufferedImage img) {
        // Get width and height of input image
        int width = img.getWidth();
        int height = img.getHeight();
        
        // Create a new image with same width and height
        BufferedImage processedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // Iterate over each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the RGB value of the current pixel at (x, y)
                int pixel = img.getRGB(x, y);
                
                // Create an ARGB object to extract the individual components
                ARGB argb = new ARGB(pixel);
                
                // Increase the RGB values by the brightness factor (restrict at 255)
                int newRed = Math.min(255, argb.red + brightnessFactor);
                int newGreen = Math.min(255, argb.green + brightnessFactor);
                int newBlue = Math.min(255, argb.blue + brightnessFactor);
                
                // Create a new ARGB object with the adjusted RGB values
                ARGB newARGB = new ARGB(argb.alpha, newRed, newGreen, newBlue);
                
                // Set the new RGB value for the pixel
                processedImg.setRGB(x, y, newARGB.toInt());
            }
        }
        
        return processedImg;
    }
}
