package primitives;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Point.generatePoints;


/**
 * Testing unit for {@link Point} class
 * @author danielattali, itiskvales
 */
class PointTests {
    Point p1 = new Point(0,1 ,1);
    Point p2 = new Point(0, 1, 1);
    Point p3 = new Point(0, 2, 1);
    Point p4 = new Point(0, 0, 0);
    Point p5 = new Point(3, 4, 0);


    /**
     * Test method for {@link primitives.Point#equals(Object)}
     */
    @Test
    void testEqualsTest() {
        // ============ Equivalence Partitions Tests ==============
        testingTheEqualsFunctionWhenThePointAreTheSame();
        testingTheEqualsFunctionWhenThePointAreNotTheSame();
    }


// ============ Equivalence Partitions Tests ==============

    @Test
    @DisplayName("testing the equals function when the point are the same")
    void testingTheEqualsFunctionWhenThePointAreTheSame() {
        // TC1. the point are equals
        assertTrue(p1.equals(p2));

    }

    @Test
    @DisplayName("testing the equals function when the point are not the same")
    void testingTheEqualsFunctionWhenThePointAreNotTheSame() {
        // TC2. where they aren't
        assertFalse(p1.equals(p3));
    }

    /**
     * Test method for {@link Point#add(Vector)}
     */
    @Test
    void addTest() {
        // testing for the addition of a vector to a point

        // ============ Equivalence Partitions Tests ==============
        testingTheAdditionOfTheVectorToAPoint();
        testingTheAdditionOfAVectorToAPoint();
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing the addition of the vector to a point")
    void testingTheAdditionOfTheVectorToAPoint() {
        // TC1. the addition of the vector to the point is the right point
        assertEquals(new Point(0, 2, 2), p1.add(new Vector(0, 1, 1)));

    }

    @Test
    @DisplayName("testing the addition of a vector to a point")
    void testingTheAdditionOfAVectorToAPoint() {
        // TC2. the addition of the vector to the point is the wrong point
        assertNotEquals(new Point(0, 2, 2), p1.add(new Vector(0, 1, 0)));
    }


    /**
     * Test method for {@link Point#subtract(Point)}
     */
    @Test
    void subtractTest() {
        // testing for the subtraction of two points

        // =============== Boundary Values Tests ==================
        testingTheSubtractionOfAPointToItselfGiveTheZeroVector();

        // ============ Equivalence Partitions Tests ==============
        testingTheSubtractionOfAPointToAnotherPointGivesBackTheRightVector();

    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing the subtraction of a point to itself give the zero vector")
    void testingTheSubtractionOfAPointToItselfGiveTheZeroVector() {
        // TC1. the subtraction of a point to itself give the 0 vector
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "the function didn't catch the exception of a 0 vector");
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing the subtraction of a point to another point gives back the right vector")
    void testingTheSubtractionOfAPointToAnotherPointGivesBackTheRightVector() {
        // TC2. the subtraction of a point to another point gives the right vector
        assertEquals(new Vector(0, -1, 0), p1.subtract(p3));

    }


    /**
     * Test method for {@link Point#distanceSquared(Point)}
     */
    @Test
    void distanceSquaredTest() {

        //testing for the distanceSquared of two points [d*d]

        // =============== Boundary Values Tests ==================
        testingTheDistanceSquaredOfAPointToItself();

        // ============ Equivalence Partitions Tests ==============
        testingTheDistanceSquaredBetweenTwoPoints();
        testingIfDistanceFormAToBIsEqualsToFromBToA();
    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing the distance squared of a point to itself")
    void testingTheDistanceSquaredOfAPointToItself() {
        // TC1. testing the distance^2 of a point to itself
        assertEquals(0.0, p1.distanceSquared(p1));
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing the distance squared between two points")
    void testingTheDistanceSquaredBetweenTwoPoints() {
        // TC2. testing the distance^2 between to points
        assertEquals(25.0, p4.distanceSquared(p5));
    }

    @Test
    @DisplayName("testing if distance form a to b is equals to from b to a")
    void testingIfDistanceFormAToBIsEqualsToFromBToA() {
        // TC3. testing if the d(p1, p2) == d(p2, p1)
        assertEquals(p1.distanceSquared(p5), p5.distanceSquared(p1));
    }




    /**
     * Test method for {@link Point#distance(Point)}
     */
    @Test
    void distanceTest() {
        // testing for the distance between two points [d]

        // =============== Boundary Values Tests ==================
        testingTheDistanceFromAPointToItself();

        // ============ Equivalence Partitions Tests ==============
        testingTheDistanceBetweenTwoPoints();
        testingIfTheDistanceFromAToBIsEqualsBToA();
    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing the distance from a point to itself")
    void testingTheDistanceFromAPointToItself() {
        // TC1. testing the distance of a point to itself
        assertEquals(0.0, p1.distance(p1));
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing the distance between two points")
    void testingTheDistanceBetweenTwoPoints() {
        // TC2. testing the distance between to points
        assertEquals(5.0, p5.distance(p4));
    }

    @Test
    @DisplayName("testing if the distance from a to b is equals b to a")
    void testingIfTheDistanceFromAToBIsEqualsBToA() {
        // TC3. testing if the d(p1, p2) == d(p2, p1)
        assertEquals(p1.distance(p5), p5.distance(p1));
    }


    // =============== Testing Point Generation ================
    @Test
    @DisplayName("testing point generation")
    void testingPointGeneration() {
        Vector vX = new Vector(1, 0, 0);
        Vector vY = new Vector(0, -1, 0);

        Point center = new Point(0, 0, 0);

        double size = 10.0d;

        int amount = 10;

        List<Point> points = generatePoints(vX, vY, amount, center, size);

        System.out.println(points.size());

        for(Point p : points) {
            System.out.println(p);
            assertTrue(p.distanceSquared(center) < size * size);
        }
    }

}