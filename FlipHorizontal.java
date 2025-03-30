import java.awt.image.BufferedImage;

/**
 * The FlipHorizontal class is a subclass of the Converter class. It is
 * responsible for flipping an image horizontally. It uses BufferedImage to
 * manipulate the pixel values and create a new image with the flipped
 * dimensions.
 * 
 */
public class FlipHorizontal extends Converter {

	/**
	 * Processes the image by flipping horizontally.
	 *
	 * This method creates a new image where the position of the pixels are mirrored
	 * across the y-axis (flips image from left to right).
	 *
	 * @param img The BufferedImage object representing the input image to be
	 *            processed
	 * @return A new BufferedImage representing the horizontally flipped image
	 */

	@Override
	protected BufferedImage processImage(BufferedImage img) {
		// Get width and height of input image
		int width = img.getWidth();
		int height = img.getHeight();

		// Create a new horizontally flipped image with <width> and <height>, and
		// specify the type of the color value
		BufferedImage flippedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// Start the recursive process
		flipRowRecursive(img, flippedImg, width, height, 0);

		// Return the new flipped image
		return flippedImg;
	}

	/**
	 * Flips the image horizontally using recursion by mirroring pixels.
	 * 
	 * @param img        The original image
	 * @param flippedImg The image being processed
	 * @param x          The current x-coordinate being processed
	 * @param y          The current y-coordinate being processed
	 */

	private void flipRowRecursive(BufferedImage img, BufferedImage flippedImg, int width, int height, int y) {
		// Base case: stop when all rows are processed
		if (y >= height)
			return;
		flipPixelRecursive(img, flippedImg, width, y, 0);
		flipRowRecursive(img, flippedImg, width, height, y + 1);
	}

	/**
	 * Flips the pixels within a single row recursively.
	 *
	 * @param img        The original image.
	 * @param flippedImg The image being processed.
	 * @param width      The width of the image.
	 * @param y          The current row being processed.
	 * @param x          The current column being processed.
	 */

	private void flipPixelRecursive(BufferedImage img, BufferedImage flippedImg, int width, int y, int x) {
		// Base case: stop when all pixels in the row are processed
		if (x >= width)
			return;
		flippedImg.setRGB(width - 1 - x, y, img.getRGB(x, y));
		flipPixelRecursive(img, flippedImg, width, y, x + 1);
	}
}
