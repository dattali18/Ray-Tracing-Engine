package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * A class that takes care of creating the BVH - Bounding Volume Hierarchy
 *
 * @author Daniel Wolpert, Amitay Cahalon
 */
public class AxisAlignedBoundingBox extends Intersectable implements Boundable {
    /**
     * The minimum values of the box on the axis
     */
    private double minX, minY, minZ;

    /**
     * The maximum values of the box on the axis
     */
    private double maxX, maxY, maxZ;

    /**
     * The middle of the box on the axis
     */
    private double midX, midY, midZ;

    /**
     * A list of the contained boundable objects
     */
    private final List<Boundable> contains;
    // Boundable left, right = null;


    /**
     * Create an axis aligned bounding box given the furthest axis values
     *
     * @param minX minimum x value
     * @param minY minimum y value
     * @param minZ minimum z value
     * @param maxX maximum x value
     * @param maxY maximum y value
     * @param maxZ maximum z value
     */
    public AxisAlignedBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;

        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

        this.midX = (minX + maxX) / 2;
        this.midY = (minY + maxY) / 2;
        this.midZ = (minZ + maxZ) / 2;

        contains = new ArrayList<>();
    }


    /**
     * Create an axis aligned bounding box that encapsulates a list of Boxes
     *
     * @param boxes the list of boxes to bound
     */
    public AxisAlignedBoundingBox(List<AxisAlignedBoundingBox> boxes) {
        AxisAlignedBoundingBox axisAlignedBoundingBox = boxes.get(0);
        this.maxX = axisAlignedBoundingBox.getMaxX();
        this.maxY = axisAlignedBoundingBox.getMaxY();
        this.maxZ = axisAlignedBoundingBox.getMaxZ();
        this.minX = axisAlignedBoundingBox.getMinX();
        this.minY = axisAlignedBoundingBox.getMinY();
        this.minZ = axisAlignedBoundingBox.getMinZ();

        for (int i = 1; i < boxes.size(); i++) {
            AxisAlignedBoundingBox currentBox = boxes.get(i);
            double boxMaxX = currentBox.maxX;
            double boxMaxY = currentBox.maxY;
            double boxMaxZ = currentBox.maxZ;
            double boxMinX = currentBox.minX;
            double boxMinY = currentBox.minY;
            double boxMinZ = currentBox.minZ;
            if (boxMaxX > maxX)
                this.maxX = boxMaxX;
            if (boxMaxY > maxY)
                this.maxY = boxMaxY;
            if (boxMaxZ > maxZ)
                this.maxZ = boxMaxZ;
            if (boxMinX < minX)
                this.minX = boxMinX;
            if (boxMinY < minY)
                this.minY = boxMinY;
            if (boxMinZ < minZ)
                this.minZ = boxMinZ;
        }
        this.midX = (minX + maxX) / 2;
        this.midY = (minY + maxY) / 2;
        this.midZ = (minZ + maxZ) / 2;
        contains = new ArrayList<>();
    }

    /**
     * Gets the value of minimum X
     *
     * @return The value
     */
    public double getMinX() {
        return minX;
    }

    /**
     * Gets the value of minimum Y
     *
     * @return The value
     */
    public double getMinY() {
        return minY;
    }

    /**
     * Gets the value of minimum Z
     *
     * @return The value
     */
    public double getMinZ() {
        return minZ;
    }

    /**
     * Gets the value of maximum X
     *
     * @return The value
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * Gets the value of maximum Y
     *
     * @return The value
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * Gets the value of maximum Z
     *
     * @return The value
     */
    public double getMaxZ() {
        return maxZ;
    }

    /**
     * Add an object to contains
     *
     * @param boundable object to add
     */
    public void addToContains(Boundable boundable) {
        this.contains.add(boundable);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        //the ray's head and direction points
        Vector dir = ray.getDir();
        double xDir = dir.getX();
        double yDir = dir.getY();
        double zDir = dir.getZ();

        Point point = ray.getP0();
        double xPoint = point.getX();
        double yPoint = point.getY();
        double zPoint = point.getZ();
        double xMax, yMax, zMax, xMin, yMin, zMin;
        // if the vector's x coordinate is zero
        if (isZero(xDir)) {
            //if the point's x value is in the box,
            if (maxX >= xPoint && minX <= xPoint) {
                xMax = Double.MAX_VALUE;
                xMin = Double.MIN_VALUE;
            } else
                return null;
        }
        //if the vector's x coordinate is not zero, we need to check if we have values
        //where (MaxX - xPoint) / xDir > (MinX - xPoint) / xDir
        else {
            double t1 = (maxX - xPoint) / xDir;
            double t2 = (minX - xPoint) / xDir;
            xMin = Math.min(t1, t2);
            xMax = Math.max(t1, t2);
        }
        //if the vector's y coordinate is zero
        if (isZero(yDir)) {
            //if the point's y value is in the box,
            if (maxX >= yPoint && minY <= yPoint) {
                yMax = Double.MAX_VALUE;
                yMin = Double.MIN_VALUE;
            } else
                return null;
        }
        //if the vector's y coordinate is not zero, we need to check if we have values
        //where (MaxY - yPoint) / yDir > (MinY - yPoint) / yDir
        else {
            double t1 = (maxY - yPoint) / yDir;
            double t2 = (minY - yPoint) / yDir;
            yMin = Math.min(t1, t2);
            yMax = Math.max(t1, t2);
        }

        //if the vector's z coordinate is zero
        if (isZero(zDir)) {
            //if the point's z value is in the box,
            if (maxZ >= zPoint && minZ <= zPoint) {
                zMax = Double.MAX_VALUE;
                zMin = Double.MIN_VALUE;
            } else
                return null;
        }
        //if the vector's z coordinate is not zero, we need to check if we have values
        //where (MaxZ - zPoint) / zDir > (MinZ - zPoint) / zDir
        else {
            double t1 = (maxZ - zPoint) / zDir;
            double t2 = (minZ - zPoint) / zDir;
            zMin = Math.min(t1, t2);
            zMax = Math.max(t1, t2);
        }

        //check if such a point exists
        if (xMin > yMax || xMin > zMax || yMin > xMax || yMin > zMax || zMin > yMax || zMin > xMax)
            return null; // if not return null
            // if they do, return all the intersection points of the contents of the box
        else {
            List<GeoPoint> lst = new LinkedList<>();
            for (Boundable geo : contains) {
                List<GeoPoint> pointLst = ((Intersectable) geo).findGeoIntersections(ray, maxDistance);
                if (pointLst != null)
                    lst.addAll(pointLst);
            }
            return lst;
        }
    }

    @Override
    public AxisAlignedBoundingBox getAxisAlignedBoundingBox() {
        return this;
    }

    /**
     * Creates an AxisAlignedBoundingBox tree given a list of boundable objects.
     * Used to create an axis aligned bounding box tree for a scene with geometries.
     *
     * @param boundables a list of boundable objects
     * @return axis aligned bounding box tree if size > 0, else null
     */
    public static AxisAlignedBoundingBox createTree(List<Boundable> boundables) {
        //if we got 0 boundables to bound
        if (boundables.size() == 0)
            return null;

        else {
            //turn the list of boundables into a list of boxes that encapsulate the boundables
            ArrayList<AxisAlignedBoundingBox> boxes = new ArrayList<>(boundables.stream().map(Boundable::getAxisAlignedBoundingBox).toList());
            return createTreeRec(boxes);
        }
    }

    /**
     * Create a tree of boxes given a list of boxes to turn into a tree
     *
     * @param boxes list of boxes
     * @return axis aligned bounding box tree
     */
    private static AxisAlignedBoundingBox createTreeRec(ArrayList<AxisAlignedBoundingBox> boxes) {
        //create a box that encapsulates all the other ones
        AxisAlignedBoundingBox node = new AxisAlignedBoundingBox(boxes);

        //find the longest edge of the box
        double x = node.maxX - node.minX;
        double y = node.maxY - node.minY;
        double z = node.maxZ - node.minZ;
        int edge = x > y && x > z ? 0 : (y > x && y > z ? 1 : 2);
        int numberOfBoxes = boxes.size();

        //base of the recursion, if the list has 1 box, return it
        if (numberOfBoxes == 1)
            return boxes.get(0);

            //base of the recursion, if the list has 2 boxes
        else if (numberOfBoxes <= 2) {
            for (AxisAlignedBoundingBox box : boxes)
                node.addToContains(box);//add them to this box
        }
        //recursion step, if we have more boxes left
        else {
            ArrayList<AxisAlignedBoundingBox> left, right;
            //sort the boxes according to how they align on that edge
            sortBoxesByAxis(boxes, edge);
            //split the list into 2 even pieces
            left = new ArrayList<>(boxes.subList(0, numberOfBoxes / 2));
            right = new ArrayList<>(boxes.subList(numberOfBoxes / 2, numberOfBoxes));
            //go down the recursion
            node.addToContains(createTreeRec(left));
            node.addToContains(createTreeRec(right));
        }
        return node;
    }

    /**
     * Sorts a bounding box list according to the axis given
     *
     * @param boxes List of boxes to sort
     * @param axis  Axis to sort by
     */
    public static void sortBoxesByAxis(ArrayList<AxisAlignedBoundingBox> boxes, int axis) {
        switch (axis) {
            case 0:
                boxes.sort((AxisAlignedBoundingBox x, AxisAlignedBoundingBox y) -> Double.compare(y.midX, x.midX));
            case 1:
                boxes.sort((AxisAlignedBoundingBox x, AxisAlignedBoundingBox y) -> Double.compare(y.midY, x.midY));
            case 2:
                boxes.sort((AxisAlignedBoundingBox x, AxisAlignedBoundingBox y) -> Double.compare(y.midZ, x.midZ));
        }
    }

    /**
     * A function that returns a list of all the geometries kept
     * inside an axis aligned bounding box tree
     *
     * @return List of geometries
     */
    public List<Intersectable> getAllGeometries() {
        List<Intersectable> res = new ArrayList<>();
        for (Boundable item : contains) {
            //Base of the recursion, we found geometry
            if (item instanceof Geometry)
                res.add((Intersectable) item);
                //Recursion step, return all the geometries in the inside box
            else
                res.addAll(((AxisAlignedBoundingBox) item).getAllGeometries());
        }
        return res;
    }
}


