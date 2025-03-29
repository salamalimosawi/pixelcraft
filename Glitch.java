import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Applies a digital glitch effect by randomly shifting color channels horizontally.
 * Creates RGB channel misalignment for a "broken screen" aesthetic.
 */
public class Glitch extends Converter {
    
    // Random number generator for glitch parameters
    private final Random rand = new Random();

    /**
     * Processes an image to apply glitch effect through RGB channel shifting
     * @param img The input image to be processed
     * @return A new BufferedImage with glitch effect applied
     */
    @Override
    protected BufferedImage processImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        
        // Create output image with ARGB color space
        BufferedImage processedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Determine maximum random shift distance (1-10 pixels)
        int shiftAmount = rand.nextInt(10) + 1; 

        // Process each scanline independently
        for (int y = 0; y < height; y++) {
            // Generate unique shifts for each color channel per line
            int rShift = rand.nextInt(shiftAmount) - shiftAmount / 2; // Red shift (-5 to +5)
            int gShift = rand.nextInt(shiftAmount) - shiftAmount / 2; // Green shift
            int bShift = rand.nextInt(shiftAmount) - shiftAmount / 2;  // Blue shift

            // Process each pixel in the current line
            for (int x = 0; x < width; x++) {
                // Calculate shifted positions with boundary checks
                int newX_R = Math.min(Math.max(x + rShift, 0), width - 1);
                int newX_G = Math.min(Math.max(x + gShift, 0), width - 1);
                int newX_B = Math.min(Math.max(x + bShift, 0), width - 1);

                // Sample color channels from different positions
                ARGB original = new ARGB(img.getRGB(x, y));
                int r = new ARGB(img.getRGB(newX_R, y)).red;    // Shifted red
                int g = new ARGB(img.getRGB(newX_G, y)).green;  // Shifted green
                int b = new ARGB(img.getRGB(newX_B, y)).blue;   // Shifted blue

                // Combine shifted colors with original alpha
                ARGB newColor = new ARGB(original.alpha, r, g, b);
                processedImg.setRGB(x, y, newColor.toInt());
            }
        }

        return processedImg;
    }
}
