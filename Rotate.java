import java.awt.image.BufferedImage;

/**
 * The Rotate class is a subclass of the Converter class. It is responsible for
 * rotating a given image by 90 degrees in a clockwise direction. It uses
 * BufferedImage to manipulate the pixel values and create a new image with the
 * rotated dimensions.
 */

public class Rotate extends Converter {

	/**
	 * Processes the image by rotating it 90 degrees in a clockwise direction.
	 * 
	 * This method produces a new image with the height and width swapped for
	 * rotation. The pixels from the input image are assigned to new positions in
	 * the processed rotated image.
	 * 
	 * @param img A BufferedImage representing the input image to be processed
	 * @return A new BufferedImage representing the rotated image
	 */

	@Override
	protected BufferedImage processImage(BufferedImage img) {

		// Get width and height of input image
		int width = img.getWidth();
		int height = img.getHeight();

		// Create a new rotated image with the <width> and <height> swapped, and
		// specifies
		// the type of the color value
		BufferedImage rotatedImg = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

		// Iterate through each pixel of the input image
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				// Get the RGB value of the current pixel at (x, y)
				int pixel = img.getRGB(x, y);

				// Calculate the new pixel positions for clockwise 90 degree rotation
				rotatedImg.setRGB(height - 1 - y, x, pixel);
			}
		}

		// Return the new rotated image
		return rotatedImg;
	}
}