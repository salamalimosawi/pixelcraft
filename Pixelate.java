import java.awt.image.BufferedImage;

/**
 * The Pixelate class is a subclass of the Converter class. It is responsible
 * for pixelating an image by dividing the image into blocks and replacing each
 * block with the average color of the pixels within that block.
 */

public class Pixelate extends Converter {

	// The size of the blocks for pixelation (default block size is 10)
	private final int blockSize = 10;

	/**
	 * Processes the image by pixelating it.
	 * 
	 * The method divides the image into blocks of pixels, and each block is
	 * replaced by the average color of the pixels in that block.
	 * 
	 * @param img The BufferedImage object representing the image to be processed
	 * @return A new BufferedImage representing the pixelated image
	 */

	@Override
	protected BufferedImage processImage(BufferedImage img) {
		// Get the width and height of the image
		int width = img.getWidth();
		int height = img.getHeight();

		// Create a new image with the same <width> and <height>, and specify the type
		// of
		// the color value
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
						// Create a new ARGB object with the average color and alpha set to 255 (opaque)
						ARGB newArgb = new ARGB(255, avgRed, avgGreen, avgBlue);
						processedImg.setRGB(bx, by, newArgb.toInt());
					}
				}
			}
		}
		return processedImg;
	}
}
