package lighting;

import geometries.Plane;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Point.generatePoints;

/**
 * this class represent a Point Light like a light bulb
 * <br>
 * contain:
 * <ul>
 *     <li>{@link PointLight#p}</li>
 *     <li>{@link PointLight#kC}</li>
 *     <li>{@link PointLight#kL}</li>
 *     <li>{@link PointLight#kQ}</li>
 * </ul>
 */
public class PointLight extends Light implements LightSource {
    /**
     * the origin point of the light
     */
    Point p;

    /**
     * the parameter kC in Phonge Light Model
     */
    private double kC = 1;

    /**
     * the parameter kL in Phonge Light Model
     */
    private double kL = 0;

    /**
     * the parameter kQ in Phonge Light Model
     */
    private double kQ = 0;

    /**
     * square edge size parameter
     */
    private double lengthOfTheSide;

    /**
     * The amount of rays of the soft shadow.
     * (set 0 to `turn off` the action)
     */
    public static int softShadowsRays;


    /**
     * @param intensity the color intensity of the light source
     * @param p the point origin of the light
     */
    public PointLight(Color intensity, Point p) {
        super(intensity);
        this.p = p;
    }

    // =============== Setters ===============

    /**
     * @param kC the parameter in PLM
     * @return this according to the Builder Pattern
     */
    public PointLight setKC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * @param kL the parameter in PLM
     * @return this according to the Builder Pattern
     */
    public PointLight setKL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * @param kQ the parameter in PLM
     * @return this according to the Builder Pattern
     */
    public PointLight setKQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Setter of the square edge size parameter
     *
     * @param lengthOfTheSide square edge size
     * @return the updated point light
     */
    public PointLight setLengthOfTheSide(double lengthOfTheSide) {
        if (lengthOfTheSide < 0)
            throw new IllegalArgumentException("LengthOfTheSide must be greater then 0");
        this.lengthOfTheSide = lengthOfTheSide;
        return this;
    }

    /**
     * Set the number of `soft shadows` rays
     *
     * @param numOfRays the number of `soft shadows` rays
     * @return the updated camera object
     */
    public PointLight setSoftShadowsRays(int numOfRays) {
        if (numOfRays < 0)
            throw new IllegalArgumentException("numOfRays must be greater then 0!");
        softShadowsRays = numOfRays;
        return this;
    }

    // =================== Methods =========================


    @Override
    public Color getIntensity(Point point) {

        double d2 = point.distanceSquared(this.p);
        double d1 = Math.sqrt(d2);

        return getIntensity().reduce(kC + kL * d1 + kQ * d2);
    }

    @Override
    public Vector getL(Point point) {
        return point.subtract(this.p).normalize();
    }

    @Override
    public double getDistance(Point p) {
        return p.distance(this.p);
    }


    @Override
    public List<Vector> getL2(Point p) {
        if (lengthOfTheSide == 0) return List.of(getL(p));

        List<Vector> vectors = new LinkedList<>();
        Vector l = getL(p);
        // plane of the light
        Plane plane = new Plane(this.p, l);

        // vectors of the plane
        List<Vector> vectorsOfThePlane = plane.findVectorsOfPlane();
        Vector u = vectorsOfThePlane.get(0), v = vectorsOfThePlane.get(1);

        List<Point> points = generatePoints(u, v, softShadowsRays, this.p, lengthOfTheSide);

        for (int k = 0; k < points.size() && k < softShadowsRays; k++) {
            vectors.add(
                    p.subtract(points.get(k))
            );

        }

        vectors.add(l);

        return vectors;
    }
}
