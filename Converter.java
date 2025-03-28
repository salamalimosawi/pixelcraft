import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * The Converter class is an abstract class that provides methods
 * for converting and processing images.
 */
public abstract class Converter {

	/**
	 * Converts the input image to a new output image.
	 * 
	 * This method reads the input image, applies a transformation to it based on the subclasses 
	 * implemented through the abstract processImage method, and then saves the processed image
	 * to the specified output file.
	 * 
	 * @param inputFileName  The name of the input file to read from
	 * @param outputFileName The name of the output file to save to
	 * @throws IOException If an error occurs during reading or writing the image file
	 */
	public void convert(String inputFileName, String outputFileName) throws IOException {

		// Read the input image file
		File inputFile = new File(inputFileName);
		BufferedImage img = ImageIO.read(inputFile);

		BufferedImage processedImg = processImage(img);

		// Save the output image to a file
		File outputFile = new File(outputFileName);
		ImageIO.write(processedImg, "PNG", outputFile);
	}

	/**
	 * Abstract method to process the image.
	 * 
	 * This method must be implemented by subclasses in order to define how the image should
	 * be transformed (e.g., rotating, applying filters).
	 * 
	 * @param img The image to be processed
	 * @return A new BufferedImage that represents the processed image
	 */
	protected abstract BufferedImage processImage(BufferedImage img);
}
