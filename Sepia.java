import java.awt.image.BufferedImage;

/**
 * The Sepia class is a subclass of the Converter class. It is responsible for
 * applying a sepia filter to an image by adjusting the RGB values of each
 * pixel. It uses specific coefficients for red, green, and blue that gives the
 * pixels a warm, brown tone. It uses BufferedImage to manipulate the pixel
 * values and create a new image with the sepia effect.
 */

public class Sepia extends Converter {

	/**
	 * Processes the image by applying a sepia filter to it.
	 * 
	 * This method processes the image row by row using recursion. It applies the
	 * sepia filter to each pixel by adjusting the RBG values using preset
	 * coefficients.
	 * 
	 * @param img The BufferedImage object representing the image to be processed.
	 * @return A new BufferedImage with the sepia filter applied.
	 */

	@Override
	protected BufferedImage processImage(BufferedImage img) {

		// Get width and height of input image
		int width = img.getWidth();
		int height = img.getHeight();

		// Create a new image with <width> and <height>, and specify the type of
		// the color value
		BufferedImage processedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// Start recursion by processing row by row
		sepiaRecursive(img, processedImg, 0);

		// Return the processed image
		return processedImg;
	}

	/**
	 * Applies sepia filter through recursion by row.
	 * 
	 * @param img          The original image
	 * @param processedImg The image being processed
	 * @param y            The row being currently processed
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