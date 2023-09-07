package geometries;

import primitives.Ray;

import java.util.*;

/**
 * Geometries class Implements the composite design pattern for the Intersectable
 * <br>
 * contains:
 * <ul>
 *     <li>a List of {@link Intersectable} objects</li>
 * </ul>
 * @author danielattali, itiskvales
 */
public class Geometries extends Intersectable {

    List<Intersectable> geometries;

    /**
     * If true, then the geometries class will use axis aligned bounding box in the calculations, and vice versa.
     */
    public static boolean axisAlignedBoundingBox = false;

    /**
     * ctor for geometries init an empty list of intersectable
     */
    public Geometries() {
        this.geometries = new LinkedList<>();
    }

    /**
     * ctor for geometries init the list of intersectable to the geometries given
     * @param geometries a list of none specified length of Intersectable object
     */
    public Geometries(Intersectable... geometries) {
        if (axisAlignedBoundingBox) {
            this.geometries = List.of(geometries);

            // create a list of all the geometries in the scene
            List<Intersectable> geos = new ArrayList<>(List.of(geometries));

            // a list of all the boundable geometries in the scene
            List<Boundable> boundables = new LinkedList<>();

            // move all the boundables from geos to boundables list
            for (Intersectable g : geometries) {
                if (g instanceof Boundable) {
                    geos.remove(g);
                    boundables.add((Boundable) g);
                }
            }

            // create an axis aligned bounding box tree for the boundable geometries and add the tree to the geometry list
            geos.add(AxisAlignedBoundingBox.createTree(boundables));
            this.geometries = geos;
        } else
            this.geometries = List.of(geometries);
    }

    /**
     * add Intersectable object to our composite
     * @param geometries a list of none specified length of Intersectable object
     */
    public void add(Intersectable... geometries) {
        if (axisAlignedBoundingBox) {
            //create a list of all the geometries already existing in the scene
            List<Intersectable> geos = new ArrayList<>();
            //add all the un-boundable ones to the ones that are bounded in boxes
            for (Intersectable item : this.geometries) {
                if (item instanceof Boundable)
                    geos.addAll(((Boundable) item).getAxisAlignedBoundingBox().getAllGeometries());
                else
                    geos.add(item);

            }
            // Add all new geometries to the existing ones
            geos.addAll(Arrays.asList(geometries));

            //a list of all the boundable geometries in the scene
            List<Boundable> boundables = new ArrayList<>();

            //move all the boundables from geos to boundables list
            for (Intersectable g : geometries) {
                if (g instanceof Boundable) {
                    geos.remove(g);
                    boundables.add((Boundable) g);
                }
            }
            // create an axis aligned bounding box tree for the boundable geometries and add the tree to the geometry list
            AxisAlignedBoundingBox axisAlignedBoundingBox = AxisAlignedBoundingBox.createTree(boundables);
            if (axisAlignedBoundingBox != null)
                geos.add(axisAlignedBoundingBox);
            this.geometries = geos;
        } else
            this.geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * add Intersectable object to our composite
     * @param geometries a list of none specified length of Intersectable object
     */
    public void addAll(List<Intersectable> geometries) {
        if(geometries == null)
            return;
        this.geometries.addAll(geometries);
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        LinkedList<GeoPoint> intersections = null;
        for (Intersectable g : geometries) {
            List<GeoPoint> temp = g.findGeoIntersections(ray, maxDistance);
            if (temp != null) {
                if (intersections == null)
                    intersections = new LinkedList<>(temp);
                else
                    intersections.addAll(temp);
            }
        }
        return intersections;
    }

    /**
     * @return the list of geometry in the Composite patter
     */
    public List<Intersectable> getGeometries() {
        return geometries;
    }
}
