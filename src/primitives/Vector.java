package primitives;

import geometries.Plane;

import java.util.LinkedList;
import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.*;

/**
 * class Vector implement a  Vector in a 3D space
 *
 * @author danielattali, itiskvales
 */
public class Vector extends Point {

    /**
     * ctor for the Vector class init the xyz with the value of x, y, z
     * @throws IllegalArgumentException
     * in the case the vector is the 0'th vector
     * @param x double value of x
     * @param y double value of y
     * @param z double value of z
     * */
    public Vector(double x, double y, double z) {
        super(x, y, z);

        if(this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("we can't have a 0 vector");
        }
    }

    /**
     * ctor for the Vector class init the xyz with the value of x, y, z
    * @throws IllegalArgumentException
    * in the case the vector is the 0'th vector
     * @param xyz Double3 value of xyz
     * */
    public Vector(Double3 xyz) {
        super(xyz);

        if(this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("we can't have a 0 vector");
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Vector other)) return false;
        return super.equals(other);
    }

    /**
     * function add() return the addition of the two vector this and other
     * @param other is a Vector
     * @return a new Vector the addition of the two vector*/
    public Vector add(Vector other) {
        return new Vector(this.xyz.add(other.xyz));
    }

    /**
     * function scale() scale the Vector by a value of d
     * @param d is a double by which we scale the vector
     * @return a new Vector resulting from the operation
     */
    public Vector scale(double d) {
        if(isZero(d))
            return new Vector(this.xyz);
       return new Vector(this.xyz.scale(d));
    }


    /**
     * function dotProduct() return the double value of the dot product between our vector the other
     * @param other is a Vector with which we do the dot product
     * @return a double value of the dot product
     */
    public double dotProduct(Vector other) {
        return this.xyz.d1 * other.xyz.d1 + this.xyz.d2 * other.xyz.d2 + this.xyz.d3 * other.xyz.d3;
    }

    /**
     * function crossProduct() return the new Vector received from the cross product operation
     * @param other is a Vector with which we do the cross product
     * @return a new Vector which is the result of the operation
     */
    public Vector crossProduct(Vector other) {
        return new Vector(  this.xyz.d2 * other.xyz.d3 - this.xyz.d3 * other.xyz.d2,
                            this.xyz.d3 * other.xyz.d1 - this.xyz.d1 * other.xyz.d3,
                            this.xyz.d1 * other.xyz.d2 - this.xyz.d2 * other.xyz.d1
                        );
    }

    /**
     * function lengthSquared() return the l^2 of our vector
     * @return a double which represent the value of the length l squared
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }


    /**
     * function length() return the length of the vector
     * @return a double which represent the value of the length l
     */
    public double length()  {
        return Math.sqrt(lengthSquared());
    }

    /**
     * function normalize the vector (is the vector that point to the same direction with length 1)
     * @return a new Vector which is a normalized version vector of our vector
     */
    public Vector normalize() {
        return new Vector(this.xyz.reduce(this.length()));
    }

    /**
     * @param gp gp the GeoPoint at the surface of the geometry
     * @param n n the normal to the surface of the geometry at the point of gp.point
     * @param coneAngle coneAngle the angle of the cone in which the random rays will be generated (in radians)
     * @param amount the number of random vector to generate
     * @return list of random direction vector within the cone defined by the normal vector
     */
    public static List<Vector> generateRandomDirectionInCone(GeoPoint gp, Vector n, double coneAngle, int amount) {
        List<Vector> result = new LinkedList<>();

        double size = Math.tan(coneAngle) / 2;

        Plane plane = new Plane(gp.point, n);
        List<Vector> vectors = plane.findVectorsOfPlane();
        Vector v = vectors.get(0), u = vectors.get(1);

        List<Point> points = generatePoints(u, v, amount, gp.point.add(n), size);

        for (Point p: points) {
            result.add(
                    p.subtract(gp.point)
            );
        }

        return result;
    }

    /**
     * @param vX the x vector of the plane
     * @param vY the y vector of the plane
     * @param size the size of the circle of the generation
     * @return a random combination of a*vX + b*vY such as a,b in [-size,size]
     */
    public static Vector generateVector(Vector vX, Vector vY, double size) {
        while(true) {
            try {
                return vX.scale(randomDoubleBetweenTwoNumbers(-size, size))
                        .add(vY.scale(randomDoubleBetweenTwoNumbers(-size, size)));
            } catch (Exception e) {
                // if the random number is 0, and we don't have 0 vector
            }
        }

    }

    //===================== Rotation Bonus ==============================

    /**
     * getRotationMatrix() return the rotation matrix for rotating by alpha degree in the 'axis' axis
     * @param alpha the angle in degree (0 to 360) of the rotation
     * @param axis the axis rotation {'x', 'y', 'z'} any other letter will return null
     * @return the rotation matrix corresponding to the rotation
     */
    public static double[][] getRotationMatrix(double alpha, char axis) {
        double alphaRadian = Math.toRadians(alpha);

        return switch (axis) {
            case 'x' -> new double[][]{
                    {1, 0, 0},
                    {0, Math.cos(alphaRadian), -Math.sin(alphaRadian)},
                    {0, Math.sin(alphaRadian), Math.cos(alphaRadian)}
            };
            case 'y' -> new double[][]{
                    {Math.cos(alphaRadian), 0, Math.sin(alphaRadian)},
                    {0, 1, 0},
                    {-Math.sin(alphaRadian), 0, Math.cos(alphaRadian)}
            };
            case 'z' -> new double[][]{
                    {Math.cos(alphaRadian), -Math.sin(alphaRadian), 0},
                    {Math.sin(alphaRadian), Math.cos(alphaRadian), 0},
                    {0, 0, 1}
            };
            default -> null;
        };
    }

    /**
     * rotate() rotate a vector alpha degree along a certain axis
     * @param alpha the angle in degree (0 to 360) of the rotation
     * @param axis the axis rotation {'x', 'y', 'z'} any other letter will return null
     * @return a new Vector after an alpha degree rotation in the 'axis' axis
     */
    public Vector rotate(double alpha, char axis) {
        double[][] rotationMatrix = getRotationMatrix(alpha, axis);
        if(rotationMatrix == null)
            return null;

        double[] rotatedVector = {0, 0, 0};
        double[] vector = {this.xyz.d1, this.xyz.d2, this.xyz.d3};


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rotatedVector[i] += rotationMatrix[i][j] * vector[j];
            }
        }

        double x = alignZero(rotatedVector[0]);
        double y = alignZero(rotatedVector[1]);
        double z = alignZero(rotatedVector[2]);

        return new Vector(x, y, z);
    }
}
