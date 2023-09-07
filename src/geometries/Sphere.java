package geometries;

import static primitives.Util.alignZero;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

/**
 * class Sphere will represent a Sphere in 3D space
 * <br>
 * contains:
 * <ul>
 *     <li>a double value that represent the radius of the sphere</li>
 *     <li>a {@link Point} object that represent the center point of the sphere</li>
 * </ul>
 * @author danielattali, itiskvales
 */
public class Sphere extends RadialGeometry implements Boundable{
    /**
     * the center of the sphere
     */
    final Point center;


    /**
     * @param radius the radius of the sphere
     * @param center the Point center of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);

        this.center = center;
    }

    /**
     * @param center the Point center of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere( Point center, double radius) {
        super(radius);

        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        // since we know that the point is on the sphere the vector is the (center - point), where O is the center
        return point.subtract(center).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        try {
            // we added a try catch block because if center == p0 u is a zero vector
            Vector u = this.center.subtract(ray.getP0());

            double tm = alignZero(ray.getDir().dotProduct(u));
            double d =  alignZero(Math.sqrt(u.lengthSquared()- tm * tm));

            // if d == r the ray is tangent to the sphere and if d > r the line doesn't intersect the sphere at all
            if(d >= this.radius) {
                return null;
            }
            // √(r^2 - d^2)
            double th = alignZero(Math.sqrt(this.radius * this.radius - d * d));

            double t1 = alignZero(tm - th);
            double t2 = alignZero(tm + th);


            // if t1 or t2 < 0 the ray is not intersecting the sphere it means that the ray is after the sphere
            // if t1 or t2 == 0 the ray is starting on the sphere so there is no intersection
            if(t1 > 0 && t2 > 0) {
                // if the distance is too big
                if(alignZero(t1 - maxDistance) >= 0 && alignZero(t2 - maxDistance) >= 0)
                    return null;
                // since we want to give the closet point we just need to compare the t's and the smallest one is the closest
                double min = Math.min(t1, t2);
                double max = Math.max(t1, t2);

                return List.of(new GeoPoint(this, ray.getPoint(min)),new GeoPoint(this, ray.getPoint(max)));
            } else if (t1 > 0) {
                if(alignZero(t1 - maxDistance) >= 0) {
                    return null;
                }
                return List.of(new GeoPoint(this,ray.getPoint(t1)));
            } else if(t2 > 0) {
                if(alignZero(t2 - maxDistance) >= 0) {
                    return null;
                }
                return List.of(new GeoPoint(this, ray.getPoint(t2)));
            }
//        in the case that the center == p0 so u = zero vector, so we know that the point = center + radius * dir
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this, ray.getPoint(this.radius)));
        }

        return null;
    }

    public Point getCenter() {
        return this.center;
    }

    public double getRadius() {
        return this.radius;
    }

    @Override
    public AxisAlignedBoundingBox getAxisAlignedBoundingBox() {
        double centerX = center.getX();
        double centerY = center.getY();
        double centerZ = center.getZ();
        AxisAlignedBoundingBox res = new AxisAlignedBoundingBox(
                centerX - radius,
                centerY - radius,
                centerZ - radius,
                centerX + radius,
                centerY + radius,
                centerZ + radius);
        res.addToContains(this);

        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sphere sphere)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getCenter(), sphere.getCenter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCenter());
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                ", emission=" + emission +
                "} ";
    }

}
