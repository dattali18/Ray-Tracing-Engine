package geometries;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing unit for {@link Plane} class
 * @author danielattali, itiskvales
 */
class PlaneTests {

    // ============ Ctor Tests ==============
    /**
     * Test method for {@link Plane#Plane(Point, Point , Point)}
     */
    @Test
    @DisplayName("ctor with 3 point test")
    void ctorWith3PointTest() {
        // =============== Boundary Values Tests ==================
        testingTheCtorWithTwoEqualPoints();
        testingTheCtorWithAllThePointOnTheSameLine();


    }
    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing the ctor with two equal points")
    void testingTheCtorWithTwoEqualPoints() {
        // TC1. testing the ctor with 2 identical point
        assertThrows(IllegalArgumentException.class, () -> new Plane(
                new Point(0, 0, 0),
                new Point(0, 0, 0),
                new Point(0, 1, 2)
        ));
    }

    @Test
    @DisplayName("testing the ctor with all the point on the same line")
    void testingTheCtorWithAllThePointOnTheSameLine() {
        // TC2. testing the ctor when all the point are on the same line
        assertThrows(IllegalArgumentException.class, ()-> new Plane(
                new Point(0, 0, 1),
                new Point(0, 0, 2),
                new Point(0, 0, 3)
        ));
    }

    // ============ GetNormal Tests ==============
    /**
     * Test method for {@link Plane#getNormal(Point)}
     */
    @Test
    void getNormalTest() {
        // ============ Equivalence Partitions Tests ==============
        testingTheGetNormalWithSomeRandomPointOnThePlane();
        testingIfTheNormalIsNormalized();
    }

    Plane plane = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
    Vector normal = plane.getNormal(new Point(0, 0, 0));

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing the get normal with some random point on the plane")
    void testingTheGetNormalWithSomeRandomPointOnThePlane() {
        // TC1.1 testing if the getNormal give back the right vector
        assertEquals(new Vector(0, 0, 1), normal);
    }

    @Test
    @DisplayName("testing if the normal is normalized")
    void testingIfTheNormalIsNormalized() {
        // TC1.2 testing if the normal is always a normalised vector
        assertTrue(isZero(plane.getNormal(new Point(0, 1, 0)).length() - 1.0));
    }

    // =============== Intersections Tests ==================
    Plane plane1 = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));

    Ray ray1 = new Ray(new Point(0, 0, -1), new Vector(0, 0, 1)); // TC1.1
    Ray ray2 = new Ray(new Point(1, 1, 1), new Vector(1, 0, -1));   // TC1.2

    Ray ray3 = new Ray(new Point(0, 0, 1), new Vector(1, 1, -2));   // TC2.1
    Ray ray4 = new Ray(new Point(0, 0, 2), new Vector(1, 1, -2));   // TC2.2

    Ray ray5 = new Ray(new Point(0, 0, -1), new Vector(1, 1, 1));   // TC3.1
    Ray ray6 = new Ray(new Point(0, 0, 1), new Vector(1, 1, 1));   // TC3.2
    Ray ray7 = new Ray(new Point(0, 0, 2), new Vector(1, 1, 1));   // TC3.3
    Ray ray8 = new Ray(new Point(0, 0, -1), new Vector(-1, -1, -1));   // TC3.4


    Ray ray9 = new Ray(new Point(1, 0, 0), new Vector(1, 5, -1));   // TC4.


    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC1. a simple plane ray intersection no parallel and no orthogonal ray to the plane

        // TC1.1 the ray intersect
        testingRayIntersectPlane1Intersection();
        // TC1.2 the ray doesn't intersect
        testingRayDontIntersectPlane0Intersection();

        // =============== Boundary Values Tests ==================

        // TC2. the ray is parallel to the plane

        // TC2.1 the ray is in the plane => inf amount of point, so we need to return  null
        testingRayIsParallelAndInThePlane0Intersection();
        // TC2.2 the ray is not in the plane
        testingTheRayIsParallelAndNotIntThePlane0Intersection();

        // TC3. the ray is orthogonal to the plane
        // TC3.1 the starting point of the ray is before (1 intersection)
        testingTheRayStartBeforeThePlane1Intersection();
        // TC3.2 the starting point of the ray is in the plane (0 intersection)
        testingTheRayStartAtThePlane0Intersection();
        // TC3.3 the starting point of the ray is after the plane (0 intersection)
        testingTheRayStartAfterThePlane0Intersection();
        // TC3.4 the starting point is before and dir = -normal (0 intersection)
        testingTheRayIsStartingBeforeButTheDirIsInverted0Intersection();

        // TC4. the string point of the ray is the same point of the plane
        testingTheStartingPointOfTheRayIsTheReferencePointOfThePlane0Intersection();
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing ray intersect plane 1 intersection")
    void testingRayIntersectPlane1Intersection() {
        assertEquals(new Point(0, 0, 1.0d), plane1.findIntersections(ray1).get(0));
    }

    @Test
    @DisplayName("testing ray dont intersect plane 0 intersection")
    void testingRayDontIntersectPlane0Intersection() {
        assertNull(plane1.findIntersections(ray2));
    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing ray is parallel and in the plane 0 intersection")
    void testingRayIsParallelAndInThePlane0Intersection() {
        assertNull(plane1.findIntersections(ray3));
    }

    @Test
    @DisplayName("testing the ray is parallel and not int the plane 0 intersection")
    void testingTheRayIsParallelAndNotIntThePlane0Intersection() {
        assertNull(plane1.findIntersections(ray4));
    }

    @Test
    @DisplayName("testing the ray start before the plane 1 intersection")
    void testingTheRayStartBeforeThePlane1Intersection() {
        assertEquals(new Point(0.666666666666666, 0.666666666666666, -0.33333333333333326), plane1.findIntersections(ray5).get(0));
    }

    @Test
    @DisplayName("testing the ray start at the plane 0 intersection")
    void testingTheRayStartAtThePlane0Intersection() {
        assertNull(plane1.findIntersections(ray6));
    }

    @Test
    @DisplayName("testing the ray start after the plane 0 intersection")
    void testingTheRayStartAfterThePlane0Intersection() {
        assertNull(plane1.findIntersections(ray7));
    }

    @Test
    @DisplayName("testing the ray is starting before but the dir is inverted 0 intersection")
    void testingTheRayIsStartingBeforeButTheDirIsInverted0Intersection() {
        assertNull(plane1.findIntersections(ray8));
    }

    // reference
    @Test
    @DisplayName("testing the starting point of the ray is the reference point of the plane 0 intersection")
    void testingTheStartingPointOfTheRayIsTheReferencePointOfThePlane0Intersection() {
        assertNull(plane1.findIntersections(ray9));
    }

    @Test
    @DisplayName("testing find intersection with max distance")
    void testingFindIntersectionWithMaxDistance() {
        assertNull(plane1.findGeoIntersections(ray5, 0.0d));
    }
}