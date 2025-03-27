import java.awt.image.BufferedImage;

public class Pixelate extends Converter {

    // The size of the blocks for pixelation
    private final int blockSize = 10;  // Default block size

    @Override
    protected void processImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        // Process each block of pixels
        for (int y = 0; y < height; y += blockSize) {
            for (int x = 0; x < width; x += blockSize) {
                // Calculate the average color for the block
                int avgRed = 0, avgGreen = 0, avgBlue = 0;
                int count = 0;

                // Loop through the block
                for (int by = y; by < Math.min(y + blockSize, height); by++) {
                    for (int bx = x; bx < Math.min(x + blockSize, width); bx++) {
                        int pixel = img.getRGB(bx, by);

                        ARGB argb = new ARGB(pixel);

                        avgRed += argb.red;
                        avgGreen += argb.green;
                        avgBlue += argb.blue;
                        count++;
                    }
                }

                // Calculate the average color
                avgRed /= count;
                avgGreen /= count;
                avgBlue /= count;

                // Set the average color for each pixel in the block
                for (int by = y; by < Math.min(y + blockSize, height); by++) {
                    for (int bx = x; bx < Math.min(x + blockSize, width); bx++) {
                        ARGB newArgb = new ARGB(255, avgRed, avgGreen, avgBlue); // alpha set to 255 (opaque)
                        img.setRGB(bx, by, newArgb.toInt());
                    }
                }
            }
        }
    }
}
