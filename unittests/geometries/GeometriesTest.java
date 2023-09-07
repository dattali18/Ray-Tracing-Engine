package geometries;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    // =============== Intersections Tests ==================
    Geometries geo1 = new Geometries();

    Geometries geo2 = new Geometries(
            new Plane(new Point(1, 1, 0), new Vector(0, 0, 1)),
            new Triangle(
                    new Point(2, 2, 1),
                    new Point(1, 0, 1),
                    new Point(0, 1, 1)
            ),
            new Sphere(1, new Point(1, 1, 3))

    );

    Ray ray1 = new Ray(new Point(-10, -10, 0), new Vector(1, 1 ,0));
    Ray ray2 = new Ray(new Point(0, 0, 3), new Vector(1, 1 ,0));
    Ray ray3 = new Ray(new Point(1, 1, -1), new Vector(0, 0 ,1));
    Ray ray4 = new Ray(new Point(-1, -1, -1), new Vector(1, 1 ,2));


    /**
     * Test method for {@link Geometries#findIntersections(Ray)}
     */
    @Test
    void findIntersectionsTest() {
        // =============== Boundary Values Tests ==================

        // TC1. the list is empty
        testingWhenTheCompositeIsEmpty();

        // TC2. the ray is not intersecting with any of the item
        testingWhenTheRayIsIntersectingWithOnlyOneItem();

        // TC3. the ray is intersecting with only one item
        testingWhenTheRayIsIntersectingWithAllTheItems();

        // TC4. the ray is intersecting with all the item
        testingWhenTheRayIsIntersectingWithNoneOfTheItems();

        // ============ Equivalence Partitions Tests ==============
        // TC5. the ray is intersecting with a few of the item
        testingWhenTheRayIsIntersectingWithSomeOfTheItems();
    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing when the composite is empty")
    void testingWhenTheCompositeIsEmpty() {
        assertNull(geo1.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 0, 0))));
    }

    @Test
    @DisplayName("testing when the ray is intersecting with only one item")
    void testingWhenTheRayIsIntersectingWithOnlyOneItem() {
        assertEquals(2, geo2.findIntersections(ray2).size());
    }

    @Test
    @DisplayName("testing when the ray is intersecting with all the items")
    void testingWhenTheRayIsIntersectingWithAllTheItems() {
        assertEquals(3, geo2.findIntersections(ray3).size());
    }

    @Test
    @DisplayName("testing when the ray is intersecting with none of the items")
    void testingWhenTheRayIsIntersectingWithNoneOfTheItems() {
        assertNull(geo2.findIntersections(ray1));
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing when the ray is intersecting with some of the items")
    void testingWhenTheRayIsIntersectingWithSomeOfTheItems() {
        assertEquals(1, geo2.findIntersections(ray4).size());
    }

    @Test
    @DisplayName("testing find intersection with max distance")
    void testingFindIntersectionWithMaxDistance() {
        assertNull(geo2.findGeoIntersections(ray2, 0.0d));
    }
}
