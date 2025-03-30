import java.awt.image.BufferedImage;

/**
 * The EdgeDetection class implements a recursive Sobel edge detection algorithm.
 * It converts an input image into an edge map where edges are highlighted in white
 * against a black background. The implementation is fully recursive, ande processes
 * the image row-by-row to avoid stack overflow.
 *
 * <p>The Sobel operator uses two 3x3 kernels (Gx for horizontal and Gy for vertical
 * edge detection) which are convolved with the image to detect edges.</p>
 */
public class EdgeDetection extends Converter {
    
    /**
     * The horizontal Sobel kernel for detecting vertical edges.
     * Kernel values: 
     * [-1, 0, 1]
     * [-2, 0, 2]
     * [-1, 0, 1]
     */
    private static final int[][] GX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    
    /**
     * The vertical Sobel kernel for detecting horizontal edges.
     * Kernel values:
     * [-1, -2, -1]
     * [ 0,  0,  0]
     * [ 1,  2,  1]
     */
    private static final int[][] GY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    
    /**
     * The number of rows to process in each batch. Smaller values reduce
     * recursion depth but may increase total recursive calls. A value of 10-20
     * provides good balance for most images.
     */
    private static final int ROW_BATCH_SIZE = 10;

    /**
     * Processes the input image to detect edges using recursive Sobel operator.
     * 
     * @param img The input image to be processed
     * @return A new BufferedImage with edges highlighted in white on black background
     */
    @Override
    protected BufferedImage processImage(BufferedImage img) {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        processRows(img, result, 1, Math.min(ROW_BATCH_SIZE, img.getHeight() - 2));
        return result;
    }

    /**
     * Recursively processes the image in batches of rows to limit stack depth.
     * 
     * @param src The source image to read pixels from
     * @param dest The destination image to write edge pixels to
     * @param startY The starting row index (inclusive)
     * @param endY The ending row index (inclusive)
     */
    private void processRows(BufferedImage src, BufferedImage dest, int startY, int endY) {
        // Base case: stop when we've processed all rows
        if (startY >= src.getHeight() - 1) return;
        
        // Process current batch of rows
        processRowBatch(src, dest, startY, endY, 1);
        
        // Process next batch recursively
        int nextStart = endY + 1;
        int nextEnd = Math.min(nextStart + ROW_BATCH_SIZE - 1, src.getHeight() - 2);
        processRows(src, dest, nextStart, nextEnd);
    }

    /**
     * Processes a batch of rows column by column.
     * 
     * @param src Source image
     * @param dest Destination image
     * @param startY Starting row of current batch
     * @param endY Ending row of current batch
     * @param x Current column being processed
     */
    private void processRowBatch(BufferedImage src, BufferedImage dest, 
                               int startY, int endY, int x) {
        // Base case: stop at image width boundary
        if (x >= src.getWidth() - 1) return;
        
        // Process current column in all rows of this batch
        processColumn(src, dest, x, startY, endY);
        
        // Process next column recursively
        processRowBatch(src, dest, startY, endY, x + 1);
    }

    /**
     * Processes a single column in the specified row range.
     * 
     * @param src Source image
     * @param dest Destination image
     * @param x Column to process
     * @param y Current row being processed
     * @param endY Last row to process in this column
     */
    private void processColumn(BufferedImage src, BufferedImage dest, 
                             int x, int y, int endY) {
        // Base case: stop when we've processed all rows in this column
        if (y > endY) return;
        
        // Calculate Sobel gradients
        int gx = calculateGradient(src, x, y, GX, -1, -1, 0, 0);
        int gy = calculateGradient(src, x, y, GY, -1, -1, 0, 0);
        
        // Calculate edge magnitude and constrain to [0,255]
        int magnitude = Math.min(255, (int) Math.sqrt(gx * gx + gy * gy));
        
        // Set output pixel (white edges on black background)
        dest.setRGB(x, y, new ARGB(255, magnitude, magnitude, magnitude).toInt());
        
        // Process next row in this column
        processColumn(src, dest, x, y + 1, endY);
    }

    /**
     * Recursively calculates the gradient value using the specified kernel.
     * 
     * @param img The source image
     * @param centerX X-coordinate of center pixel
     * @param centerY Y-coordinate of center pixel
     * @param kernel The Sobel kernel (GX or GY) to use
     * @param dx Current x-offset (-1, 0, or 1)
     * @param dy Current y-offset (-1, 0, or 1)
     * @param sum Accumulated sum of weighted neighbor values
     * @param count Number of valid neighbors processed
     * @return The calculated gradient value
     */
    private int calculateGradient(BufferedImage img, int centerX, int centerY, 
                                int[][] kernel, int dx, int dy, int sum, int count) {
        // Base case: finished processing 3x3 neighborhood
        if (dy > 1) return sum;
        
        // Move to next row when done with current row
        if (dx > 1) {
            return calculateGradient(img, centerX, centerY, kernel, -1, dy + 1, sum, count);
        }

        int nx = centerX + dx;
        int ny = centerY + dy;
        
        // Only process valid pixels within image bounds
        if (nx >= 0 && nx < img.getWidth() && ny >= 0 && ny < img.getHeight()) {
            ARGB pixel = new ARGB(img.getRGB(nx, ny));
            // Convert to grayscale and apply kernel weight
            int gray = (pixel.red + pixel.green + pixel.blue) / 3;
            return calculateGradient(img, centerX, centerY, kernel, dx + 1, dy, 
                                   sum + gray * kernel[dy + 1][dx + 1], count + 1);
        }
        
        // Skip out-of-bounds pixels
        return calculateGradient(img, centerX, centerY, kernel, dx + 1, dy, sum, count);
    }
}
