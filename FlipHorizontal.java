import java.awt.image.BufferedImage;

/**
 * The FlipHorizontal class is a subclass of the Converter class. It is
 * responsible for flipping an image horizontally. It uses BufferedImage to
 * manipulate the pixel values and create a new image with the flipped
 * dimensions.
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
		BufferedImage flippedHorImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// Iterate through each pixel and flip it horizontally
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				// Get the RGB value of the current pixel at (x, y)
				int pixel = img.getRGB(x, y);

				// Set pixels at new mirrored position (flipped over vertical axis)
				flippedHorImg.setRGB(width - 1 - x, y, pixel);
			}
		}

		// Return the new horizontally flipped image
		return flippedHorImg;
	}
}
