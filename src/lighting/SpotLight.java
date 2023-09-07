package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * this class represent a SpotLight in our engin
 */
public class SpotLight extends PointLight {
    /**
     * the direction of the spotlight
     */
    private Vector direction;

    /**
     * the angle of the beam of light
     */
    private double beamAngle = Math.toRadians(30);

    /**
     * the angle at which the light stops
     */
    private double fallOffAngle = Math.toRadians(60);

    /**
     * @param intensity the color intensity of the light source
     * @param p         the point origin of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point p, Vector direction) {
        super(intensity, p);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double value = Math.max(0.0d, direction.dotProduct(getL(p)));

        double angle = Math.acos(direction.dotProduct(getL(p)));

        if(angle > this.beamAngle + this.fallOffAngle) {
            return new Color(0.0d, 0.0d, 0.0d);
        }
        if(angle > this.beamAngle) {
            double portion = 1 - ((angle - this.beamAngle) / this.fallOffAngle);
            if(portion > 0)
                return super.getIntensity(p).scale(portion);
        }
        return super.getIntensity(p);
//        return super.getIntensity(p).scale(value);
    }

    /**
     * @param value the angle of the beam
     * @return this according to the Builder pattern
     */
    public SpotLight setNarrowBeam(double value) {
        this.beamAngle = Math.toRadians(value);
        this.fallOffAngle = 2 * this.beamAngle;
        return this;
    }
}
