package geometries;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing unit for {@link Sphere} class
 * @author danielattali, itiskvales
 */
class SphereTests {
    // ============ Normal Tests ==============
    Sphere sphere = new Sphere(1, new Point(0, 0, 1));

    /**
     * Test method for {@link Sphere#getNormal(Point)}
     */
    @Test
    void getNormalTest() {
        // ============ Equivalence Partitions Tests ==============
        // TC1. testing for a point on the surface of a sphere
        Sphere sphere = new Sphere(2, new Point(0, 0, 0));

        Vector normal = sphere.getNormal(new Point(2, 0, 0));

        assertEquals(new Vector(1, 0, 0), normal);

        assertTrue(isZero(normal.length() - 1));
    }

    // ============ Intersections Tests ==============

    /**
     * Test method for {@link Sphere#findIntersections(Ray)}
     */
    @Test
    void findIntersectionsTest() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        testingRayLineIsOutsideTheSphere0Intersections();

        // TC02: ray's is intersection at two point
        testingRayIsIntersectingA2Points();

        // TC03: Ray starts inside the sphere (1 point)
        testingRayStartInsideTheSphere1Intersection();

        // TC04: Ray starts after the sphere (0 points)
        testingRayStartsAfterTheSphere0Intersections();

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 point)
        testingRayStartAtSphereAndGoesInside1Intersection();

        // TC12: Ray starts at sphere and goes outside (0 points)
        testingRayStartAtSphereAndGoesOutside0Intersection();

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        testingRayStartBeforeTheSphere2Intersections();
        
        // TC14: Ray starts at sphere and goes inside (1 point)
        testingRayStartAtSphereAndGoesInsideThroughTheCenter1Intersection();
        
        // TC15: Ray starts inside (1 point)
        testingRayStartInsideThroughTheCenter1Intersection();
        
        // TC16: Ray starts at the center (1 point)
        testingRayStartAtTheCenter1Intersection();
        
        // TC17: Ray starts at sphere and goes outside (0 points)
        testingRayTheAtTheSphereAndGoesOutside0Intersection();

        // TC18: Ray starts after sphere (0 points)
        testingRayStartAfterTheSphereAndDontPassThroughTheCenter0Intersection();

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        testingRayStartBeforeTheTangentPoint0Intersection();

        // TC20: Ray starts at the tangent point
        testingRayStartsAtTheTangentPoint0Intersection();

        // TC21: Ray starts after the tangent point
        testingRayStartsAfterTheTangentPoint0Intersection();

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        testingRayLineIsOutsideAndOrthogonalToTheCenterLine0Intersection();
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing ray line is outside the sphere 0 intersections")
    void testingRayLineIsOutsideTheSphere0Intersections() {
        Ray ray = new Ray(new Point(-2, 2, 0), new Vector(1, 0, 0));
        assertNull(sphere.findIntersections(ray));
    }

    @Test
    @DisplayName("testing ray is intersecting a 2 points")
    void testingRayIsIntersectingA2Points() {
        Ray ray = new Ray(new Point(-2, 0.5, 1), new Vector(1, 0, 0));
        assertEquals(List.of(new Point(-0.8660254037844386, 0.5, 1), new Point(0.8660254037844386, 0.5, 1)), sphere.findIntersections(ray));
    }

    @Test
    @DisplayName("testing ray start inside the sphere 1 intersection")
    void testingRayStartInsideTheSphere1Intersection() {
        Ray ray = new Ray(new Point(-0.5, 0.5, 1), new Vector(1, 0, 0));
        List<Point> points1 = sphere.findIntersections(ray);
        assertEquals(new Point(0.8660254037844386, 0.5, 1), points1.get(0));
        assertEquals(1, points1.size());
    }

    @Test
    @DisplayName("testing ray starts after the sphere 0 intersections")
    void testingRayStartsAfterTheSphere0Intersections() {
        Ray ray = new Ray(new Point(2, 0.5, 1), new Vector(1, 0, 0));
        assertNull(sphere.findIntersections(ray));
    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing ray start at sphere and goes inside 1 intersection")
    void testingRayStartAtSphereAndGoesInside1Intersection() {
        Ray ray = new Ray(new Point(-1, 0, 1), new Vector(1, 0, 0));
        assertEquals(new Point(1, 0, 1), sphere.findIntersections(ray).get(0));
    }

    @Test
    @DisplayName("testing ray start at sphere and goes outside 0 intersection")
    void testingRayStartAtSphereAndGoesOutside0Intersection() {
        Ray ray = new Ray(new Point(1, 0, 1), new Vector(1, 0, 0));
        assertNull(sphere.findIntersections(ray));
    }

    @Test
    @DisplayName("testing ray start before the sphere 2 intersections")
    void testingRayStartBeforeTheSphere2Intersections() {
        Ray ray = new Ray(new Point(-2, 0, 1), new Vector(1, 0, 0));
        assertEquals(List.of(new Point(-1, 0, 1), new Point(1, 0, 1)), sphere.findIntersections(ray));
    }

    @Test
    @DisplayName("testing ray start at sphere and goes inside through the center 1 intersection")
    void testingRayStartAtSphereAndGoesInsideThroughTheCenter1Intersection() {
        Ray ray = new Ray(new Point(-1, 0, 1), new Vector(1, 0, 0));
        assertEquals(new Point(1, 0, 1), sphere.findIntersections(ray).get(0));
    }

    @Test
    @DisplayName("testing ray start inside Through the center 1 intersection")
    void testingRayStartInsideThroughTheCenter1Intersection() {
        Ray ray = new Ray(new Point(0.5, 0, 1), new Vector(1, 0, 0));
        assertEquals(new Point(1, 0, 1), sphere.findIntersections(ray).get(0));
    }

    @Test
    @DisplayName("testing ray start at the center 1 intersection")
    void testingRayStartAtTheCenter1Intersection() {
        Ray ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
        assertEquals(new Point(1, 0, 1), sphere.findIntersections(ray).get(0));
    }

    @Test
    @DisplayName("testing ray the at the sphere and goes outside 0 intersection")
    void testingRayTheAtTheSphereAndGoesOutside0Intersection() {
        Ray ray = new Ray(new Point(1, 0, 1), new Vector(1, 0, 0));
        assertNull(sphere.findIntersections(ray));
    }

    @Test
    @DisplayName("testing ray start after the sphere and dont pass through the center 0 intersection")
    void testingRayStartAfterTheSphereAndDontPassThroughTheCenter0Intersection() {
        Ray ray = new Ray(new Point(2, 0, 1), new Vector(1, 0, 0));
        assertNull(sphere.findIntersections(ray));
    }

    @Test
    @DisplayName("testing ray start before the tangent point 0 intersection")
    void testingRayStartBeforeTheTangentPoint0Intersection() {
        Ray ray = new Ray(new Point(-1, 1, 1), new Vector(1, 0, 0));
        assertNull(sphere.findIntersections(ray));
    }

    @Test
    @DisplayName("testing ray starts at the tangent point 0 intersection")
    void testingRayStartsAtTheTangentPoint0Intersection() {
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(1, 0, 0));
        assertNull(sphere.findIntersections(ray));
    }

    @Test
    @DisplayName("testing ray starts after the tangent point 0 intersection")
    void testingRayStartsAfterTheTangentPoint0Intersection() {
        Ray ray = new Ray(new Point(1, 0, 0), new Vector(1, 0, 0));
        assertNull(sphere.findIntersections(ray));
    }

    @Test
    @DisplayName("testing ray line is outside and orthogonal to the center line 0 intersection")
    void testingRayLineIsOutsideAndOrthogonalToTheCenterLine0Intersection() {
        Ray ray = new Ray(new Point(0, 0, -1), new Vector(1, 0, 0));
        assertNull(sphere.findIntersections(ray));
    }
}