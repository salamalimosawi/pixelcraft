import java.awt.image.BufferedImage;

/**
 * The Grayscale class is a subclass of the Converter class. It is responsible
 * for converting a given image to grayscale. It uses BufferedImage to
 * manipulate pixel values and creates a new image with the grayscale version of
 * the input image.
 */

public class Grayscale extends Converter {

	/**
	 * Processes the input image and converts it to grayscale.
	 * 
	 * This method calculates the grayscale value for each pixel by averaging the
	 * red, green, and blue components. It then assigns the equivalent grayscale
	 * value to the RGB values while retaining the original alpha value.
	 * 
	 * @param img A BufferedImage representing the input image to be processed
	 * @return A new BufferedImage representing the grayscale image
	 */

	@Override
	public BufferedImage processImage(BufferedImage img) {

		// Get width and height of input image
		int width = img.getWidth();
		int height = img.getHeight();

		// Create a new grayscale image with the same <width> and <height>
		BufferedImage processedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// Iterate over each pixel to convert it to grayscale
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				int rgb = img.getRGB(x, y);
				ARGB color = new ARGB(rgb);

				int grayLevel = (color.red + color.green + color.blue) / 3;
				ARGB grayColor = new ARGB(color.alpha, grayLevel, grayLevel, grayLevel);

				processedImg.setRGB(x, y, grayColor.toInt());
			}
		}
		return processedImg;
	}
}
