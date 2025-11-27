import java.awt.image.BufferedImage;

/**
 * Applies a pixelation effect by replacing blocks of pixels with their average color.
 * This class creates a retro, low-resolution aesthetic by dividing the image into
 * square blocks and replacing all pixels within each block with the average color
 * of that block. This effect is commonly used in retro gaming art, censorship,
 * and creative photo effects.
 */
public class Pixelate extends Converter {
    
    /**
     * The size of the blocks for pixelation.
     * Larger values create more pronounced pixelation (lower resolution).
     * Default block size is 10x10 pixels.
     */
    private final int blockSize = 10;
    
    /**
     * Pixelates the image by dividing it into blocks.
     * Each block is replaced with the average color of its pixels. This creates
     * a mosaic-like effect where detail is reduced to uniform-colored squares.
     * Edge blocks that don't fit evenly into the image dimensions are handled
     * by using the remaining pixels without padding.
     * 
     * @param img The image to be processed
     * @return A new image with a pixelated effect, where groups of pixels are
     *         replaced with their average color
     */
    @Override
    protected BufferedImage processImage(BufferedImage img) {
        // Get the width and height of the image
        int width = img.getWidth();
        int height = img.getHeight();
        
        // Create a new image with the same width and height
        BufferedImage processedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // Process each block of pixels in the image
        for (int y = 0; y < height; y += blockSize) {
            for (int x = 0; x < width; x += blockSize) {
                // Variables to calculate the average color for the block
                int avgRed = 0, avgGreen = 0, avgBlue = 0;
                int count = 0;
                
                // Loop through the block and accumulate the color values
                for (int by = y; by < Math.min(y + blockSize, height); by++) {
                    for (int bx = x; bx < Math.min(x + blockSize, width); bx++) {
                        int pixel = img.getRGB(bx, by);
                        ARGB argb = new ARGB(pixel);
                        
                        // Accumulate the red, green, and blue components of each pixel
                        avgRed += argb.red;
                        avgGreen += argb.green;
                        avgBlue += argb.blue;
                        count++;
                    }
                }
                
                // Calculate the average color for the block
                avgRed /= count;
                avgGreen /= count;
                avgBlue /= count;
                
                // Set the average color for each pixel in the block
                for (int by = y; by < Math.min(y + blockSize, height); by++) {
                    for (int bx = x; bx < Math.min(x + blockSize, width); bx++) {
                        // Create a new ARGB object with the average color and full opacity
                        ARGB newArgb = new ARGB(255, avgRed, avgGreen, avgBlue);
                        processedImg.setRGB(bx, by, newArgb.toInt());
                    }
                }
            }
        }
        
        return processedImg;
    }
}
