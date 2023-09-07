package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Cylinder class will represent a Cylinder in 3D space (cylinder is a finite tube)
 * <br>
 * fields:
 * <ul>
 *      <li>a double value that represent the radius of the cylinder</li>
 *      <li>a {@link Ray} object that represent the center of the cylinder</li>
 *      <li>a double value that represent the length of the cylinder</li>
 *  </ul>
 *  <br>
 *  methods:
 *  <ul>
 *      <li>a ctor {@link Cylinder#Cylinder(double, Ray, double)}</li>
 *      <li>a {@link Cylinder#getNormal(Point)} method</li>
 *      <li> a {@link Cylinder#findGeoIntersectionsHelper(Ray, double)} method</li>
 *  </ul>
 * @author danielattali, itiskvales
 */
public class Cylinder extends Tube implements Boundable {
    /**
     * a <b>double</b> value that represent the length (or height) of our cylinder (finite tube)
     */
    final double length;


    /** ctor for {@link Cylinder}
     * @param radius a double value that represent the radius of the cylinder
     * @param axisRay a {@link Ray} object that represent the center ray of the cylinder
     * @param length a double value that represent the length (or height) of the cylinder
     */
    public Cylinder(double radius, Ray axisRay, double length) {
        super(radius, axisRay);
        this.length = length;
    }

    @Override
    public Vector getNormal(Point point) {
        // first we need to check if the point is on the round part or on the base edge
        // if the condition is true the point is in the center of the base
        if(axisRay.getP0().equals(point))
            return axisRay.getDir();

        Vector vec1 = axisRay.getP0().subtract(point);

        // p.s. I created getPoint know because I knew we will be needing it in the future so DRY.
        // if the condition is true the point is in the center of the top
        if(axisRay.getPoint(length).equals(point))
            return axisRay.getDir();

        Vector vec2 = axisRay.getPoint(length).subtract(point);
        // let's check if vec1 or vec2 is orthogonal to axis vector
        // if one of the is orthogonal to the axis vector vec_i * v = 0 we know for sure that the point is on one of the edges
        // if vec1 * dir == 0 so the point on the edge (where p0 is) if vec2 * dir == 0 the point is on the edge (the other edge)
        // so we return the dir vector
        if(isZero(vec1.dotProduct(axisRay.getDir())) || isZero(vec2.dotProduct(axisRay.getDir()))) {
            // since the surface of the edge is flat we can return v or -v
            return axisRay.getDir();
        }
        // else the point is on the round part, so we can just return what we compute in the Tube class (DRY)
        return super.getNormal(point);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = super.findGeoIntersectionsHelper(ray, maxDistance);
        Point p0 = axisRay.getP0();
        Vector dir = axisRay.getDir();
        if (intersections != null) {
            List<GeoPoint> temp = new LinkedList<>();
            for (GeoPoint g : intersections) {
                double pointHeight = alignZero(g.point.subtract(p0).dotProduct(dir));
                if (pointHeight > 0 && pointHeight < length) {
                    temp.add(g);
                }
            }
            if (temp.isEmpty())
                intersections = null;
            else
                intersections = temp;
        }

        List<GeoPoint> planeIntersection = new Plane(p0, dir).findGeoIntersections(ray);
        if (planeIntersection != null) {
            GeoPoint point = planeIntersection.get(0);
            if (point.point.equals(p0) || alignZero(point.point.subtract(p0).lengthSquared() - radius * radius) < 0) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                point.geometry = this;
                intersections.add(point);
            }
        }

        Point p1 = p0.add(dir.scale(length));

        planeIntersection = new Plane(p1, dir).findGeoIntersections(ray);
        if (planeIntersection != null) {
            GeoPoint point = planeIntersection.get(0);
            if (point.point.equals(p1) || alignZero(point.point.subtract(p1).lengthSquared() - radius * radius) < 0) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                point.geometry = this;
                intersections.add(point);
            }
        }

        if (intersections != null) {
            for (GeoPoint g : intersections) {
                g.geometry = this;
            }
        }

        return intersections;
    }


    /**
     * @return the length of the cylinder
     */
    public double getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "length=" + length +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                ", emission=" + emission +
                "} ";
    }

    @Override
    public AxisAlignedBoundingBox getAxisAlignedBoundingBox() {
        double minX, minY, minZ, maxX, maxY, maxZ;
        Point o1 = axisRay.getP0(); // middle of first end
        Point o2 = o1.add(axisRay.getDir().scale(length)); // middle of second end
        double o2X = o2.getX();
        double o1X = o1.getX();
        // middle point of side circles plus a radius offset is a good approximation for the bounding box
        if (o1X > o2X) {
            maxX = o1X + radius;
            minX = o2X - radius;
        } else {
            maxX = o2X + radius;
            minX = o1X - radius;
        }
        double o2Y = o2.getY();
        double o1Y = o1.getY();
        if (o1Y > o2Y) {
            maxY = o1Y + radius;
            minY = o2Y - radius;
        } else {
            maxY = o2Y + radius;
            minY = o1Y - radius;
        }
        double o2Z = o2.getZ();
        double o1Z = o1.getZ();
        if (o1Z > o2Z) {
            maxZ = o1Z + radius;
            minZ = o2Z - radius;
        } else {
            maxZ = o2Z + radius;
            minZ = o1Z - radius;
        }
        AxisAlignedBoundingBox res = new AxisAlignedBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
        res.addToContains(this);

        return res;
    }
}
