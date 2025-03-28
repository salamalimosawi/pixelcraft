import java.awt.image.BufferedImage;

/**
 * The Brighten class is a subclass of the Converter class. It is responsible
 * for increasing the brightness of an image by a given factor. The class
 * iterates over all pixels in the image, extracts the RGB values, and adjusts
 * the brightness of each pixel while ensuring the new RGB values stay within
 * the valid range of [0, 255].
 */

public class Brighten extends Converter {

	// Brightness factor to increase each pixel's RGB values
	private int brightnessFactor = 90;

	/**
	 * Processes the image by adjusting the brightness of each pixel's color.
	 * 
	 * This method produces a new image with each pixel's red, green, and blue
	 * components increased by a brightness factor. The new values are restricted at
	 * 255 to ensure they stay within the valid RGB range.
	 * 
	 * @param img The BufferedImage object representing the image to be processed
	 * @return A new BufferedImage with the adjusted brightness
	 */

	@Override
	protected BufferedImage processImage(BufferedImage img) {

		// Get width and height of input image
		int width = img.getWidth();
		int height = img.getHeight();

		// Create a new image with same <width> and <height>
		BufferedImage processedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// Iterate over each pixel in the image by going through rows and columns
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				// Get the RGB value of the current pixel at (x, y)
				int pixel = img.getRGB(x, y);

				// Create an ARGB object to extract the individual components
				ARGB argb = new ARGB(pixel);

				// Increase the RGB values by the brightness factor (restrict at 255)
				int newRed = Math.min(255, argb.red + brightnessFactor);
				int newGreen = Math.min(255, argb.green + brightnessFactor);
				int newBlue = Math.min(255, argb.blue + brightnessFactor);

				// Create a new ARGB object with the adjusted RGB values
				ARGB newARGB = new ARGB(argb.alpha, newRed, newGreen, newBlue);

				// Set the new RGB value for the pixel at (x, y) in the image
				processedImg.setRGB(x, y, newARGB.toInt());
			}
		}

		// Return the processed image with adjusted brightness
		return processedImg;
	}
}
