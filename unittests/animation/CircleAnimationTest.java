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
import renderer.RayTracerBase;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class CircleAnimationTest {
    private static final int SHININESS = 402;
    private static final double  KD = 0.5;
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);
    private static final double  KS  = 0.5;
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
    private final Material material = new Material().setKD(KD3).setKS(KS3).setNShininess(SHININESS);
    private final Sphere sphere1 = (Sphere) new Sphere(
            new Point(0, 0, 0),
            30.0d
    )
            .setEmission(new Color(0, 0, 128))
            .setMaterial(new Material().setKD(KD).setKS(KS).setNShininess(SHININESS));

    private final Plane plane = (Plane) new Plane(
            new Point(0, 0, -30),
            new Vector(0, 0, 1)
    )
            .setEmission(new Color(85, 107, 47))
            .setMaterial(material);

    private final Point[] points = {
            new Point(50, 50, -30),
            new Point(50, -50, -30),
            new Point(-50, 50, -30),
            new Point(-50, -50, -30),
            new Point(0, 0, 40)
    };

    private final Triangle triangle1 = (Triangle) new Triangle(points[0], points[1], points[4])
            .setEmission(new Color(YELLOW))
            .setMaterial(material);
    private final Triangle triangle2 = (Triangle) new Triangle(points[1], points[3], points[4])
            .setEmission(new Color(RED))
            .setMaterial(material);
    private final Triangle triangle3 = (Triangle) new Triangle(points[0], points[2], points[4])
            .setEmission(new Color(GREEN))
            .setMaterial(material);
    private final Triangle triangle4 = (Triangle) new Triangle(points[3], points[2], points[4])
            .setEmission(new Color(BLUE))
            .setMaterial(material);
    private final Triangle triangle5 = (Triangle) new Triangle(points[0], points[1], points[3])
            .setMaterial(material);
    private final Triangle triangle6 = (Triangle) new Triangle(points[0], points[2], points[3])
            .setMaterial(material);

    private final Geometries geo1 = new Geometries(triangle5, triangle6);
    private final Geometries geo2 = new Geometries(geo1, triangle1, triangle2, triangle3, triangle4);

    private final PointLight pointLight1 = new PointLight(
            new Color(WHITE),
            new Point(0, 30, 20)
    )
            .setKL(0.0001)
            .setKQ(0.00002);

    private final PointLight pointLight2 = new PointLight(
            new Color(WHITE),
            new Point(0, -30, 20)
    )
            .setKL(0.001)
            .setKQ(0.0002);

    private final PointLight pointLight3 = new PointLight(
            new Color(WHITE),
            new Point(-30, 0, 20)
    )
            .setKL(0.001)
            .setKQ(0.0002);

    private final PointLight pointLight4 = new PointLight(
            new Color(WHITE),
            new Point(30, 0, 20)
    )
            .setKL(0.001)
            .setKQ(0.0002);

    private final AmbientLight ambientLight = new AmbientLight(
            new Color(WHITE),
            new Double3(0.15)
    );

    private final Scene scene = new Scene.SceneBuilder()
            .setLightSource( List.of(pointLight1, pointLight2, pointLight3, pointLight4) )
            .setAmbientLight( ambientLight )
            .setGeometries( geo2 )
            .setBackGround( new Color(50, 50, 50) )
            .setName("many sphere")
            .build();

    private final RayTracerBase rayTracer = new RayTracerBasic(scene);

    private final int frames = 1;

    private final Vector vUp = new Vector(0, 0, 1);


    @Test
    @DisplayName("testing a circle")
    void testingACircle() {

        Point[] points2 =  {
                new Point(50, 50, -50),
                new Point(50, -50, -50),
                new Point(-50, -50, -50),
                new Point(-50, 50, -50),

                new Point(50, 50, 50),
                new Point(50, -50, 50),
                new Point(-50, -50, 50),
                new Point(-50, 50, 50),
        };

        Material mat1 = material;
        Material mat2 = material.setKT(0.4).setKR(0.4);


        Triangle[] cube = {
                (Triangle) new Triangle(points2[0], points2[1], points2[5]).setMaterial(mat2).setEmission(new Color(BLACK)),
                (Triangle) new Triangle(points2[4], points2[5], points2[0]).setMaterial(mat2).setEmission(new Color(BLACK)),

                (Triangle) new Triangle(points2[1], points2[2], points2[6]).setMaterial(mat2).setEmission(new Color(BLACK)),
                (Triangle) new Triangle(points2[5], points2[6], points2[1]).setMaterial(mat2).setEmission(new Color(BLACK)),

                (Triangle) new Triangle(points2[2], points2[3], points2[6]).setMaterial(mat2).setEmission(new Color(BLACK)),
                (Triangle) new Triangle(points2[6], points2[7], points2[3]).setMaterial(mat2).setEmission(new Color(BLACK)),

                (Triangle) new Triangle(points2[0], points2[3], points2[4]).setMaterial(mat2).setEmission(new Color(BLACK)),
                (Triangle) new Triangle(points2[4], points2[7], points2[3]).setMaterial(mat2).setEmission(new Color(BLACK)),

                (Triangle) new Triangle(points2[4], points2[5], points2[6]).setMaterial(mat2).setEmission(new Color(BLACK)),
                (Triangle) new Triangle(points2[6], points2[7], points2[4]).setMaterial(mat2).setEmission(new Color(BLACK)),

                (Triangle) new Triangle(points2[0], points2[1], points2[2]).setMaterial(mat1).setEmission(new Color(BLACK)),
                (Triangle) new Triangle(points2[2], points2[3], points2[0]).setMaterial(mat1).setEmission(new Color(BLACK))

        };

        Scene scene = new Scene("triangles");

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.5));

        scene.addGeometries(
                new Cylinder(5, new Ray(new Point(-25, 0, -25), new Vector(1, 0 ,1)), 50)
                        .setEmission(new Color(200, 0,0))
                        .setMaterial(new Material().setKD(KD3).setKS(KS3).setNShininess(SHININESS))
                ,
                new Plane(new Point(0, 0, -50), new Vector(0, 0, 1))
                        .setEmission(new Color(50, 50, 50))
                        .setMaterial(new Material().setKD(KD3).setKS(KS3).setNShininess(SHININESS))
        );

