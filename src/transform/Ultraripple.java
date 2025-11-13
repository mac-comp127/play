package transform;

import edu.macalester.graphics.Point;

public class Ultraripple implements PointTransform {
    private final double effectRadius, rippliness, gridRipple, xWavelen, yWavelen, phase;

    public Ultraripple(
        double effectRadius,
        double rippliness,
        double gridRipple,
        double xWavelen,
        double yWavelen,
        double phase
    ) {
        this.effectRadius = effectRadius;
        this.rippliness = rippliness;
        this.gridRipple = gridRipple;
        this.xWavelen = xWavelen;
        this.yWavelen = yWavelen;
        this.phase = phase;
    }

    @Override
    public Point apply(Point p) {
        double theta = phase
            + Math.hypot(p.getX(), p.getY()) * rippliness
            + phase * gridRipple * (Math.cos(p.getX() * xWavelen) + Math.cos(p.getY() * yWavelen));

        return new Point(
            p.getX() + effectRadius * Math.cos(theta),
            p.getY() + effectRadius * Math.sin(theta)
        );
    }
}
