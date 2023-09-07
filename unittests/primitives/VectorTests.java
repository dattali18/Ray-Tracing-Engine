package primitives;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Testing unit for {@link Vector} class
 * @author danielattali, itiskvales
 */
class VectorTests {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    Vector vr = v1.crossProduct(v3);

    /**
     * Test method for {@link Vector#Vector(double x, double y, double z)}
     */
    @Test
    void CtorTest() {
        // =============== Boundary Values Tests ==================
        // TC1. testing for 0'th vector, for the exception
        assertThrows(IllegalArgumentException.class, () ->new Vector(0, 0, 0));
    }


    /**
     * Test method for {@link Vector#equals(Object)}
     */
    @Test
    void testEqualsTest() {
        // ============ Equivalence Partitions Tests ==============
        testingIfAVectorEqualsToItself();
        testingIfTwoVectorAreNotEquals();
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing if a vector equals to itself")
    void testingIfAVectorEqualsToItself() {
        // TC1. testing if v==v is true
        assertTrue(v1.equals(v1));
    }

    @Test
    @DisplayName("testing if two vector are not equals")
    void testingIfTwoVectorAreNotEquals() {
        // TC2. testing if v1 == v2 return false
        assertFalse(v1.equals(v2));
    }

    /**
     * Test method for {@link Vector#add(Vector)}
     */
    @Test
    void addTest() {
        // =============== Boundary Values Tests ==================
        testingIfAVectorPlusMinusItselfIsTheZeroVector();

        // ============ Equivalence Partitions Tests ==============
        testingForTwoRandomVector();
        testingIfV1PlusV2EqualsV2PlusV1();

    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing if a vector plus minus itself is the zero vector")
    void testingIfAVectorPlusMinusItselfIsTheZeroVector() {
        // TC1. testing is v1 - v1 = 0 throw an exception
        assertThrows(IllegalArgumentException.class,()-> v1.add(new Vector(-1, -2, -3)));
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing for two random vector")
    void testingForTwoRandomVector() {
        // TC2. testing v1 + v2
        assertEquals(new Vector(-1, -2, -3), v1.add(v2));
    }

    @Test
    @DisplayName("testing if v1 plus v2 equals v2 plus v1")
    void testingIfV1PlusV2EqualsV2PlusV1() {
        // TC3. testing if v1 + v2 == v2 + v1
        assertEquals(v1.add(v2), v2.add(v1));
    }

    /**
     * Test method for {@link Vector#scale(double)}
     */
    @Test
    void scaleTest() {
        // =============== Boundary Values Tests ==================
        testingForScalingBy0();

        // ============ Equivalence Partitions Tests ==============
        testingForScalingByMinus1();
    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing for scaling by 0")
    void testingForScalingBy0() {
        // TC1. testing for 0*v1 throw an exception
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0));
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing for scaling by minus 1")
    void testingForScalingByMinus1() {
        // TC2. testing for -1*v = -v
        assertEquals(new Vector(-1, -2, -3), v1.scale(-1));
    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)}
     */
    @Test
    void dotProductTest() {
        // =============== Boundary Values Tests ==================
        testingForOrthogonalVector();

        // ============ Equivalence Partitions Tests ==============
        testingDotProductForSomeRandomVectors();
    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing for orthogonal vector")
    void testingForOrthogonalVector() {
        // TC1. test for orthogonal vector
        assertTrue(isZero(v1.dotProduct(v3)));
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing dot product for some random vectors ")
    void testingDotProductForSomeRandomVectors() {
        // TC2. test for some vector
        assertTrue(isZero(v1.dotProduct(v2) + 28));
    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)}
     */
    @Test
    void crossProductTest() {
        // =============== Boundary Values Tests ==================
        testingForParallelVectorThrowsAnException();

        // ============ Equivalence Partitions Tests ==============
        testingForCrossProductBetweenTwoRandomPoints();
        testingIfDotProductWithCrossProductReturnAZeroVector();
    }

    // =============== Boundary Values Tests ==================
    @Test
    @DisplayName("testing for parallel vector throws an exception")
    void testingForParallelVectorThrowsAnException() {
        // TC1. test for parallel vector throw exception
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2));
    }

    // ============ Equivalence Partitions Tests ==============
    @Test
    @DisplayName("testing for cross product between two random points")
    void testingForCrossProductBetweenTwoRandomPoints() {
        // TC2. test for some vector
        assertTrue(isZero(vr.length() - v1.length() * v3.length()));
    }

    @Test
    @DisplayName("testing if dot product with cross product return a zero vector")
    void testingIfDotProductWithCrossProductReturnAZeroVector() {
        // TC3. test if (v1 x v2)*v1 = 0
        assertTrue((isZero(vr.dotProduct(v1))));
    }

    /**
     * Test method for {@link Vector#lengthSquared()}
     */
    @Test
    void lengthSquaredTest() {
        // ============ Equivalence Partitions Tests ==============
        // TC1. testing for the length^2
        assertTrue(isZero(v1.lengthSquared() - 14));
    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    void lengthTest() {
        // ============ Equivalence Partitions Tests ==============
        // TC1. testing for the length of the vector
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5));
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    void normalizeTest() {
        // ============ Equivalence Partitions Tests ==============
        // TC1. testing if |v| = 1
        assertTrue(isZero(v1.normalize().length() - 1));
    }

    @Test
    @DisplayName("testing dot product")
    void testingDotProduct() {
        Vector v1 = new Vector(0, 0, 1);
        Vector v2 = new Vector(0, -1, 1).normalize();

        double value = v1.dotProduct(v2);
        double alpha = Math.toRadians(45);

        System.out.println(value);
        System.out.println(alpha);
    }

    /**
     * Test method for {@link Vector#rotate(double, char)}
     */
    @Test
    void rotateTest() {
        Vector v1 = new Vector(1, 1, 0);
        Vector v2 = v1.rotate(45, 'z').rotate(-45, 'z');

        assertEquals(v1, v2);
    }
}