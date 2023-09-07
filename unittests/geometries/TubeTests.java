package geometries;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing unit for {@link Tube} class
 * @author danielattali, itiskvales
 */
class TubeTests {

    Tube tube = new Tube(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));

    /**
     * Test method for {@link Tube#getNormal(Point)}
     */
    @Test
    void getNormalTest() {
        // =============== Boundary Values Tests ==================
        testingTheNormalAtTheStartingPoint();

        // ============ Equivalence Partitions Tests ==============
        testingANormalAtSomeRandomPoint();
    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing the normal at the starting point")
    void testingTheNormalAtTheStartingPoint() {
        // TC1. testing the normal at the staring point
        Vector n1 = tube.getNormal(new Point(0, 1, 0));
        assertEquals(new Vector(0, 1, 0), n1);
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing a normal at some random point")
    void testingANormalAtSomeRandomPoint() {
        // TC2. testing a normal at some random point
        Vector n2 = tube.getNormal(new Point(0, 1, 5));
        assertEquals(new Vector(0, 1, 0), n2);
    }

}