package renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import primitives.*;

import java.util.List;

/**
 * Testing Camera Class
 *
 * @author Dan
 *
 */
class CameraTests {
    // ============ Ctor Tests ==============
    @Test
    @DisplayName("testing the ctor")
    void testingTheCtor() {
        assertThrows(IllegalArgumentException.class,() -> new Camera(new Point(0, 0, 0), new Vector(1, 0, 0), new Vector(-1, 1, 0)));

    }


    // ============ Rays Constructions Tests ==============
    static final Point ZERO_POINT = new Point(0, 0, 0);

    /**
     * Test method for
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testConstructRay() {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(10);
        String badRay = "Bad ray";

        // ============ Equivalence Partitions Tests ==============
        // EP01: 4X4 Inside (1,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(1, -1, -10)),
            camera.setVPSize(8, 8).constructRay(4, 4, 1, 1), badRay);

        // =============== Boundary Values Tests ==================
        // BV01: 3X3 Center (1,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(0, 0, -10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 1, 1), badRay);

        // BV02: 3X3 Center of Upper Side (0,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(0, -2, -10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 1, 0), badRay);

        // BV03: 3X3 Center of Left Side (1,0)
        assertEquals(new Ray(ZERO_POINT, new Vector(2, 0, -10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 0, 1), badRay);

        // BV04: 3X3 Corner (0,0)
        assertEquals(new Ray(ZERO_POINT, new Vector(2, -2, -10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 0, 0), badRay);

        // BV05: 4X4 Corner (0,0)
        assertEquals(new Ray(ZERO_POINT, new Vector(3, -3, -10)),
                camera.setVPSize(8, 8).constructRay(4, 4, 0, 0), badRay);

        // BV06: 4X4 Side (0,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(1, -3, -10)),
                camera.setVPSize(8, 8).constructRay(4, 4, 1, 0), badRay);

    }

    @Test
    void rotateTest() {
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1));
        Camera rotatedCamera = new Camera(new Point(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 1));

        assertEquals(rotatedCamera, camera.rotate(0, 0, 90));

//        System.out.println(camera.rotate(45, 45, 45));
    }

    @Test
    @DisplayName("testing constructing rays")
    void testingConstructingRays() {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(10)
                .setVPSize(8, 8)
                .setAliasRays(15);
        List<Ray> rays = camera.constructRays(4, 4, 1, 0);

        Ray ray1 = camera.constructRay(4, 4, 1, 0);

        System.out.println(ray1.getDir());

        System.out.println(rays.size());

        for(Ray ray: rays) {
            System.out.println(ray.getDir());
        }
    }
}
