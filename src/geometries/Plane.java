package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/**
 * Plane class implements a 3D plane in our project
 * <br>
 * contains:
 * <ul>
 *     <li>a {@link Point} object that represent a point on the Plane</li>
 *     <li>a {@link Vector} object that represent the normal (orthogonal) vector to the plane</li>
 * </ul>
 * @author danielattali, itiskvales
 */
public class Plane extends Geometry {
    // a point on the plane
    private final Point q0;

    // the normal vector to the plane
    private final Vector normal;

    /**
     * ctor for a Plane receive 3 Points and calculate the perpendicular vector to the plane
     * @param p1 is a Point
     * @param p2 is a Point
     * @param p3 is a Point
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;

        // calculating the two vector from p1->p2 and p1->p3
        Vector v1 = p1.subtract(p2);
        Vector v2 = p1.subtract(p3);

        // calculating the vector who is perpendicular to the Plane
        // in the future we will store it in the this.normal vector
        Vector n = v1.crossProduct(v2);
        this.normal = n.normalize();

    }

    /**
     * ctor for a Plane receive a reference point and a vector which is normal to the plane
     * @param q0 a Point
     * @param normal a Vector (normal to the Plane)
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }


    @Override
    public Vector getNormal(Point point) {
        // we are assuming that the given point is on the plane we are not checking if it is
        return getNormal();
    }

    /**
     * function getNormal() return our normal
     * @return a Vector which is normal to the plane
     */
    public Vector getNormal() { return normal; }

    /**
     * @return the starting point of the plane
     */
    public Point getPoint() {return this.q0;}


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // checking if the starting point of ray is q0
        if(this.q0.equals(ray.getP0()))
            return null;

        double t = alignZero(this.normal.dotProduct(this.q0.subtract(ray.getP0())) / this.normal.dotProduct(ray.getDir()));



        // checking if t>0 the is one intersection point
        if(t > 0) {
            // if the distance is greater than the max distance we don't return anything
            if(alignZero(t - maxDistance) >= 0) {
                return null;
            }
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plane plane)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(q0, plane.q0) && Objects.equals(getNormal(), plane.getNormal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), q0, getNormal());
    }


    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                ", emission=" + emission +
                "} ";
    }

    public List<Vector> findVectorsOfPlane() {
        List<Vector> vectors = new LinkedList<>();

        Vector normalVector = getNormal();
        Point p0 = this.q0;

        double nX = normalVector.getX(), nY = normalVector.getY(), nZ = normalVector.getZ();
        double pX = p0.getX(), pY = p0.getY(), pZ = p0.getZ();

        double[] normal = {nX, nY, nZ};


        double d = -(nX * pX + nY * pY + nZ * pZ);


        int i;
        double val = 0;
        for (i = 0; i < 3; i++) {
            if(!isZero(normal[i])) {
                val = normal[i];
                break;
            }
        }

        Vector v1 = null;
        switch (i) {
            case 0 -> v1 = new Vector(val, 0, 0);
            case 1 -> v1 = new Vector(0, val, 0);
            case 2 -> v1 = new Vector(0, 0, val);
        }


        assert v1 != null;
        Vector v2 = v1.crossProduct(normalVector);

        return List.of(v1.normalize(), v2.normalize());
    }
}
