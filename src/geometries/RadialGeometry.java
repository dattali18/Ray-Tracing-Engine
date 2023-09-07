package geometries;

import java.util.Objects;

/**
 * class RadialGeometry is a class that will represent a radial (round) in 3D space
 * this class will serve all RadialGeometry object in our project
 * @author danielattali, itiskvales
 */
public abstract class RadialGeometry extends Geometry {
    /**
     * the radius of a Radial Geometry object
     */
    protected final double radius;

    /**
     * ctor for a Radial Geometry object receive the radius of the object
     * @param radius a double which represent the radius
     */
    protected RadialGeometry(double radius) {
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RadialGeometry that)) return false;
        return Double.compare(that.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius);
    }

    @Override
    public String toString() {
        return "RadialGeometry{" +
                "radius=" + radius +
                ", emission=" + emission +
                "} ";
    }
}
