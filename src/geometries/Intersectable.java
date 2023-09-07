package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * this interface is representing an object that can be intersected with rays
 * @author danielattali, itiskvales
 */
public abstract class Intersectable {

    /**
     * function findIntersections() will return all the intersection point between a ray and an object (which implement the interface)
     * @param ray a ray which will intersect the object
     * @return a list of points that the ray intersect
     */
    public final List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream()
                .map(gp -> gp.point).toList();
    }

    /**
     * function findIntersections() will return all the intersection point between a ray and an object (which implement the interface)
     * @param ray a ray which will intersect the object
     * @return a list of GeoPoints that the ray intersect
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * function findIntersections() will return all the intersection point between a ray and an object (which implement the interface)
     * using the NVI Pattern
     * @param ray a ray which will intersect the object
     * @param maxDistance the max distance from which there is no intersection
     * @return a list of GeoPoints that the ray intersect
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * @param ray the ray we want to intersect with
     * @param maxDistance the maximum distance we want the point to be from the starting point
     * @return a list of all the point that intersect the ray
     */
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * PDS for storing the data of on what geometry is a certain point
     */
    public static class GeoPoint {
        /**
         * the geometry
         */
        public Geometry geometry;

        /**
         * the point on the geometry
         */
        public Point point;

        /**
         * ctor for the PDS GeoPoint
         * @param geo geometry
         * @param p point
         */
        public GeoPoint(Geometry geo, Point p) {
            this.point = p;
            this.geometry = geo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return (geometry == geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

}
