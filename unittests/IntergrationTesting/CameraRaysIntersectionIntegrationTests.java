package IntergrationTesting;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.Camera;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CameraRaysIntersectionIntegrationTests {

    Sphere sphere1 = new Sphere(1, new Point(3, 0, 0)); // 2
    Sphere sphere2 = new Sphere(2.5, new Point(3, 0, 0)); // 18
    Sphere sphere3 = new Sphere(2, new Point(2.5, 0, 0)); // 10
    Sphere sphere4 = new Sphere(4, new Point(2, 0, 0)); // 9
    Sphere sphere5 = new Sphere(1, new Point(-2, 0, 0)); // 0

    Plane plane1 = new Plane(new Point(2, 0, 0), new Vector(1, 0, 0)); // 9
    Plane plane2 = new Plane(new Point(2, 0, 0), new Vector(1, 1, 0)); // 9
    Plane plane3 = new Plane(new Point(2, 0, 0), new Vector(1, 5, 0)); // 9

    Camera camera = new Camera(
            new Point(0, 0, 0),
            new Vector(1, 0, 0),
            new Vector(0, 0, 1)
    )
            .setVPSize(3, 3)
            .setVPDistance(1);

    List<Point> pointList;
    List<Ray> rays = constructAllRay(camera, 3, 3);

    /**
     * testing method for testing integration between the Camera's constructRay and Intersectable findIntersection methods
     */
    @Test
    @DisplayName("testing Integration")
    void testingIntegration() {

        // TC1. a small sphere there is 2 intersections
        testingTwoIntersectionsSphere();

        // TC2. a bigger sphere there is 18 intersections
        testing18IntersectionsSphere();

        // TC3. a sphere there is 10 intersections
        testing10IntersectionsSphere();

        // TC4. the camera is in the sphere there is 9 intersection
        testing9IntersectionsSphere();

        // TC5. the sphere is on the other side of the camera
        testingZeroIntersectionSphere();

        // TC6. the plane is parallel to the view plane 9 intersections
        testingParallelPlaneToTheViewPlane9Intersections();

        // TC7. the plane is a bit tilted compare to the view plane 9 intersections
        testingTiltedPlaneToTheViewPlane9Intersections();

        // TC8. the plane is tilted compare to the view plane 6 intersections
        testingTiltedPlaneToTheViewPlane6Intersections();
    }

    @Test
    @DisplayName("testing two intersections sphere")
    void testingTwoIntersectionsSphere() {
        List<Point> pointList = findAllIntersection(rays, sphere1);
        assertEquals(2,pointList.size());

    }
    @Test
    @DisplayName("testing 18 intersections sphere")
    void testing18IntersectionsSphere() {
        pointList = findAllIntersection(rays, sphere2);
        assertEquals(18,pointList.size());
    }

    @Test
    @DisplayName("testing 10 intersections sphere")
    void testing10IntersectionsSphere() {
        pointList = findAllIntersection(rays, sphere3);
        assertEquals(10,pointList.size());
    }

    @Test
    @DisplayName("testing 9 intersections sphere")
    void testing9IntersectionsSphere() {
        pointList = findAllIntersection(rays, sphere4);
        assertEquals(9,pointList.size());
    }

    @Test
    @DisplayName("testing zero intersection sphere")
    void testingZeroIntersectionSphere() {
        pointList = findAllIntersection(rays, sphere5);
        assertEquals(0,pointList.size());
    }

    @Test
    @DisplayName("testing parallel plane to the view plane 9 intersections")
    void testingParallelPlaneToTheViewPlane9Intersections() {
        pointList = findAllIntersection(rays, plane1);
        assertEquals(9,pointList.size());
    }

    @Test
    @DisplayName("testing tilted plane to the view plane 9 intersections")
    void testingTiltedPlaneToTheViewPlane9Intersections() {
        pointList = findAllIntersection(rays, plane2);
        assertEquals(9,pointList.size());
    }

    @Test
    @DisplayName("testing tilted plane to the view plane 6 intersections")
    void testingTiltedPlaneToTheViewPlane6Intersections() {
        pointList = findAllIntersection(rays, plane3);
        assertEquals(6,pointList.size());
    }


    List<Point> findAllIntersection(List<Ray> rays, Intersectable obj) {
        List<Point> pointList = new ArrayList<>();

        for (Ray ray: rays) {
            List<Point> res = obj.findIntersections(ray);
            if(res != null)
                pointList.addAll(res);
        }
        return pointList;
    }

    List<Ray> constructAllRay(Camera camera, int nX, int nY) {
        List<Ray> rays = new ArrayList<>();
        double h = camera.getHeight();
        double w = camera.getWidth();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                rays.add(camera.constructRay(nX, nY, j, i));
            }
        }
        return rays;
    }

}
