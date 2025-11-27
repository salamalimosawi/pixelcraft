import java.awt.image.BufferedImage;

/**
 * Applies an intense 5x5 box blur effect to images while preserving transparency.
 * This implementation uses a 25-pixel neighborhood averaging technique to create
 * a strong blur effect. Each pixel's RGB values are replaced with the average
 * of all valid pixels in a 5x5 grid centered on that pixel.
 * 
 * @author Salam Mosawi
 * @version 1.0
 * @since 2024
 */
public class Blur extends Converter {
    
    /**
     * Processes an image to apply intense 5x5 box blur effect.
     * For each pixel, this method samples a 5x5 neighborhood and computes
     * the average RGB values. The alpha channel is preserved from the original.
     * 
     * @param img The input image to be blurred
     * @return A new BufferedImage with the 5x5 box blur applied
     */
    @Override
    protected BufferedImage processImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // Process each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Initialize RGB accumulators
                int sumRed = 0, sumGreen = 0, sumBlue = 0;
                int count = 0;
                
                // Sample 5x5 neighborhood (25 pixels)
                for (int dy = -2; dy <= 2; dy++) {
                    for (int dx = -2; dx <= 2; dx++) {
                        int nx = x + dx;
                        int ny = y + dy;
                        
                        // Verify pixel is within image bounds
                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            ARGB pixel = new ARGB(img.getRGB(nx, ny));
                            sumRed += pixel.red;
                            sumGreen += pixel.green;
                            sumBlue += pixel.blue;
                            count++;
                        }
                    }
                }
                
                // Preserve original alpha channel while applying blurred RGB
                result.setRGB(x, y, new ARGB(
                    img.getRGB(x, y) >>> 24,  // Original alpha
                    sumRed / count,           // Averaged red
                    sumGreen / count,         // Averaged green
                    sumBlue / count           // Averaged blue
                ).toInt());
            }
        }
        
        return result;
    }
}
