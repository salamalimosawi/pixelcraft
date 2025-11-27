# PixelCraft

A Java image processing library implementing common image manipulation algorithms.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-blue.svg?style=flat-square)

## About

PixelCraft is an educational image processing library that demonstrates core computer vision algorithms and object-oriented design patterns in Java. The project implements the Template Method pattern for a clean, extensible architecture.

## Features

- Grayscale and sepia tone color transformations
- Box blur and Sobel edge detection
- Geometric transformations (rotation, flipping)
- Creative effects (pixelation, glitch, inversion)
- Brightness adjustment
- Custom ARGB color manipulation

## Installation

```bash
git clone https://github.com/salamalimosawi/pixelcraft.git
cd pixelcraft
javac *.java
```

## Quick Start

```java
import java.io.IOException;

public class Example {
    public static void main(String[] args) throws IOException {
        Converter converter = new Grayscale();
        converter.convert("input.jpg", "output.png");
    }
}
```

## Usage

### Grayscale Conversion

```java
Converter grayscale = new Grayscale();
grayscale.convert("image.jpg", "grayscale.png");
```

### Sepia Tone

```java
Converter sepia = new Sepia();
sepia.convert("image.jpg", "sepia.png");
```

### Blur Effect

```java
Converter blur = new Blur();
blur.convert("image.jpg", "blurred.png");
```

### Edge Detection

```java
Converter edges = new EdgeDetection();
edges.convert("image.jpg", "edges.png");
```

### Other Effects

```java
// Brightness adjustment
new Brighten().convert("dark.jpg", "bright.png");

// 90-degree rotation
new Rotate().convert("portrait.jpg", "landscape.png");

// Horizontal flip
new FlipHorizontal().convert("image.jpg", "flipped.png");

// Pixelation effect
new Pixelate().convert("image.jpg", "pixelated.png");

// Color inversion
new Invert().convert("image.jpg", "inverted.png");

// Glitch effect
new Glitch().convert("image.jpg", "glitched.png");
```

## Architecture

The project uses the Template Method pattern. All converters extend the abstract `Converter` class:

```java
public abstract class Converter {
    public void convert(String inputFileName, String outputFileName) throws IOException {
        File inputFile = new File(inputFileName);
        BufferedImage img = ImageIO.read(inputFile);
        BufferedImage processedImg = processImage(img);
        File outputFile = new File(outputFileName);
        ImageIO.write(processedImg, "PNG", outputFile);
    }
    
    protected abstract BufferedImage processImage(BufferedImage img);
}
```

Subclasses implement `processImage()` to define specific transformations.

## Implementation Details

**Grayscale**: Simple averaging method (R+G+B)/3

**Sepia**: Standard sepia matrix transformation
- Red = 0.393R + 0.769G + 0.189B
- Green = 0.349R + 0.686G + 0.168B
- Blue = 0.272R + 0.534G + 0.131B

**Blur**: 5x5 box blur using neighborhood averaging

**Edge Detection**: Recursive Sobel operator with 3x3 kernels

**Brighten**: Additive brightness (+90 default factor)

**Rotate**: 90-degree clockwise transformation

**Flip**: Horizontal mirroring along vertical axis

**Pixelate**: Block averaging (10x10 default block size)

**Invert**: RGB channel inversion (255 - value)

**Glitch**: Random RGB channel shifting per scanline

## Project Structure

```
pixelcraft/
├── ARGB.java              # ARGB color model utility
├── Converter.java         # Abstract base class
├── Blur.java
├── Brighten.java
├── EdgeDetection.java
├── FlipHorizontal.java
├── Glitch.java
├── Grayscale.java
├── Invert.java
├── Pixelate.java
├── Rotate.java
├── Sepia.java
├── PixelCraft.java        # Main entry point
└── Image-Results/         # Output directory
```

## Technical Specifications

- **Language**: Java 8+
- **Dependencies**: None (uses standard Java libraries only)
- **Input Formats**: JPG, PNG, BMP, GIF
- **Output Format**: PNG (preserves transparency)
- **Image Processing**: java.awt.image.BufferedImage
- **I/O**: javax.imageio.ImageIO

## Algorithms

Several converters implement recursive algorithms:

- `EdgeDetection`: Batch-based row processing with recursive gradient calculation
- `FlipHorizontal`: Recursive pixel-by-pixel traversal
- `Invert`: Divide-and-conquer block processing (16x16 blocks)
- `Sepia`: Tail-recursive row processing

## Extending PixelCraft

Create a custom converter by extending the `Converter` class:

```java
import java.awt.image.BufferedImage;

public class CustomEffect extends Converter {
    @Override
    protected BufferedImage processImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Custom processing logic
                int pixel = img.getRGB(x, y);
                ARGB color = new ARGB(pixel);
                
                // Modify color values
                ARGB newColor = new ARGB(color.alpha, color.red, color.green, color.blue);
                result.setRGB(x, y, newColor.toInt());
            }
        }
        
        return result;
    }
}
```

## Known Limitations

- Single-threaded processing (no parallel optimization)
- Fixed parameters (blur radius, block size, brightness factor)
- Rotation limited to 90-degree clockwise only
- No GUI interface
- Output always PNG format

## Future Work

- Add configurable parameters for effects
- Implement multi-threading for large images
- Support for arbitrary rotation angles
- Additional filters (sharpen, emboss, Gaussian blur)
- Command-line argument parsing
- GUI interface

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Contributors
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/salamalimosawi">
        <img src="https://github.com/salamalimosawi.png" width="100px;" alt=""/>
        <br />
        <sub><b>Salam Mosawi</b></sub>
      </a>
      <br />
      <sub>Project Creator</sub>
    </td>
    <td align="center">
      <a href="https://github.com/felda-del">
        <img src="https://github.com/felda-del.png" width="100px;" alt=""/>
        <br />
        <sub><b>felda-del</b></sub>
      </a>
      <br />
      <sub>Contributor</sub>
    </td>
    <td align="center">
      <a href="https://github.com/mhdiss84">
        <img src="https://github.com/mhdiss84.png" width="100px;" alt=""/>
        <br />
        <sub><b>mhdiss84</b></sub>
      </a>
      <br />
      <sub>Contributor</sub>
    </td>
  </tr>
</table>
