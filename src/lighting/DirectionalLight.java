package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * this class is representing a Directional Light in our engin (Directional is like the sun far away)
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**
     * @param intensity the color intensity of the light source
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();

    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public List<Vector> getL2(Point p) {
        return List.of(getL(p));
    }
}
