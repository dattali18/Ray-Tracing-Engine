package renderer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import primitives.Color;

public class ImageWriterTests {
    @Test
    @DisplayName("testing a blank image")
    void testingBlankImage() {
        ImageWriter imageWriter = new ImageWriter("blankImage", 800, 500);

        Color yellow = new Color(255, 255, 0);
        Color red = new Color(255, 0, 0);

        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if(i % 50 == 0 || j % 50 == 0)
                {
                    imageWriter.writePixel(i, j ,red);
                } else {
                    imageWriter.writePixel(i, j ,yellow);
                }

            }
        }

        imageWriter.writeToImage();

    }
}
