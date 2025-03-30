import java.awt.image.BufferedImage;

/**
 * Inverts the colors of an image by turning each pixel into its opposite color.
 * Uses a recursive approach to process the image without loops.
 * Works by subtracting each color value (red, green, blue) from 255.
 */

public class Invert extends Converter {
    private static final int BLOCK_SIZE = 16; // Process 16x16 blocks at a time

    /**
     * Processes the given image by inverting all its pixels recursively.
     * Creates a new BufferedImage with the same dimensions as the input image,
     * then recursively processes the image in blocks (16x16 pixels by default)
     * to invert each pixel's color values (red, green, blue).
     * 
     * @param img The input image to be processed
     * @return A new BufferedImage containing the inverted version of the input image
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
     * Recursively processes blocks of the image.
     * If the block is small enough (<= BLOCK_SIZE), invert its pixels recursively.
     * Otherwise, split it into smaller blocks and recurse.
     *
     * @param original The original source image to read pixels from
     * @param processed The destination image where inverted pixels will be written
     * @param startX The starting x-coordinate of the current block to process
     * @param startY The starting y-coordinate of the current block to process
     * @param width The width of the current block to process
     * @param height The height of the current block to process
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
            invertBlockRecursively(original, processed, startX, startY, startX + width, startY + height, startX, startY);
            return processed;
        }

        // Split into smaller blocks and recurse
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        // Top-left block
        invertRecursively(original, processed, startX, startY, halfWidth, halfHeight);
        // Top-right block
        invertRecursively(original, processed, startX + halfWidth, startY, width - halfWidth, halfHeight);
        // Bottom-left block
        invertRecursively(original, processed, startX, startY + halfHeight, halfWidth, height - halfHeight);
        // Bottom-right block
        invertRecursively(original, processed, startX + halfWidth, startY + halfHeight, width - halfWidth, height - halfHeight);

        return processed;
    }

    /**
     * Recursively inverts all pixels in a specified rectangular block without using loops.
     * Processes pixels row by row, moving to the next pixel column until the end of the row,
     * then moves to the next row until the entire block is processed.
     * 
     * @param original The source image containing the original pixels
     * @param processed The destination image where inverted pixels will be stored
     * @param startX The starting x-coordinate of the block boundary
     * @param startY The starting y-coordinate of the block boundary
     * @param endX The ending x-coordinate of the block boundary (exclusive)
     * @param endY The ending y-coordinate of the block boundary (exclusive)
     * @param currentX The current x-coordinate being processed (for recursion tracking)
     * @param currentY The current y-coordinate being processed (for recursion tracking)
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
            invertBlockRecursively(original, processed, startX, startY, endX, endY, startX, currentY + 1);
            return;
        }

        // Invert the current pixel
        int pixel = original.getRGB(currentX, currentY);
        int invertedPixel = invertPixel(pixel);
        processed.setRGB(currentX, currentY, invertedPixel);

        // Move to the next pixel in the row
        invertBlockRecursively(original, processed, startX, startY, endX, endY, currentX + 1, currentY);
    }

    /**
     * Inverts the RGB color channels of a single pixel while preserving its alpha (transparency).
     * The inversion is performed by subtracting each color component (red, green, blue) from 255.
     * The alpha channel remains unchanged to maintain transparency.
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
