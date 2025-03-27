import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public abstract class Converter {

    public void convert(String inputFileName, String outputFileName) throws IOException {
        
        BufferedImage img = ImageIO.read(new File(inputFileName));
        
        processImage(img);
        
        ImageIO.write(img, "PNG", new File(outputFileName));
    }
    protected abstract void processImage(BufferedImage img);
}

