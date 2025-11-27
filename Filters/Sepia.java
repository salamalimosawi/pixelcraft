import java.awt.image.BufferedImage;

/**
 * Applies a sepia tone filter to images for a vintage photographic effect.
 * This class transforms colored images into warm, brown-toned images reminiscent
 * of old photographs. The sepia effect is achieved by applying a weighted
 * transformation matrix to the RGB values of each pixel using industry-standard
 * coefficients that simulate the chemical process of sepia toning.
 */
public class Sepia extends Converter {
    
    /**
     * Processes the image by applying a sepia tone filter.
     * This method processes the image row by row using recursion. It applies the
     * sepia filter to each pixel by transforming the RGB values using preset
     * coefficients. The alpha channel is preserved, and RGB values are clamped
     * at 255 to prevent overflow.
     * 
     * @param img The BufferedImage object representing the image to be processed
     * @return A new BufferedImage with the sepia filter applied, giving it a
     *         warm, vintage brown tone
     */
    @Override
    protected BufferedImage processImage(BufferedImage img) {
        // Get width and height of input image
        int width = img.getWidth();
        int height = img.getHeight();
        
        // Create a new image with the same dimensions
        BufferedImage processedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // Start recursion by processing row by row
        sepiaRecursive(img, processedImg, 0);
        
        // Return the processed image
        return processedImg;
    }
    
    /**
     * Recursively applies sepia filter row by row.
     * For each pixel in the current row, this method calculates new RGB values
     * using the sepia transformation matrix. The calculations are based on the
     * standard sepia tone formula used in image processing.
     * 
     * @param img The original image to read pixels from
     * @param processedImg The destination image to write sepia-toned pixels to
     * @param y The current row being processed
     */
    private void sepiaRecursive(BufferedImage img, BufferedImage processedImg, int y) {
        int width = img.getWidth();
        int height = img.getHeight();
        
        // Base case: Stop when all rows are processed
        if (y >= height)
            return;
        
        // Process the whole row at once
        for (int x = 0; x < width; x++) {
            int pixel = img.getRGB(x, y);
            ARGB argb = new ARGB(pixel);
            
            int newRed = Math.min(255, (int) (0.393 * argb.red + 0.769 * argb.green + 0.189 * argb.blue));
            int newGreen = Math.min(255, (int) (0.349 * argb.red + 0.686 * argb.green + 0.168 * argb.blue));
            int newBlue = Math.min(255, (int) (0.272 * argb.red + 0.534 * argb.green + 0.131 * argb.blue));
            
            processedImg.setRGB(x, y, new ARGB(argb.alpha, newRed, newGreen, newBlue).toInt());
        }
        
        // Recursive call for the next row
        sepiaRecursive(img, processedImg, y + 1);
    }
}
