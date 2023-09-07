package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * this interface will represent a light source in our scene
 */
public interface LightSource {
    /**
     * @param p a point
     * @return the color at the point p
     */
    public Color getIntensity(Point p);

    /**
     * @param p a point
     * @return the direction of the light at the point p
     */
    public Vector getL(Point p);

    /**
     * @param p the point from which we want the distance
     * @return the distance from point to the light source
     */
    public double getDistance(Point p);

    /**
     * Gets vectors from the given point to the light source
     *
     * @param p the point
     * @return all vectors who created
     */
    public List<Vector> getL2(Point p);
}
