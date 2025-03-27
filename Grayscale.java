import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Grayscale extends Converter {
    @Override
    public void convert(String inputFile, String outputFile) throws IOException {
        File input = new File(inputFile);
        BufferedImage img = ImageIO.read(input);

        if (img == null) {
            throw new IOException("Failed to read image file: " + inputFile);
        }

        processImage(img);

        File output = new File(outputFile);
        ImageIO.write(img, "PNG", output);
    }

    @Override
    public void processImage(BufferedImage img) {
        for (int x = 0; x < img.getWidth(); ++x) {
            for (int y = 0; y < img.getHeight(); ++y) {
                int rgb = img.getRGB(x, y);
                ARGB color = new ARGB(rgb);

                int grayLevel = (color.red + color.green + color.blue) / 3;
                ARGB grayColor = new ARGB(color.alpha, grayLevel, grayLevel, grayLevel);

                img.setRGB(x, y, grayColor.toInt());
            }
        }
    }
}

