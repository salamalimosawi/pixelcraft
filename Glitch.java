import java.awt.image.BufferedImage;
import java.util.Random;

public class Glitch extends Converter {
	private final Random rand = new Random();

	@Override
	protected BufferedImage processImage(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();

		// Create a new buffered image with <width> and <height>, and specify the type
		// of
		// the color value
		BufferedImage processedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		int shiftAmount = rand.nextInt(10) + 1; // Random shift between 1 and 10 pixels

		for (int y = 0; y < height; y++) {
			int rShift = rand.nextInt(shiftAmount) - shiftAmount / 2;
			int gShift = rand.nextInt(shiftAmount) - shiftAmount / 2;
			int bShift = rand.nextInt(shiftAmount) - shiftAmount / 2;

			for (int x = 0; x < width; x++) {
				int newX_R = Math.min(Math.max(x + rShift, 0), width - 1);
				int newX_G = Math.min(Math.max(x + gShift, 0), width - 1);
				int newX_B = Math.min(Math.max(x + bShift, 0), width - 1);

				ARGB original = new ARGB(img.getRGB(x, y));
				int r = new ARGB(img.getRGB(newX_R, y)).red;
				int g = new ARGB(img.getRGB(newX_G, y)).green;
				int b = new ARGB(img.getRGB(newX_B, y)).blue;

				ARGB newColor = new ARGB(original.alpha, r, g, b);
				processedImg.setRGB(x, y, newColor.toInt());
			}
		}

		return processedImg;
	}
}
