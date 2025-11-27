import java.awt.image.BufferedImage;

/**
 * Flips images horizontally (mirrors along the vertical axis).
 * This class implements horizontal image flipping using a fully recursive algorithm.
 * Each pixel's position is mirrored across the y-axis, creating a left-to-right
 * reflection of the original image. All pixel data including RGB and alpha channels
 * are preserved during the transformation.
 */
public class FlipHorizontal extends Converter {
    
    /**
     * Processes the image by flipping it horizontally.
     * This method creates a new image where the pixels are mirrored across
     * the vertical center axis. The leftmost pixels become rightmost and vice versa.
     * All color and transparency information is preserved.
     * 
     * @param img The BufferedImage object representing the input image to be processed
     * @return A new BufferedImage representing the horizontally flipped image
     */
    @Override
    protected BufferedImage processImage(BufferedImage img) {
        // Get width and height of input image
        int width = img.getWidth();
        int height = img.getHeight();
        
        // Create a new horizontally flipped image
        BufferedImage flippedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // Start the recursive process
        flipRowRecursive(img, flippedImg, width, height, 0);
        
        // Return the new flipped image
        return flippedImg;
    }
    
    /**
     * Recursively processes each row of the image for horizontal flipping.
     * This method iterates through each row from top to bottom recursively,
     * delegating pixel-level processing to {@link #flipPixelRecursive}.
     * 
     * @param img The original image to read pixels from
     * @param flippedImg The destination image to write flipped pixels to
     * @param width The width of the image
     * @param height The height of the image
     * @param y The current row being processed
     */
    private void flipRowRecursive(BufferedImage img, BufferedImage flippedImg, int width, int height, int y) {
        // Base case: stop when all rows are processed
        if (y >= height)
            return;
        
        flipPixelRecursive(img, flippedImg, width, y, 0);
        flipRowRecursive(img, flippedImg, width, height, y + 1);
    }
    
    /**
     * Recursively flips pixels within a single row.
     * For each pixel at position (x, y) in the original image, this method
     * places it at position (width - 1 - x, y) in the flipped image, creating
     * the horizontal mirror effect.
     * 
     * @param img The original image to read pixels from
     * @param flippedImg The destination image to write flipped pixels to
     * @param width The width of the image
     * @param y The current row being processed
     * @param x The current column being processed
     */
    private void flipPixelRecursive(BufferedImage img, BufferedImage flippedImg, int width, int y, int x) {
        // Base case: stop when all pixels in the row are processed
        if (x >= width)
            return;
        
        flippedImg.setRGB(width - 1 - x, y, img.getRGB(x, y));
        flipPixelRecursive(img, flippedImg, width, y, x + 1);
    }
}
