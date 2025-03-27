import java.awt.image.BufferedImage;

public class Invert extends Converter {

    @Override
    protected void processImage(BufferedImage img) {
        invertImageIteratively(img);
    }

    // Process the image using iteration instead of recursion
    private void invertImageIteratively(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Process the current pixel (x, y)
                int pixel = img.getRGB(x, y);
                ARGB argb = new ARGB(pixel);

                // Invert the color by subtracting each color from 255
                int invertedRed = 255 - argb.red;
                int invertedGreen = 255 - argb.green;
                int invertedBlue = 255 - argb.blue;

                // Create a new ARGB with the inverted color
                ARGB invertedARGB = new ARGB(argb.alpha, invertedRed, invertedGreen, invertedBlue);

                // Set the inverted pixel
                img.setRGB(x, y, invertedARGB.toInt());
            }
        }
    }
}