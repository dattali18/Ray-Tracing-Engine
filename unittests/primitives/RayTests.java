package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {

    /**
     * Test method for {@link Ray#getPoint(double)}
     */
    @Test
    void getPointTest() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        assertEquals(new Point(0, 0, 0), ray.getPoint(0.0d));

        assertEquals(new Point(3.5, 0, 0), ray.getPoint(3.5));
    }

    /**
     * Test method for {@link Ray#findClosestPoint(List)}
     */
    @Test
    void findClosestPointTest() {
        Ray ray  = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        Point p1 = new Point(5, 0, 0);
        Point p2 = new Point(2, 0, 0);
        Point p3 = new Point(-4, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC1. the closest point is in the middle of the list
        List<Point> list1 = new ArrayList<>();
        list1.add(p1);
        list1.add(p2);
        list1.add(p3);

        assertEquals(p2, ray.findClosestPoint(list1));


        // =============== Boundary Values Tests ==================
        // TC2. the list is empty
        List<Point> list2 = null;

        assertNull(ray.findClosestPoint(list2));

        // TC3. the closest point is at the start of the list
        List<Point> list3 = new ArrayList<>();
        list3.add(p2);
        list3.add(p1);
        list3.add(p3);

        assertEquals(p2, ray.findClosestPoint(list3));

        // TC4. the closet point is at the end of the list
        List<Point> list4 = new ArrayList<>();
        list4.add(p1);
        list4.add(p3);
        list4.add(p2);

        assertEquals(p2, ray.findClosestPoint(list4));

    }
}