import java.awt.image.BufferedImage;

/**
 * The Blur class is a subclass of the Converter class. It is responsible for
 * applying a blur effect to a given image, giving it a softened appearance. It
 * uses a recursive approach to apply the blur filter iteratively, to
 * progressively smaller sections of the image.
 */

public class Blur extends Converter {

	/**
	 * Processes the image by applying a recursive blur effect.
	 * 
	 * @param img A BufferedImage representing the input image to be processed
	 * @return A new BufferedImage with the blur effect applied
	 */

	@Override
	protected BufferedImage processImage(BufferedImage img) {

		// Get width and height of input image
		int width = img.getWidth();
		int height = img.getHeight();

		// Create a new blurred image with <width> and <height>, and specifies the type
		// of the color value
		BufferedImage processedImg = new BufferedImage(width, height, img.getType());

		// Apply the recursive blur
		applyRecursiveBlur(img, processedImg, 0, 0, width, height);

		return processedImg;

	}

	private void applyRecursiveBlur(BufferedImage img, BufferedImage temp, int x, int y, int width, int height) {
		if (width <= 2 || height <= 2) {
			return;
		}

		applyBoxBlur(img, temp, x, y, width, height);

		copyRegion(temp, img, x, y, width, height);

		int halfWidth = width / 2;
		int halfHeight = height / 2;

		applyRecursiveBlur(img, temp, x, y, halfWidth, halfHeight);
		applyRecursiveBlur(img, temp, x + halfWidth, y, halfWidth, halfHeight);
		applyRecursiveBlur(img, temp, x, y + halfHeight, halfWidth, halfHeight);
		applyRecursiveBlur(img, temp, x + halfWidth, y + halfHeight, halfWidth, halfHeight);
	}

	private void applyBoxBlur(BufferedImage img, BufferedImage temp, int x, int y, int width, int height) {
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				if (i >= img.getWidth() || j >= img.getHeight()) {
					continue;
				}

				int sumAlpha = 0, sumRed = 0, sumGreen = 0, sumBlue = 0;
				int count = 0;

				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						int nx = i + dx;
						int ny = j + dy;

						if (nx >= 0 && nx < img.getWidth() && ny >= 0 && ny < img.getHeight()) {
							ARGB pixel = new ARGB(img.getRGB(nx, ny));
							sumAlpha += pixel.alpha;
							sumRed += pixel.red;
							sumGreen += pixel.green;
							sumBlue += pixel.blue;
							count++;
						}
					}
				}

				ARGB blurredPixel = new ARGB(sumAlpha / count, sumRed / count, sumGreen / count, sumBlue / count);

				temp.setRGB(i, j, blurredPixel.toInt());
			}
		}
	}

	private void copyRegion(BufferedImage src, BufferedImage dest, int x, int y, int width, int height) {
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				if (i < dest.getWidth() && j < dest.getHeight()) {
					dest.setRGB(i, j, src.getRGB(i, j));
				}
			}
		}
	}
}
