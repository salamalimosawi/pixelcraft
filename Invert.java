import java.awt.image.BufferedImage;

/**
 * Inverts the colors of an image by turning each pixel into its opposite color.
 * Uses a recursive approach to process the image without loops.
 * Works by subtracting each color value (red, green, blue) from 255.
 */

public class Invert extends Converter {
    private static final int BLOCK_SIZE = 16; // Process 16x16 blocks at a time

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

    /** Recursively inverts all pixels in a given block (no loops) */
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

    /** Inverts a single pixel's color */
    private int invertPixel(int pixel) {
        int alpha = (pixel >> 24) & 0xFF;
        int red = 255 - ((pixel >> 16) & 0xFF);
        int green = 255 - ((pixel >> 8) & 0xFF);
        int blue = 255 - (pixel & 0xFF);
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
}
