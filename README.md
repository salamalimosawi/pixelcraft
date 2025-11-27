# PixelCraft 🎨

A powerful Java-based image manipulation tool that provides various converters and effects for image processing.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Getting Started](#getting-started)
- [Usage Examples](#usage-examples)
- [Available Effects](#available-effects)
- [Project Structure](#project-structure)
- [Technical Implementation](#technical-implementation)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)
- [Contributors](#contributors)

## 🌟 Overview

PixelCraft is a comprehensive image processing library built in Java that demonstrates advanced object-oriented programming principles and image manipulation techniques. The project features a modular converter architecture using the Template Method design pattern, allowing for easy extensibility and maintenance.

**Key Highlights:**
- 🎯 10+ image processing effects
- 🔄 Recursive algorithm implementations
- 🏗️ Clean architecture with Template Method pattern
- 📦 Zero external dependencies (pure Java)
- 📝 Comprehensive API documentation

## ✨ Features

### Color Transformations
- **Grayscale Conversion**: Convert images to monochrome using pixel averaging
- **Sepia Tone**: Apply vintage brown-toned photographic effects
- **Color Inversion**: Create negative/inverted color effects
- **ARGB Manipulation**: Direct control over alpha, red, green, and blue channels

### Image Effects
- **Blur**: Apply 5x5 box blur for smooth effects
- **Brightness Adjustment**: Dynamically increase image brightness
- **Edge Detection**: Sobel operator-based edge detection with recursive implementation
- **Glitch Effect**: Digital distortion through RGB channel shifting
- **Pixelation**: Create retro-style mosaic effects

### Geometric Transformations
- **90° Rotation**: Clockwise image rotation
- **Horizontal Flip**: Mirror images along vertical axis

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK) 8 or higher**
- An IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)
- Basic knowledge of Java and image processing concepts

### Installation

1. **Clone the repository:**
```bash
git clone https://github.com/salamalimosawi/pixelcraft.git
cd pixelcraft
```

2. **Compile the project:**
```bash
javac *.java
```

3. **Prepare your images:**
   - Place input images in the project directory
   - Ensure the `Image-Results/` folder exists for output

4. **Run the application:**
```bash
java PixelCraft
```

## 💡 Usage Examples

### Basic Grayscale Conversion

```java
import java.io.IOException;

public class Example {
    public static void main(String[] args) {
        try {
            // Create a grayscale converter
            Converter grayscale = new Grayscale();
            
            // Convert the image
            grayscale.convert("input.jpg", "Image-Results/output_grayscale.png");
            
            System.out.println("Conversion successful!");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

### Applying Multiple Effects

```java
public class ChainedEffects {
    public static void main(String[] args) throws IOException {
        // Apply blur
        Converter blur = new Blur();
        blur.convert("original.jpg", "step1_blurred.png");
        
        // Apply sepia tone
        Converter sepia = new Sepia();
        sepia.convert("step1_blurred.png", "step2_sepia.png");
        
        // Brighten the image
        Converter brighten = new Brighten();
        brighten.convert("step2_sepia.png", "final_result.png");
    }
}
```

### Custom Effect Implementation

```java
import java.awt.image.BufferedImage;

public class CustomEffect extends Converter {
    @Override
    protected BufferedImage processImage(BufferedImage img) {
        // Your custom image processing logic here
        BufferedImage result = new BufferedImage(
            img.getWidth(), 
            img.getHeight(), 
            BufferedImage.TYPE_INT_ARGB
        );
        
        // Process pixels...
        
        return result;
    }
}
```

### Working with ARGB Values

```java
// Extract color components from a pixel
int pixel = image.getRGB(x, y);
ARGB color = new ARGB(pixel);

System.out.println("Red: " + color.red);
System.out.println("Green: " + color.green);
System.out.println("Blue: " + color.blue);
System.out.println("Alpha: " + color.alpha);

// Create a new color
ARGB newColor = new ARGB(255, 100, 150, 200); // ARGB values
int packedColor = newColor.toInt();
image.setRGB(x, y, packedColor);
```

## 🎨 Available Effects

### 1. **Grayscale** (`Grayscale.java`)
Converts images to monochrome using simple averaging method.

**Formula:** `Gray = (R + G + B) / 3`

**Usage:**
```java
Converter converter = new Grayscale();
converter.convert("color.jpg", "grayscale.png");
```

---

### 2. **Sepia Tone** (`Sepia.java`)
Applies a warm, vintage brown tone reminiscent of old photographs.

**Transformation Matrix:**
- Red = 0.393R + 0.769G + 0.189B
- Green = 0.349R + 0.686G + 0.168B
- Blue = 0.272R + 0.534G + 0.131B

**Usage:**
```java
Converter converter = new Sepia();
converter.convert("photo.jpg", "vintage.png");
```

---

### 3. **Blur** (`Blur.java`)
Applies a 5x5 box blur effect for smooth, soft images.

**Method:** Averages pixels in a 5x5 neighborhood  
**Preserves:** Alpha channel (transparency)

**Usage:**
```java
Converter converter = new Blur();
converter.convert("sharp.jpg", "blurred.png");
```

---

### 4. **Brighten** (`Brighten.java`)
Increases image brightness by adding a constant value to each RGB channel.

**Default Factor:** +90  
**Method:** Additive brightening with clamping at 255

**Usage:**
```java
Converter converter = new Brighten();
converter.convert("dark.jpg", "bright.png");
```

---

### 5. **Edge Detection** (`EdgeDetection.java`)
Detects edges using the Sobel operator with recursive implementation.

**Algorithm:** Sobel gradient calculation  
**Output:** White edges on black background

**Usage:**
```java
Converter converter = new EdgeDetection();
converter.convert("photo.jpg", "edges.png");
```

---

### 6. **Horizontal Flip** (`FlipHorizontal.java`)
Mirrors the image along the vertical axis (left ↔ right).

**Implementation:** Fully recursive pixel remapping

**Usage:**
```java
Converter converter = new FlipHorizontal();
converter.convert("original.jpg", "flipped.png");
```

---

### 7. **Glitch Effect** (`Glitch.java`)
Creates digital distortion by randomly shifting RGB channels.

**Effect:** Chromatic aberration and VHS-style corruption  
**Randomization:** 1-10 pixel shifts per scanline

**Usage:**
```java
Converter converter = new Glitch();
converter.convert("photo.jpg", "glitched.png");
```

---

### 8. **Color Inversion** (`Invert.java`)
Creates a photographic negative by inverting all RGB values.

**Formula:** `newValue = 255 - oldValue`  
**Implementation:** Recursive block-based processing

**Usage:**
```java
Converter converter = new Invert();
converter.convert("photo.jpg", "negative.png");
```

---

### 9. **Pixelation** (`Pixelate.java`)
Creates a retro, low-resolution mosaic effect.

**Block Size:** 10×10 pixels (default)  
**Method:** Average color per block

**Usage:**
```java
Converter converter = new Pixelate();
converter.convert("hires.jpg", "pixelated.png");
```

---

### 10. **90° Rotation** (`Rotate.java`)
Rotates the image 90 degrees clockwise.

**Transformation:** (x, y) → (height - 1 - y, x)  
**Output Dimensions:** Width and height swapped

**Usage:**
```java
Converter converter = new Rotate();
converter.convert("landscape.jpg", "portrait.png");
```

## 📁 Project Structure

```
pixelcraft/
├── ARGB.java              # Color model for ARGB pixel manipulation
├── Converter.java         # Abstract base class (Template Method pattern)
├── Blur.java              # 5x5 box blur implementation
├── Brighten.java          # Additive brightness adjustment
├── EdgeDetection.java     # Recursive Sobel edge detection
├── FlipHorizontal.java    # Horizontal image mirroring
├── Glitch.java            # RGB channel shift effect
├── Grayscale.java         # Color to monochrome conversion
├── Invert.java            # Color inversion (negative)
├── Pixelate.java          # Block-based pixelation effect
├── Rotate.java            # 90° clockwise rotation
├── Sepia.java             # Vintage sepia tone filter
├── PixelCraft.java        # Main application entry point
├── Image-Results/         # Output directory for processed images
└── README.md              # This file
```

## 🛠️ Technical Implementation

### Design Patterns

**Template Method Pattern:**  
The `Converter` abstract class defines the skeleton of the image processing algorithm, with subclasses implementing specific transformations via the `processImage()` method.

```java
public abstract class Converter {
    public void convert(String input, String output) throws IOException {
        BufferedImage img = ImageIO.read(new File(input));
        BufferedImage processed = processImage(img);  // Template method
        ImageIO.write(processed, "PNG", new File(output));
    }
    
    protected abstract BufferedImage processImage(BufferedImage img);
}
```

### Key Technologies

| Component | Technology |
|-----------|-----------|
| **Language** | Java 8+ |
| **Image I/O** | javax.imageio.ImageIO |
| **Graphics** | java.awt.image.BufferedImage |
| **Color Model** | Custom ARGB class with bit manipulation |
| **Architecture** | Object-oriented with inheritance |

### Recursive Algorithms

Several converters demonstrate advanced recursive techniques:

- **EdgeDetection**: Row-batch recursion with gradient calculation
- **FlipHorizontal**: Row-by-row, pixel-by-pixel recursion
- **Invert**: Divide-and-conquer block processing
- **Sepia**: Simple row-based tail recursion

### Image Format Support

| Format | Input | Output |
|--------|-------|--------|
| **PNG** | ✅ | ✅ (Default) |
| **JPEG/JPG** | ✅ | ❌ |
| **BMP** | ✅ | ❌ |
| **GIF** | ✅ | ❌ |

*Output is always PNG to preserve quality and transparency.*

## 🔮 Future Enhancements

### Planned Features
- [ ] **GUI Interface**: JavaFX/Swing-based visual editor
- [ ] **Batch Processing**: Process multiple images at once
- [ ] **CLI Arguments**: Command-line parameter support
- [ ] **Additional Filters**:
  - Sharpen effect
  - Emboss effect
  - Oil painting style
  - Gaussian blur (vs. box blur)
- [ ] **Advanced Transformations**:
  - Arbitrary angle rotation
  - Scaling/resizing
  - Cropping utilities
- [ ] **Performance Optimizations**:
  - Multi-threading for large images
  - GPU acceleration research
- [ ] **File Format Expansion**:
  - GIF animation support
  - TIFF support
  - WebP support
- [ ] **Undo/Redo System**: History management for effects
- [ ] **Effect Presets**: Pre-configured effect chains
- [ ] **Real-time Preview**: Live effect preview before saving

### Technical Improvements
- [ ] Unit testing with JUnit
- [ ] JavaDoc generation automation
- [ ] Maven/Gradle build system
- [ ] Continuous Integration (CI/CD)
- [ ] Performance benchmarking suite

## 🤝 Contributing

Contributions are welcome! Here's how you can help improve PixelCraft:

### How to Contribute

1. **Fork the repository**
2. **Create a feature branch:**
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. **Make your changes and commit:**
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. **Push to the branch:**
   ```bash
   git push origin feature/AmazingFeature
   ```
5. **Open a Pull Request**

### Contribution Guidelines

- Follow existing code style and conventions
- Add JavaDoc comments to all public methods
- Test your changes thoroughly
- Update README if adding new features
- Keep commits focused and well-described

### Areas for Contribution

- 🐛 Bug fixes and issue resolution
- ✨ New image effect implementations
- 📚 Documentation improvements
- 🎨 GUI development
- ⚡ Performance optimizations
- 🧪 Test coverage expansion

## 👥 Contributors

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
