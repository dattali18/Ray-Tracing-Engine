package geometries;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;


/**
 * class Triangle represent a triangle in 3D space
 *
 * @author danielattali, itiskvales
 */
public class Triangle extends Polygon {
    // decided to store the point in the triangle for better performance

    /**
     * first point
     */
    final Point p1;

    /**
     * second point
     */
    final Point p2;

    /**
     * third point
     */
    final Point p3;

    /**
     * @return the first point of the triangle
     */
    public Point getP1() {
        return p1;
    }

    /**
     * @return the second point of the triangle
     */
    public Point getP2() {
        return p2;
    }

    /**
     * @return the third point of the triangle
     */
    public Point getP3() {
        return p3;
    }


    /**
     * ctor for Triangle receive 3 Point and call the Polygon ctor to init the triangle
     *
     * @param p1 a Point
     * @param p2 a Point
     * @param p3 a Point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);

        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // since we first find if the is intersection with the plane containing the triangle
        List<GeoPoint> intersectionPoints = this.plane.findGeoIntersections(ray, maxDistance);

        // if intersectionPoints == null there is no intersection
        if(intersectionPoints == null)
            return null;

        // there can't be more than one point of intersection with a triangle
        Point p0 = intersectionPoints.get(0).point;

        // we need to check for if the point is one of the triangle Point
        if(p0.equals(p1) || p0.equals(p2) || p0.equals(p3))
            return null;

        // now that we have the point on the plane we need to check if it's in the triangle

        Vector v1 = this.p1.subtract(p0);
        Vector v2 = this.p2.subtract(p0);
        Vector v3 = this.p3.subtract(p0);

        Vector n_v1 = v1.normalize();
        Vector n_v2 = v2.normalize();
        Vector n_v3 = v3.normalize();

        // checking if the point is on the one of the line on the one of the line
        // between two point
        if(isZero(v1.dotProduct(v2)) || isZero(v2.dotProduct(v3)) || isZero(v3.dotProduct(v1)))
            return null;

        // outside the triangle
        if(n_v1.equals(n_v2) || n_v1.equals(n_v3) || n_v2.equals(n_v3))
            return null;

        try {
            // using the algorithm given to check if the point is in the triangle and not outside
            Vector n1 = v1.crossProduct(v2).normalize();
            Vector n2 = v2.crossProduct(v3).normalize();
            Vector n3 = v3.crossProduct(v1).normalize();

            double t1 = alignZero(ray.getDir().dotProduct(n1));
            double t2 = alignZero(ray.getDir().dotProduct(n2));
            double t3 = alignZero(ray.getDir().dotProduct(n3));

            if ((t1 > 0 && t2 > 0 && t3 > 0) || (t1 < 0 && t2 < 0 && t3 < 0)) {
                // return the geo point
                return List.of(new GeoPoint(this, p0));
            }
        } catch (IllegalArgumentException e) {
            return null;
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Triangle triangle)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getP1(), triangle.getP1()) && Objects.equals(getP2(), triangle.getP2()) && Objects.equals(getP3(), triangle.getP3());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getP1(), getP2(), getP3());
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                ", plane=" + plane +
                ", emission=" + emission +
                "} ";
    }
}
