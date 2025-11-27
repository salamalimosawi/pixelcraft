import java.awt.image.BufferedImage;

/**
 * Inverts the colors of an image by creating a photographic negative effect.
 * This class uses a recursive divide-and-conquer approach to process the image
 * in blocks. Each pixel's RGB values are inverted by subtracting them from 255,
 * while the alpha channel (transparency) is preserved. This creates the classic
 * color negative effect where dark areas become light and vice versa.
 */
public class Invert extends Converter {
    
    /**
     * The size of blocks to process at once.
     * Blocks larger than this are subdivided recursively.
     */
    private static final int BLOCK_SIZE = 16;
    
    /**
     * Processes the given image by inverting all its pixels recursively.
     * Creates a new BufferedImage with the same dimensions as the input, then
     * recursively processes the image in blocks (16x16 pixels by default) to
     * invert each pixel's RGB color values. The alpha channel is preserved.
     * 
     * @param img The input image to be processed
     * @return A new BufferedImage containing the color-inverted version of the input
     */
    @Override
    protected BufferedImage processImage(BufferedImage img) {
        BufferedImage processedImg = new BufferedImage(
            img.getWidth(), 
            img.getHeight(), 
            BufferedImage.TYPE_INT_ARGB
        );
        return invertRecursively(img, processedImg, 0, 0, img.getWidth(), img.getHeight());
    }
    
    /**
     * Recursively processes blocks of the image using divide-and-conquer.
     * If the block is small enough (≤ BLOCK_SIZE), inverts its pixels recursively.
     * Otherwise, divides the block into four quadrants and processes each recursively.
     * This approach limits recursion depth while maintaining a fully recursive
     * implementation.
     *
     * @param original The original source image to read pixels from
     * @param processed The destination image where inverted pixels will be written
     * @param startX The starting x-coordinate of the current block
     * @param startY The starting y-coordinate of the current block
     * @param width The width of the current block
     * @param height The height of the current block
     * @return The processed image with inverted colors
     */
    private BufferedImage invertRecursively(
        BufferedImage original,
        BufferedImage processed,
        int startX,
        int startY,
        int width,
        int height
    ) {
        // Base case: If the block is small enough, process its pixels recursively
        if (width <= BLOCK_SIZE && height <= BLOCK_SIZE) {
            invertBlockRecursively(original, processed, startX, startY, 
                                  startX + width, startY + height, startX, startY);
            return processed;
        }
        
        // Split into smaller blocks and recurse
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        
        // Top-left block
        invertRecursively(original, processed, startX, startY, halfWidth, halfHeight);
        
        // Top-right block
        invertRecursively(original, processed, startX + halfWidth, startY, 
                         width - halfWidth, halfHeight);
        
        // Bottom-left block
        invertRecursively(original, processed, startX, startY + halfHeight, 
                         halfWidth, height - halfHeight);
        
        // Bottom-right block
        invertRecursively(original, processed, startX + halfWidth, startY + halfHeight, 
                         width - halfWidth, height - halfHeight);
        
        return processed;
    }
    
    /**
     * Recursively inverts all pixels in a specified rectangular block.
     * Processes pixels row by row without loops, moving through each column
     * until reaching the end of the row, then advancing to the next row until
     * the entire block is processed.
     * 
     * @param original The source image containing the original pixels
     * @param processed The destination image where inverted pixels will be stored
     * @param startX The starting x-coordinate of the block boundary
     * @param startY The starting y-coordinate of the block boundary
     * @param endX The ending x-coordinate of the block boundary (exclusive)
     * @param endY The ending y-coordinate of the block boundary (exclusive)
     * @param currentX The current x-coordinate being processed
     * @param currentY The current y-coordinate being processed
     */
    private void invertBlockRecursively(
        BufferedImage original,
        BufferedImage processed,
        int startX,
        int startY,
        int endX,
        int endY,
        int currentX,
        int currentY
    ) {
        // Base case: If we've processed all rows, return
        if (currentY >= endY) {
            return;
        }
        
        // Base case: If we've processed all columns in this row, move to the next row
        if (currentX >= endX) {
            invertBlockRecursively(original, processed, startX, startY, endX, endY, 
                                  startX, currentY + 1);
            return;
        }
        
        // Invert the current pixel
        int pixel = original.getRGB(currentX, currentY);
        int invertedPixel = invertPixel(pixel);
        processed.setRGB(currentX, currentY, invertedPixel);
        
        // Move to the next pixel in the row
        invertBlockRecursively(original, processed, startX, startY, endX, endY, 
                              currentX + 1, currentY);
    }
    
    /**
     * Inverts the RGB color channels of a single pixel while preserving alpha.
     * The inversion is performed by subtracting each color component from 255.
     * The alpha channel remains unchanged to maintain the original transparency.
     * 
     * @param pixel The original pixel value in ARGB format (32-bit integer)
     * @return A new pixel value with inverted RGB channels and original alpha
     */
    private int invertPixel(int pixel) {
        int alpha = (pixel >> 24) & 0xFF;
        int red = 255 - ((pixel >> 16) & 0xFF);
        int green = 255 - ((pixel >> 8) & 0xFF);
        int blue = 255 - (pixel & 0xFF);
        
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
}
