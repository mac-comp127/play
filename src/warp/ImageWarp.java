package warp;

import java.awt.Color;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;
import transform.PointTransform;
import transform.Ripple;
import transform.Ultraripple;
import transform.Rotate;
import transform.Squinch;
import transform.Warble;

public class ImageWarp {
    private static double z = 0;

    public static void main(String[] args) {
        PixelArray input = new PixelArray(new Image("images/coo.jpeg"));

        int outSize = 768;
        PixelArray output = new PixelArray(outSize, outSize);

        CanvasWindow canvas = new CanvasWindow("Warp", outSize, outSize);
        canvas.setBackground(Color.BLACK);

        z = 0;
        canvas.animate(dt -> {
            z += dt;

            // ----------------------------------------------------------------
            // THINGS TO TRY
            // ----------------------------------------------------------------
            //
            // - The code above loads an image of Coo. Try Nessie too.
            //   (Look in res/images/ for the filename.)
            // - Try each of the commented-out options from the list below,
            //   at first enabling just _one_ of the transforms at a time
            // - Try playing with the parameters to the transforms:
            //   - Play with the Ripple constants
            //   - Multiply the Squinch parameters by different constants
            //   - Make one parameter to Warble or Squinch a constant
            // - Try combining multiple options
            // - Try reordering the transforms in the list when multiple are enabled
            // - Write your own transform class by copying and modifying an existing one

            List<PointTransform> transforms = List.of(
                new Ripple(0.07, 0.12, z * 4)
                // new Squinch(z * 0.9, z * -0.3)
                // new Warble(z * 0.1 + 0.4, Math.cos(z))
                // new Rotate(z)
                // new Ultraripple(0.2, 20.5, Math.sin(z / 5.0), z * 0.2, z * 0.2, Math.sin(z * 0.1) * 30)
            );

            Image image = warp(input, output, transforms);
            canvas.removeAll();
            canvas.add(image);
            canvas.draw();
        });
    }

    private static Image warp(
        PixelArray input,
        PixelArray output,
        List<PointTransform> transforms
    ) {
        int midX = input.getWidth() / 2;
        int midY = input.getHeight() / 2;
        double inScale = 1.0 / Math.min(midX, midY);

        int outSize = output.getWidth();
        double outScale = 3.0 / outSize;

        // For each output pixel...
        for (int outY = 0; outY < output.getHeight(); outY++) {
            for (int outX = 0; outX < output.getWidth(); outX++) {
                // ...convert pixel coordinates to real numbers approx in the range [-1...1]
                Point p = new Point(
                    (outX - outSize / 2) * outScale,
                    (outY - outSize / 2) * outScale
                );

                // Apply transforms to get corresponding input coordinates
                for (PointTransform transform : transforms) {
                    p = transform.apply(p);
                }

                // Convert to input pixel coordinates
                int inX = (int) Math.round(p.getX() / inScale + midX);
                int inY = (int) Math.round(p.getY() / inScale + midY);

                // Copy pixel from input to output
                output.setPixel(outX, outY, input.getPixel(inX, inY));
            }
        }

        Image image = output.toImage();
        return image;
    }
}
