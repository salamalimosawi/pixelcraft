/**
 * Represents an ARGB (Alpha, Red, Green, Blue) color model.
 * This class provides utilities for extracting and encoding ARGB values
 * from/to a 32-bit integer representation. Each color channel is stored
 * as an 8-bit value (0-255).
 */
public class ARGB {
    /**
     * Alpha channel value (0-255).
     * 0 represents fully transparent, 255 represents fully opaque.
     */
    public int alpha;
    
    /**
     * Red channel value (0-255).
     */
    public int red;
    
    /**
     * Green channel value (0-255).
     */
    public int green;
    
    /**
     * Blue channel value (0-255).
     */
    public int blue;
    
    /**
     * Constructs an ARGB object by extracting color channels from a packed integer.
     * This constructor uses bit shifting and masking to extract the individual
     * ARGB components from a single 32-bit integer value.
     * 
     * @param pixel A 32-bit integer containing packed ARGB values
     */
    public ARGB(int pixel) {
        // Extract different bits from pixel that stores the ARGB values
        this.alpha = (pixel >> 24) & 0xff;
        this.red = (pixel >> 16) & 0xff;
        this.green = (pixel >> 8) & 0xff;
        this.blue = pixel & 0xff;
    }
    
    /**
     * Constructs an ARGB object with specified channel values.
     * All values should be in the range 0-255. Values outside this range
     * will not be validated but may produce unexpected results.
     * 
     * @param a Alpha channel value (0-255)
     * @param r Red channel value (0-255)
     * @param g Green channel value (0-255)
     * @param b Blue channel value (0-255)
     */
    public ARGB(int a, int r, int g, int b) {
        this.alpha = a;
        this.red = r;
        this.green = g;
        this.blue = b;
    }
    
    /**
     * Encodes the ARGB values into a single 32-bit integer.
     * This method packs the four color channels into a single integer
     * using bit shifting, suitable for use with BufferedImage.setRGB().
     * 
     * @return A 32-bit integer with packed ARGB values
     */
    public int toInt() {
        // Encode the ARGB values into a single integer
        return (this.alpha << 24) | (this.red << 16) | (this.green << 8) | blue;
    }
}
