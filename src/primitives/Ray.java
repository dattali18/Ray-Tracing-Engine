package primitives;
/** This class implement ray in 3D
 * @author Itzik
 * */

import geometries.Intersectable;

import java.util.*;

import static primitives.Util.isZero;
import static geometries.Intersectable.GeoPoint;

/**
 * class Ray represent a ray (a finite line) in 3D Space
 * contains:
 * <ul>
 *      <li>a {@link Point} object that represent the starting point of the Ray</li>
 *      <li>a {@link Vector} object thar represent the direction of the Ray</li>
 *  </ul>
 * @author danielattali, itiskvales
 */
public class Ray {
    /**
     * @return the value of the starting point of the ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * @return the direction Vector of the ray
     */
    public Vector getDir() {
        return dir;
    }

    // the starting point of the ray in R^3
    final Point p0;
    // the vector which is the direction
    final Vector dir;

    /**
     * the const value by which we move the normal vector of reflected and refracted rays
     */
    private static final double DELTA = 0.1d;

    /**
     * the ctor  receive a Point and a Vector, and it will store it (it will normalize)
     * @param p the starting Point
     * @param v the direction Vector
     */
    public Ray(Point p, Vector v){
        p0 = p;
        dir = v.normalize();
    }

    /**
     * @param p the starting point of the ray
     * @param v the direction of the ray
     * @param n the normal vector from the surface
     */
    public Ray(Point p, Vector v, Vector n) {
        double nv = n.dotProduct(v);
        if (isZero(nv)) {
            p0 = p;

        } else {
            Vector delta = n.scale(nv > 0 ? DELTA : -DELTA);
            p0 = p.add(delta);
        }

        dir = v.normalize();
    }

    /** function getPoint give the point at P0 + t*v
     * @param t is a double which represent the distance we travel along the ray
     * @return the point = p0 + t*v
     */
    public Point getPoint(double t) {
        if(isZero(t) || t == 0.0d)
            return getP0();
        try {
            return p0.add(dir.scale(t));
        } catch (Exception e) {
            return getP0();
        }

    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (!(obj instanceof Ray other)) return false;
        return this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    @Override
    public String toString(){
        return "Ray point: " + p0.toString() + "\nRay vector: " + dir.toString();
    }

    /**
     * calculate and return the closest point to the origin of the ray p0
     * @param points a list of point
     * @return the closet point to the origin of the ray
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null ? null : findClosestGeoPoint(points.stream()
                .map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * calculate and return the closest point to the origin of the ray p0
     * @param geoPoints a list of point
     * @return the closet point to the origin of the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        // if the list is null we return null
        if(geoPoints == null || geoPoints.isEmpty())
            return null;

        Map<GeoPoint, Double> map = new HashMap<>();

        // mapping each point with the distance to the origin of the ray
        for (GeoPoint p: geoPoints) {
            map.put(p, p.point.distanceSquared(this.p0));
        }

        // put the map in a list and sort by the value (double
        List<Map.Entry<GeoPoint, Double>> list = new LinkedList<>(map.entrySet());

        // Sort the list
        list.sort((Comparator<Map.Entry<GeoPoint, Double>>) (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));

        return list.get(0).getKey();
    }

}
