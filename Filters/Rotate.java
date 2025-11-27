import java.awt.image.BufferedImage;

/**
 * Rotates images 90 degrees clockwise.
 * This class performs a geometric transformation that rotates the entire image
 * by 90 degrees in the clockwise direction. The output image dimensions are
 * swapped (width becomes height and vice versa) to accommodate the rotation.
 */
public class Rotate extends Converter {
    
    /**
     * Processes the image by rotating it 90 degrees clockwise.
     * This method creates a new image with swapped dimensions (width ↔ height)
     * and maps each pixel from the original image to its new rotated position.
     * All pixel data including RGB and alpha channels are preserved during rotation.
     * 
     * @param img A BufferedImage representing the input image to be processed
     * @return A new BufferedImage representing the rotated image with dimensions
     *         swapped from the original (original width becomes new height)
     */
    @Override
    protected BufferedImage processImage(BufferedImage img) {
        // Get width and height of input image
        int width = img.getWidth();
        int height = img.getHeight();
        
        // Create a new rotated image with width and height swapped
        BufferedImage rotatedImg = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
        
        // Iterate through each pixel of the input image
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Get the RGB value of the current pixel at (x, y)
                int pixel = img.getRGB(x, y);
                
                // Calculate the new pixel position for clockwise 90 degree rotation
                rotatedImg.setRGB(height - 1 - y, x, pixel);
            }
        }
        
        // Return the new rotated image
        return rotatedImg;
    }
}
