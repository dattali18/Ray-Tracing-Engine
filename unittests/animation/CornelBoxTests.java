package animation;

import geometries.*;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;

public class CornelBoxTests {
    Material reflactiveMaterial = new Material().setKD(0.5).setKS(0.9).setNShininess(32).setKR(1);
    Material difusiveMaterial = new Material().setKD(1).setKS(0.8).setNShininess(30);

    int rays = 64;

    Material mat1 = new Material().setKD(0.5).setKS(1).setNShininess(32).setKR(0.2)
            .setConeAngleReflected(30).setNumRaysReflected(rays);
    Material mat2 = new Material().setKD(0.5).setKS(1).setNShininess(42).setKR(0.2)
            .setConeAngleReflected(60).setNumRaysReflected(rays);
    Material mat3 = new Material().setKD(0.5).setKS(1).setNShininess(42).setKR(0.2)
            .setConeAngleReflected(90).setNumRaysReflected(rays);

    Material mat4 = new Material().setKD(1).setKS(0.4).setNShininess(32).setKT(0.95)
            .setNumRaysRefracted(rays).setConeAngleRefracted(15);
    Material mat5 = new Material().setKD(1).setKS(0.4).setNShininess(32).setKT(0.85)
            .setNumRaysRefracted(rays).setConeAngleRefracted(30);
    Material mat6 = new Material().setKD(1).setKS(0.4).setNShininess(32).setKT(0.85)
            .setNumRaysRefracted(rays).setConeAngleRefracted(45);

    Material mat7 = new Material().setKD(1).setKS(0.4).setNShininess(32).setKT(0.95)
            .setNumRaysRefracted(rays).setConeAngleRefracted(60);

    Material mat8 = new Material().setKD(1).setKS(0.4).setNShininess(32).setKT(0.9)
            .setNumRaysRefracted(rays).setConeAngleRefracted(5);

    Sphere sphere1 = (Sphere) new Sphere(new Point(100, -70, -20), 25)
            .setMaterial(mat1)
            .setEmission(new Color(WHITE).scale(0.7));

    Sphere sphere2 = (Sphere) new Sphere(new Point(0, -30, 0), 20)
            .setMaterial(mat1)
            .setEmission(new Color(BLACK));

    Sphere sphere3 = (Sphere) new Sphere(new Point(-100, -70, -20), 25)
            .setMaterial(mat3)
            .setEmission(new Color(WHITE).scale(0.7));

    Sphere sphere4 = (Sphere) new Sphere(new Point(40, -70, -60), 40)
            .setMaterial(reflactiveMaterial)
            .setEmission(new Color(10, 10, 10));

    Material material = difusiveMaterial.setKS(0.8);
    Sphere sphere5 = (Sphere) new Sphere(new Point(15, -15, -90), 10)
            .setMaterial(difusiveMaterial)
            .setEmission(new Color(135,144, 242).scale(0.8));

    Point[] points1 = {
            new Point(100, -20, 30),
            new Point(50, -20, 30),
            new Point(100, -20, -40),
            new Point(50, -20, -40)
    };
    Triangle[] window1 = {
            (Triangle) new Triangle(points1[0], points1[1], points1[2])
                    .setMaterial(mat4),
            (Triangle) new Triangle(points1[1], points1[2], points1[3])
                    .setMaterial(mat4)
    };

    Point[] points2 = {
            new Point(25, -20, 30),
            new Point(-25, -20, 30),
            new Point(25, -20, -40),
            new Point(-25, -20, -40)
    };
    Triangle[] window2 = {
            (Triangle) new Triangle(points2[0], points2[1], points2[2])
                    .setMaterial(mat5),
            (Triangle) new Triangle(points2[1], points2[2], points2[3])
                    .setMaterial(mat5)
    };

    Point[] points3 = {
            new Point(-100, -20, 30),
            new Point(-50, -20, 30),
            new Point(-100, -20, -40),
            new Point(-50, -20, -40)
    };
    Triangle[] window3 = {
            (Triangle) new Triangle(points3[0], points3[1], points3[2])
                    .setMaterial(mat6),
            (Triangle) new Triangle(points3[1], points3[2], points3[3])
                    .setMaterial(mat6)
    };

