package geometries;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing unit for {@link Triangle} class
 * @author danielattali, itiskvales
 */
class TriangleTests {
    // ============ Normals Tests ==============

    /**Test method for {@link geometries.Triangle#getNormal(primitives.Point)}. */
    @Test
    @DisplayName("getNormal test")
    void getNormalTest() {
        // ============ Equivalence Partitions Tests ==============
        // TC1. testing for the normal to the triangle
        Triangle triangle = new Triangle(
                new Point(0, 0, 0),
                new Point(1, 0, 0),
                new Point(0, 1, 0)
        );

        Vector normal = triangle.getNormal(new Point(0, 1, 0));
        Vector v = new Vector(0, 0, 1);
        assertEquals(normal, v);
    }

    // ============ Intersections Tests ==============

    Triangle triangle = new Triangle(
            new Point(1, 1, 0),
            new Point(-1, 1, 0),
            new Point(0, -1, 0)
    );

    Ray ray1 = new Ray(new Point(0, -0.5d, -1), new Vector(0, 1, 1));   // TC1.
    Ray ray2 = new Ray(new Point(0, -3, -1), new Vector(0, 1, 1));      // TC2.
    Ray ray3 = new Ray(new Point(1, -1, -1), new Vector(0, 1, 1));      // TC3.

    Ray ray4 = new Ray(new Point(0, 0, -1), new Vector(0, 1, 1));       // TC4.
    Ray ray5 = new Ray(new Point(2, 0, -1), new Vector(0, 1, 1));      // TC5.
    Ray ray6 = new Ray(new Point(1, 0, -1), new Vector(0, 1, 1));       // TC6.
    /**
     * Test method for {@link Triangle#findIntersections(Ray)}
     */
    @Test
    void findIntersectionsTest() {
        // ============ Equivalence Partitions Tests ==============
        // TC1. testing a Point in triangle
        testingAPointInTriangle1Intersection();
        // TC2. testing a Point between two line 1
        testingAPointBetweenTwoLine0Intersection();
        // TC3. testing a Point between two line 2
        testingAPointBetweenTwoLine0Intersection2();

        // =============== Boundary Values Tests ==================
        // TC4. testing a Point on the line not in the triangle
        testingAPointOnTheLineNotInTheTriangle();
        // TC5. testing a Point on the line in the triangle
        testingAPointOnTheLineInTheTriangle();
        // TC6. testing a Point on one of the triangle Point
        testingAPointOnOneOfTheTrianglePoint();
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing a point in triangle 1 intersection")
    void testingAPointInTriangle1Intersection() {
        assertEquals(new Point(0, 0.5, 0), triangle.findIntersections(ray1).get(0));
    }

    @Test
    @DisplayName("testing a point between two line1 0 intersection")
    void testingAPointBetweenTwoLine0Intersection() {
        assertNull(triangle.findIntersections(ray2));
    }

    @Test
    @DisplayName("testing a point between two line 0 intersection 2")
    void testingAPointBetweenTwoLine0Intersection2() {
        assertNull(triangle.findIntersections(ray3));
    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing a point on the line not in the triangle")
    void testingAPointOnTheLineNotInTheTriangle() {
        assertNull(triangle.findIntersections(ray4));
    }

    @Test
    @DisplayName("testing a point on the line in the triangle")
    void testingAPointOnTheLineInTheTriangle() {
        assertNull(triangle.findIntersections(ray5));
    }

    @Test
    @DisplayName("testing a point on one of the triangle point")
    void testingAPointOnOneOfTheTrianglePoint() {
        assertNull(triangle.findIntersections(ray6));
    }
}