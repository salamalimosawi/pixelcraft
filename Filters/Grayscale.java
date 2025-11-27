import java.awt.image.BufferedImage;

/**
 * Converts colored images to grayscale using the averaging method.
 * This class implements grayscale conversion by calculating the average of the
 * red, green, and blue color channels for each pixel. While this method is
 * computationally simple, it treats all color channels equally and may not
 * produce the most perceptually accurate grayscale representation.
 */
public class Grayscale extends Converter {
    
    /**
     * Processes the input image and converts it to grayscale.
     * This method calculates the grayscale value for each pixel by averaging the
     * red, green, and blue components. The resulting gray value is assigned to all
     * three RGB channels while preserving the original alpha (transparency) value.
     * 
     * @param img A BufferedImage representing the input image to be processed
     * @return A new BufferedImage representing the grayscale image with the same
     *         dimensions as the input
     */
    @Override
    public BufferedImage processImage(BufferedImage img) {
        // Get width and height of input image
        int width = img.getWidth();
        int height = img.getHeight();
        
        // Create a new grayscale image with the same width and height
        BufferedImage processedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // Iterate over each pixel to convert it to grayscale
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                int rgb = img.getRGB(x, y);
                ARGB color = new ARGB(rgb);
                
                int grayLevel = (color.red + color.green + color.blue) / 3;
                ARGB grayColor = new ARGB(color.alpha, grayLevel, grayLevel, grayLevel);
                
                processedImg.setRGB(x, y, grayColor.toInt());
            }
        }
        
        return processedImg;
    }
}