//        scene.addGeometries(
//                triangle1,
//                triangle2,
//                triangle3,
//                triangle4,
//                triangle5,
//                triangle6
//        );


//        scene.addLights(
////                new PointLight(new Color(400, 400, 400), new Point(0, 75, 0)) //
////                .setKL(0.0004).setKQ(0.002),
//
//            new SpotLight(new Color(WHITE).scale(0.5), new Point(0, 40, 40), new Vector(0, -1, -1))
//                    .setNarrowBeam(15)//
//                    .setKL(0.0004).setKQ(0.0002),
//
//            new SpotLight(new Color(WHITE).scale(0.5), new Point(0, -40, 40), new Vector(0, 1, -1))
//                    .setNarrowBeam(15)//
//                    .setKL(0.0004).setKQ(0.0002),
//
//            new SpotLight(new Color(WHITE).scale(0.5), new Point(40, 0, 40), new Vector(-1, 0, -1))
//                    .setNarrowBeam(15)//
//                    .setKL(0.0004).setKQ(0.0002),
//
//            new SpotLight(new Color(WHITE).scale(0.5), new Point(-40, 0, 40), new Vector(1, 0, -1))
//                    .setNarrowBeam(15)//
//                    .setKL(0.0004).setKQ(0.0002)
//
//        );

        scene.addLights(
                new PointLight(new Color(WHITE).scale(0.25),new Point(0, 0, 100))
                        .setKL(0.0004).setKQ(0.0002)
        );


        RayTracerBase rayTracer = new RayTracerBasic(scene);

        double angle = Math.toRadians((double) 360 / frames);

        int n = 300;
        Point q = new Point(0, 0, 180);

        for (int i = 0; i < frames; i++) {

            Point p = new Point(1000 * Math.cos(angle * i), 1000 * Math.sin(angle * i), 180);
            Vector v = q.subtract(p);

            new Camera(p, v, vUp)
                    .pitchCamera(10)
                    .setImageWriter(
                            new ImageWriter("animation/cylinder/" + i, n, n)
                    )
                    .setVPSize(150, 150)
                    .setVPDistance(1000)
                    .setRayTracer(rayTracer)
                    .renderImage()
//                    .printGrid(n / 8, new Color(WHITE))
                    .writeToImage();

        }

    }

    @Test
    @DisplayName("testing six sphere in a circle")
    void testingSixSphereInACircle() {
        Material difusiveMaterial = new Material().setKD(1).setKS(0.1).setNShininess(30);
        Material reflactiveMaterial = new Material().setKD(1).setKS(1).setNShininess(52).setKR(1);

        double angle = Math.toRadians((double) 60);

        Color[] colors = {
                new Color(190,134,5),
                new Color(169,183,24),
                new Color(12,159,85),
                new Color(20,99,212),
                new Color(130,27,142),
                new Color(181,7,39),
        };

        Sphere[] spheres = new Sphere[6];

        for (int i = 0; i < 6; i++) {
            spheres[i] =
                    (Sphere) new Sphere(
                            new Point(100 * Math.sin(angle * i), 100 * Math.cos(angle * i), 0),
                            35.0d
                    )
                            .setEmission(colors[i])
                            .setMaterial(reflactiveMaterial);

            System.out.println(spheres[i].getCenter());
        }

        int n = 400;

        Scene scene = new Scene("six circles")
                .addGeometries(spheres)
//                .addGeometries(new Sphere(new Point(0, 0, 0), 10).setMaterial(difusiveMaterial).setEmission(new Color(WHITE)))
                .addLights(
                        new SpotLight(new Color(WHITE), new Point(0, 200, 400), new Vector(0, -1, -2))
                                .setKL(4E-5)
                                .setKQ(2E-5)
                )
                .setBackground(new Color(BLACK))
                .setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        RayTracerBase rayTracer = new RayTracerBasic(scene);

        Camera camera = new Camera(
                    new Point(0, 0, 400),
                    new Vector(0, 0, -1),
                    new Vector(0, 1, 0)
                )
                .setImageWriter(
                        new ImageWriter("sixCircle", n, n)
                )
                .setVPSize(150, 150)
                .setVPDistance(200)
                .setRayTracer(rayTracer)
                .renderImage();

        camera.writeToImage();
    }

}
