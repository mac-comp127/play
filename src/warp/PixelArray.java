package warp;

import edu.macalester.graphics.Image;

public class PixelArray {
    private final int width, height;
    private final int[] pixels;
    
    public PixelArray(Image image) {
        this(
            image.getImageWidth(),
            image.getImageHeight(),
            image.toIntArray()
        );
    }

    public PixelArray(int width, int height) {
        this(width, height, new int[width * height]);
    }

    private PixelArray(int width, int height, int[] pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPixel(int x, int y) {
        if (outOfBounds(x, y)) {
            return 0;
        }
        return pixels[pixelIndex(x, y)];
    }

    public void setPixel(int x, int y, int value) {
        if (!outOfBounds(x, y)) {
            pixels[pixelIndex(x, y)] = value;
        }
    }

    private boolean outOfBounds(int x, int y) {
        return x < 0 || x >= width || y < 0 || y >= height;
    }

    private int pixelIndex(int x, int y) {
        return x + y * width;
    }

    public Image toImage() {
        return new Image(width, height, pixels);
    }
}