    Plane plane1 = (Plane) new Plane(new Point(-150, 0, 0), new Vector(1, 0, 0))
            .setMaterial(difusiveMaterial)
            .setEmission(new Color(RED).scale(0.5));
    Plane plane2 = (Plane) new Plane(new Point(150, 0, 0), new Vector(-1, 0, 0))
            .setMaterial(difusiveMaterial)
            .setEmission(new Color(BLUE).scale(0.5));
    Plane plane3 = (Plane) new Plane(new Point(0, 0, 150), new Vector(0, 0, -1))
            .setMaterial(difusiveMaterial)
            .setEmission(new Color(WHITE).scale(0.5));
    Plane plane4 = (Plane) new Plane(new Point(0, 0, -150), new Vector(0, 0, 1))
            .setMaterial(difusiveMaterial)
            .setEmission(new Color(WHITE).scale(0.5));
    Plane plane5 = (Plane) new Plane(new Point(0, -150, 0), new Vector(0, 1, 0))
            .setMaterial(difusiveMaterial)
            .setEmission(new Color(WHITE).scale(0.5));

    Plane plane6 = (Plane) new Plane(new Point(0, 150, 0), new Vector(0, -1, 0))
            .setMaterial(difusiveMaterial)
            .setEmission(new Color(BLACK));

    Cylinder cylinder = (Cylinder) new Cylinder(10, new Ray(new Point(-50, -40, -100), new Vector(0, 0, 1)), 70)
            .setMaterial(new Material().setKS(0.5).setKD(0.5).setNShininess(30))
            .setEmission(new Color(147, 54, 180));

    Cylinder cylinderLight = (Cylinder) new Cylinder(15, new Ray(new Point(0, -50, 100), new Vector(0, 0, -1)), 5)
            .setMaterial(new Material().setKD(1).setKS(0.6).setNShininess(30).setKT(1))
            .setEmission(new Color(BLACK));

    Vector u = new Vector(1,1,0).normalize(), v = new Vector(1, -1, 0).normalize(), w = new Vector(0, 0, 1).normalize();
    Point p0 = new Point(-55, 30, -100);
    Point p1 = new Point(55, 30, -100);

    double scale = 15;
    Cube cube1 = new Cube(u, v, w, p0, scale)
            .setEmission(new Color(225, 50, 153))
            .setMaterial(new Material().setKD(1).setKS(0.9).setNShininess(32));
    Cube cube2 = new Cube(u, v.scale(-5), w, p1.add(v.scale(scale * 2)), scale - 5)
            .setEmission(new Color(57, 150, 68))
            .setMaterial(difusiveMaterial);
    Cube cube3 = new Cube
            (
                    new Vector(2, 1, 4).normalize(),
                    new Vector(2, -1,4).normalize(),
                    new Vector(-2, 0, 4).normalize(),
                    new Point(10, 40, -100),
                    15)
            .setEmission(new Color(64, 18, 139))
            .setMaterial(difusiveMaterial);

    Cube cube4 = new Cube(u, v, w, p0.add(v.scale(-scale)).add(w.scale(scale)), scale)
            .setEmission(new Color(221, 88, 214))
            .setMaterial(difusiveMaterial);

    Cube cube11 = new Cube(
            new Vector(1, 1, 0).normalize(),
            new Vector(-1, 1, 0).normalize(),
            new Vector(0, 0, 1),
            new Point(0, -70, -25),
            50)
            .setMaterial(mat5)
            .setEmission(new Color(WHITE).scale(0.4));


    private final AmbientLight ambientLight = new AmbientLight(
            new Color(WHITE),
            new Double3(0.2)
    );

    Camera camera = new Camera(new Point(0, 120, 40), new Vector(0, -1, 0), new Vector(0, 0, 1))
            .setVPSize(250, 250)
            .setVPDistance(160);

    SpotLight spotLight1 = (SpotLight) new SpotLight(new Color(WHITE), new Point(100, -40, 100), new Vector(-5, 2, -5))
            .setKL(8E-5).setKQ(8E-5);

    SpotLight spotLight2 = (SpotLight) new SpotLight(new Color(WHITE), new Point(-100, -40, 100), new Vector(5, 2, -5))
            .setKL(8E-5).setKQ(8E-5);

    Sphere sphereLight1 = (Sphere) new Sphere(new Point(0, -80, 50), 3)
            .setEmission(new Color(WHITE))
            .setMaterial(new Material().setKT(1));
    PointLight pointLight1 = new PointLight(new Color(WHITE), new Point(0, -40, 100))
            .setKL(3E-5).setKQ(3E-5);

