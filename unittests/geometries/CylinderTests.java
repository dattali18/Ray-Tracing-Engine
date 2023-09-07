package geometries;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing unit for {@link Cylinder} class
 * @author danielattali, itiskvales
 */
class CylinderTests {
    Cylinder cylinder = new Cylinder(1,
            new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)),
            10.0f
    );
    Point p1 = new Point(0,0,0);    // p1 is in the center of the base
    Point p2 = new Point(0,0.5,0);  // p2 is on the base
    Point p3 = new Point(0,1,0);    // p3 is on the edge of the base
    Point p4 = new Point(0,0,10);   // p4 is in the center of the top
    Point p5 = new Point(0,0.5,10); // p5 is on the top
    Point p6 = new Point(0,1,10);   // p6 is on the edge of the top
    Point p7 = new Point(0,1,1);    // p7 is on the round part
    Vector v1 = new Vector(0,1,0);
    Vector v2 = new Vector(0,0,1);

    /**
     * Test method for {@link Cylinder#getNormal(Point)}
     */
    @Test
    void getNormalTest() {
        // ============ Equivalence Partitions Tests ==============
        testingAPointOnHteRoundPart();
        testingAPointOnTheBase();
        testingAPointOnTheTop();

        // =============== Boundary Values Tests ==================
        testingAPointOnTheBaseEdge();
        testingAPointOnTheTopEdge();
        testingAPointInTheCenterOfTheBase();
        testingAPointInTheCenterOfTheTop();
    }


    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing a point on hte round part")
    void testingAPointOnHteRoundPart() {
        // TC1. a point on the round part
        assertEquals(v1, cylinder.getNormal(p7));
    }

    @Test
    @DisplayName("testing a point on the base")
    void testingAPointOnTheBase() {
        //         TC2. a point on the base
        assertEquals(v2, cylinder.getNormal(p2));
    }
    @Test
    @DisplayName("testing a point on the top")
    void testingAPointOnTheTop() {
        // TC3. a point on the top
        assertEquals(v2, cylinder.getNormal(p5));
    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing a point on the base edge")
    void testingAPointOnTheBaseEdge() {
        // TC4. a point on the base edge
        assertEquals(v2, cylinder.getNormal(p3));
    }

    @Test
    @DisplayName("testing a point on the top edge")
    void testingAPointOnTheTopEdge() {
        // TC5. a point on the top edge
        assertEquals(v2, cylinder.getNormal(p6));
    }

    @Test
    @DisplayName("testing a point in the center of the base")
    void testingAPointInTheCenterOfTheBase() {
        // TC6. a point in the center of the base
        assertEquals(v2, cylinder.getNormal(p1));
    }

    @Test
    @DisplayName("testing a point in the center of the top")
    void testingAPointInTheCenterOfTheTop() {
        // TC7. a point in the center of the top
        assertEquals(v2, cylinder.getNormal(p4));
    }
}