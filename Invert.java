import java.awt.image.BufferedImage;

/**
 * The Invert class extends the Converter class and flips the colors of an image.
 * It does this by subtracting each RGB value (red, green, blue) from 255,
 * turning each color into its opposite (complementary color).
 */

public class Invert extends Converter {

	/**
	 * Processes the image by inverting the colors of all pixels.
	 * 
	 * @param img The BufferedImage object representing the image to be processed.
	 */

	@Override
	protected BufferedImage processImage(BufferedImage img) {
		// Call the method to invert the image colors iteratively
		return invertImageIteratively(img);
	}

	/**
	 * Iteratively inverts the color of each pixel in the image. For each pixel, the
	 * RGB components are inverted by subtracting each component (red, green, blue)
	 * from 255.
	 * 
	 * @param img The BufferedImage object representing the image to be processed.
	 */

	private BufferedImage invertImageIteratively(BufferedImage img) {
		// Get the width and height of the image
		int width = img.getWidth();
		int height = img.getHeight();

		// Create a new inverted image with the same <width> and <height>, and specify
		// the type of
		// the color value
		BufferedImage processedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// Iterate through each pixel (x, y) in the image
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Get the RGB value of the current pixel
				int pixel = img.getRGB(x, y);

				// Create an ARGB object to extract the individual color components (alpha, red,
				// green, blue)
				ARGB argb = new ARGB(pixel);

				// Invert the red, green, and blue components by subtracting each from 255
				int invertedRed = 255 - argb.red;
				int invertedGreen = 255 - argb.green;
				int invertedBlue = 255 - argb.blue;

				// Create a new ARGB object with the inverted colors
				ARGB invertedARGB = new ARGB(argb.alpha, invertedRed, invertedGreen, invertedBlue);

				// Set the new inverted pixel value at (x, y) in the image
				processedImg.setRGB(x, y, invertedARGB.toInt());
			}
		}
		return processedImg;
	}
}