    Scene scene = new Scene("CornelBoxTest")
            .setAmbientLight(ambientLight)
//            .addLights(spotLight1)
//            .addLights(spotLight2)
            .addLights(spotLight1.setLengthOfTheSide(15).setSoftShadowsRays(16))
            .addLights(spotLight2.setLengthOfTheSide(15).setSoftShadowsRays(16))
//            .addGeometries(sphere1, sphere2, plane1, plane2, plane3, plane4, plane5, plane6, cylinder)
            .addGeometries(plane1, plane2, plane3, plane4, plane5, plane6)
            .addGeometries(cube11.getCube())
            .addGeometries(sphere2)
//            .addGeometries(sphere1, sphere2, sphere3)
//            .addGeometries(window1)
//            .addGeometries(window2)
//            .addGeometries(window3)
//            .addGeometries(cube1.getCube())
//            .addGeometries(cube2.getCube())
//            .addGeometries(cube3.getCube())
//            .addGeometries(cube4.getCube())
            ;


    @Test
    @DisplayName("testing cornel box")
    void testingCornelBox() {
        int n = 400;

        camera
                .pitchCamera(20)
                .setAliasRays(32)
                .setImageWriter(new ImageWriter("cornel/glass20", n, n))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }


    @Test
    @DisplayName("testing cornel box animation")
    void testingCornelBoxAnimation() {
        int n = 500;

        int totalFrame = 38;
        int frame1 = 10;
        int frame2 = 10;
        int frame3 = 10;
        int frame4 = 10;


        int aliasingRays = 20;
//        for (int i = 0; i < frame1; i++) {
//            totalFrame += 1;
//
//            camera
//                    .setAliasRays(aliasingRays)
//                    .setImageWriter(new ImageWriter("cornel/animation/" + totalFrame, n, n))
//                    .setRayTracer(new RayTracerBasic(scene))
//                    .renderImage()
//                    .writeToImage();
//
//            aliasingRays += 2;
//        }
//
//
//        int softShadowRays = 20;
//        double lightRadius = 0.0;
//        for (int i = 0; i < frame2; i++) {
//            totalFrame += 1;
//
//            scene.setLight(
//                    spotLight1.setLengthOfTheSide(lightRadius).setSoftShadowsRays(softShadowRays)
//            );
//
//            camera
//                    .setAliasRays(aliasingRays)
//                    .setImageWriter(new ImageWriter("cornel/animation/" + totalFrame, n, n))
//                    .setRayTracer(new RayTracerBasic(scene))
//                    .renderImage()
//                    .writeToImage();
//
//            lightRadius += 1.5;
//
//        }

//        totalFrame = 30;
//        camera.setAliasRays(1);
//
//        int dofRays = 20;
//        double lensRadius = 0.1;
//        for (int i = 0; i < frame3; i++) {
//            totalFrame += 1;
//
//            camera
//                    .setLensRadius(lensRadius)
//                    .setFocalRays(dofRays)
//                    .setImageWriter(new ImageWriter("cornel/animation/" + totalFrame, n, n))
//                    .setRayTracer(new RayTracerBasic(scene))
//                    .renderImage()
//                    .writeToImage();
//
//            lensRadius += 1.5;
//        }

//        camera
//                .setLensRadius(0)
//                .setFocalRays(1);
//
//
//        double h = 1;
//        double angle = 0;
//
//        for (int i = 0; i < frame4; i++) {
//            totalFrame += 1;
//            angle = Math.toDegrees(Math.atan(h/camera.getDistance()));
//
//            camera
//                    .moveUp(h)
//                    .pitchCamera(angle)
//                    .setAliasRays(aliasingRays)
//                    .setImageWriter(new ImageWriter("cornel/animation/" + totalFrame, n, n))
//                    .setRayTracer(new RayTracerBasic(scene))
//                    .renderImage()
//                    .writeToImage();
//
//            h += 5;
//        }
//
//
//        camera
//                .moveUp(-h)
//                .pitchCamera(-angle);

        double rollAngle = 1;
        for (int i = 0; i < frame4; i++) {
            totalFrame += 1;

            camera
                    .rollCamera(rollAngle)
                    .setAliasRays(aliasingRays)
                    .setImageWriter(new ImageWriter("cornel/animation/" + totalFrame, n, n))
                    .setRayTracer(new RayTracerBasic(scene))
                    .renderImage()
                    .writeToImage();

            rollAngle += 3;
        }
//
//        camera.rollCamera(-rollAngle);

//        double yawAngle = -1;
//        for (int i = 0; i < frame4; i++) {
//            totalFrame += 1;
//
//            camera
//                    .yawCamera(yawAngle)
//                    .setAliasRays(aliasingRays)
//                    .setImageWriter(new ImageWriter("cornel/animation/" + totalFrame, n, n))
//                    .setRayTracer(new RayTracerBasic(scene))
//                    .renderImage()
//                    .writeToImage();
//
//            yawAngle -= 3;
//        }
    }
}
