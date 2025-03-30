import java.awt.image.BufferedImage;

/**
 * The Brighten class is a child of the Converter class. 
 * Its job is to make an image brighter by a certain amount. 
 * It goes through every pixel, gets the RGB values, increases 
 * their brightness, and makes sure they don’t go over the 
 * allowed range of 0 to 255.
 */

public class Brighten extends Converter {

	// Brightness factor to increase each pixel's RGB values
	private int brightnessFactor = 90;

	/**
	 * This method adjusts the brightness of each pixel in the image. 
  	 * It creates a new image where the red, green, and blue values of each pixel 
    	 * are increased by a brightness factor. If any value goes over 255, it’s 
      	 * capped to stay within the valid RGB range.
  	 * @param img – The image to be processed
    	 * @return A new image with adjusted brightness
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
