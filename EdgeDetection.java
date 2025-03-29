import java.awt.image.BufferedImage;

/**
 * Recursive Sobel edge detection implementation.
 * Outputs white edges on black background.
 */
public class EdgeDetection extends Converter {
    // Sobel operator kernels
    private static final int[][] GX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    private static final int[][] GY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    private static final int BATCH_SIZE = 10; // Rows per batch

    @Override
    protected BufferedImage processImage(BufferedImage img) {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        processRows(img, result, 1, Math.min(BATCH_SIZE, img.getHeight() - 2));
        return result;
    }

    // Processes image in batches of rows
    private void processRows(BufferedImage src, BufferedImage dest, int startY, int endY) {
        if (startY >= src.getHeight() - 1) return;
        processRowBatch(src, dest, startY, endY, 1);
        processRows(src, dest, endY + 1, Math.min(endY + BATCH_SIZE, src.getHeight() - 2));
    }

    // Processes columns within a row batch
    private void processRowBatch(BufferedImage src, BufferedImage dest, 
                               int startY, int endY, int x) {
        if (x >= src.getWidth() - 1) return;
        processColumn(src, dest, x, startY, endY);
        processRowBatch(src, dest, startY, endY, x + 1);
    }

    // Processes pixels in a column
    private void processColumn(BufferedImage src, BufferedImage dest, 
                             int x, int y, int endY) {
        if (y > endY) return;
        int gx = calculateGradient(src, x, y, GX, -1, -1, 0, 0);
        int gy = calculateGradient(src, x, y, GY, -1, -1, 0, 0);
        int magnitude = Math.min(255, (int) Math.sqrt(gx * gx + gy * gy));
        dest.setRGB(x, y, new ARGB(255, magnitude, magnitude, magnitude).toInt());
        processColumn(src, dest, x, y + 1, endY);
    }

    // Calculates gradient using 3x3 kernel
    private int calculateGradient(BufferedImage img, int centerX, int centerY, 
                                int[][] kernel, int dx, int dy, int sum, int count) {
        if (dy > 1) return sum;
        if (dx > 1) return calculateGradient(img, centerX, centerY, kernel, -1, dy + 1, sum, count);
        
        int nx = centerX + dx;
        int ny = centerY + dy;
        if (nx >= 0 && nx < img.getWidth() && ny >= 0 && ny < img.getHeight()) {
            ARGB pixel = new ARGB(img.getRGB(nx, ny));
            int gray = (pixel.red + pixel.green + pixel.blue) / 3;
            return calculateGradient(img, centerX, centerY, kernel, dx + 1, dy, 
                                   sum + gray * kernel[dy + 1][dx + 1], count + 1);
        }
        return calculateGradient(img, centerX, centerY, kernel, dx + 1, dy, sum, count);
    }
}
