package transform;

import edu.macalester.graphics.Point;

public class Ripple implements PointTransform {
    private final double wavelength, amplitude, phase;

    public Ripple(double wavelength, double amplitude, double phase) {
        this.wavelength = wavelength;
        this.amplitude = amplitude;
        this.phase = phase;
    }

    @Override
    public Point apply(Point p) {
        return new Point(
            p.getX() + amplitude * Math.cos(phase + p.getY() / wavelength),
            p.getY() + amplitude * Math.cos(phase + p.getX() / wavelength)
        );
    }
}
