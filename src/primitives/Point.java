package primitives;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Vector.generateVector;


/**
 * class Point Implement the point object in a 3D space
 * <br>
 * contains:
 * <ul>
 *     <li>a {@link Double3} object that represent the (x,y,z) coordinate in 3D space</li>
 * </ul>
 * @author danielattali, itiskvales
* */
public class Point {
    final Double3 xyz;

    /**
     * the const value of the center of axis
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
    * ctor for Point represented as (x,y,z)
    * @param x double value for x
    * @param y double value for y
    * @param z double value for z
    *  */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
    * ctor to init Point with the value of a Double3 object
    * @param xyz  is a Double3
    * */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public String toString() {
        return " "+xyz.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object instanceof Point other) {
            return this.xyz.equals(other.xyz);
        } else if(object instanceof  Double3 other) {
            return this.xyz.equals(other);
        }
        return false;
    }

    /**
     * function add() adds a vector to the point and return the point at the end of the vector
     * @param vector is a Vector and is the vector we add to the point
     * @return the point at the end of the vector */
    public Point add(Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
    }

    /**
     * function subtract() return the vector from out point the other
     * @param other a Point from which the vector start
     * @return the vector with the head in our point and tail at other*/
    public Vector subtract(Point other) {
        return new Vector(this.xyz.subtract(other.xyz));
    }

    /**
     * function distanceSquared() return the distance between two points [d] squared
     * @param other is a Point
     * @return a double value which represent d*d */
    public double distanceSquared(Point other) {
        Double3 diff = other.xyz.subtract(this.xyz);
        return  (diff.d1 * diff.d1) +
                (diff.d2 * diff.d2) +
                (diff.d3 * diff.d3);
    }

    /**
     * function distance() return the distance between two points [d]
     * @param other is a Point
     * @return a double value which represent d*/
    public double distance(Point other) {
        return Math.sqrt(this.distanceSquared(other));
    }


    /**
     * @return the value of the point
     */
    public Double3 getCoordinate() {return this.xyz;}

    /**
     * @return the x value
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * @return the y value
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * @return the z value
     */
    public double getZ() {
        return xyz.d3;
    }

    /**
     * @param vX the x vector of the plane
     * @param vY the y vector of the plane
     * @param amount the amount of point to generate
     * @param center the 'center' of the generation
     * @param size the size of the circle of the generation
     * @return a list of point generated using Jittered Pattern inside a circle around center
     */
    public static List<Point> generatePoints(Vector vX, Vector vY, int amount, Point center, double size) {
        List<Point> points = new LinkedList<>();

//        amount = (int) (1.273 * amount);

        double divider = Math.sqrt(amount);
        double r = size / divider;

//        double size2 = size * size;

        for (int k = 0; k < divider; k++) {
            for (int l = 0; l < divider; l++) {
                double yI = alignZero( -(k - (divider - 1) / 2) * r);
                double xJ = alignZero( -(l - (divider - 1) / 2) * r);

                Point pIJ = center;

                if (xJ != 0) pIJ = pIJ.add(vX.scale(xJ));
                if (yI != 0) pIJ = pIJ.add(vY.scale(yI));

                // adding some random jitter
                pIJ = pIJ.add(generateVector(vX, vY, r));

//                if(alignZero(pIJ.distanceSquared(center)) < size2)
                points.add(pIJ);
            }
        }

        return points;
    }

}
