package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class Tube will represent a tube in 3D space
 * <br>
 * contains:
 * <ul>
 *     <li>a double value that represent the radius of the cylinder</li>
 *     <li>a {@link Ray} object that represent the center of the cylinder</li>
 * </ul>
 * @author danielattali, itiskvales
 */
public class Tube extends RadialGeometry {
    /**
     * the ray axis of the tube
     */
    final Ray axisRay;

    /**
     * @param radius the radius of the tube
     * @param axisRay the axis ray of the tube
     */
    protected Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    @Override
    public Vector getNormal(Point point) {
        // first we find the value t such as P0 + v = O (O is the projection of the point P on the Ray
        // t = v * (P - P0)
        // BVA: if t == 0
        double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));

        Point O;

        // O = P0 + t*v
        if (isZero(t)) {
            O = axisRay.getP0();
        } else {
            O = axisRay.getP0().add(axisRay.getDir().scale(t));
        }
        return point.subtract(O).normalize();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tube tube)) return false;
        return Objects.equals(axisRay, tube.axisRay) && tube.radius == this.radius;
    }

    @Override
    public int hashCode() {
        return Objects.hash(axisRay);
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                ", emission=" + emission +
                "} ";
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector d = ray.getDir();
        Vector v = axisRay.getDir();
        double dv = d.dotProduct(v);

        if (ray.getP0().equals(axisRay.getP0())) {
            if (isZero(dv)) {
                if (alignZero(maxDistance - radius) > 0)
                    return List.of(new GeoPoint(this, ray.getPoint(radius)));
                else
                    return null;
            }

            Vector dvv = v.scale(d.dotProduct(v));

            if (d.equals(dvv)) {
                return null;
            }
            double t = Math.sqrt(radius * radius / d.subtract(dvv).lengthSquared());
            if(alignZero(maxDistance-t) > 0)
                return List.of(new GeoPoint(this, ray.getPoint(t)));
            else
                return null;
        }

        Vector x = ray.getP0().subtract(axisRay.getP0());

        double xv = x.dotProduct(v);

        double a = 1 - dv * dv;
        double b = 2 * d.dotProduct(x) - 2 * dv * xv;
        double c = x.lengthSquared() - xv * xv - radius * radius;

        if (isZero(a)) {
            if (isZero(b)) {
                return null;
            }
            double t = -c / b;
            if(alignZero(maxDistance-t) > 0)
                return List.of(new GeoPoint(this, ray.getPoint(t)));
            else
                return null;
        }

        double delta = alignZero(b * b - 4 * a * c);

        if (delta <= 0)
            return null;

        List<GeoPoint> intersections = null;
        double t = alignZero(-(b + Math.sqrt(delta)) / (2 * a));
        if (t > 0 && alignZero(maxDistance-t) > 0) {
            intersections = new LinkedList<>();
            intersections.add(new GeoPoint(this, ray.getPoint(t)));
        }
        t = alignZero(-(b - Math.sqrt(delta)) / (2 * a));
        if (t > 0 && alignZero(maxDistance-t) > 0) {
            if (intersections == null) {
                intersections = new LinkedList<>();
            }
            intersections.add(new GeoPoint(this, ray.getPoint(t)));
        }

        return intersections;
    }
}
