import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Abstract base class for image conversion and processing operations.
 * This class provides a template for image processing workflows using the
 * Template Method design pattern. Subclasses must implement the
 * method to define specific transformations.
 */
public abstract class Converter {
    
    /**
     * Converts an input image file to an output image file with processing.
     * This method reads the input file, applies the transformation defined in
     * and saves the result in PNG format.
     * 
     * @param inputFileName  The path to the input image file
     * @param outputFileName The path where the processed image will be saved
     * @throws IOException If an error occurs during file I/O operations
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
     * Processes the input image and returns the transformed result.
     * This abstract method must be implemented by all subclasses to define
     * the specific image transformation to be applied.
     * 
     * @param img The input BufferedImage to be processed
     * @return A new BufferedImage representing the processed result
     */
    protected abstract BufferedImage processImage(BufferedImage img);
}
