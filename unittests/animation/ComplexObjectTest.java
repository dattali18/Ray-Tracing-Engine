package animation;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class ComplexObjectTest {
    Intersectable[] triangles;

    Plane plane1 = (Plane) new Plane(new Point(0, 0, -100), new Vector(0, 0, 1))
            .setMaterial(new Material().setKD(1).setKS(0.1).setNShininess(30).setKR(0.2))
            .setEmission(new Color(20, 20, 20));

    Plane plane2 = (Plane) new Plane(new Point(0, -100, 0), new Vector(0, -1, 0))
            .setMaterial(new Material().setKD(1).setKS(0.1).setNShininess(30).setKR(0.5))
            .setEmission(new Color(100, 100, 100));
    Color emission = new Color(200, 40 , 40);
    Material material = new Material().setKD(1).setKS(0.1).setNShininess(30);
    public ComplexObjectTest() {

        double factor = 20;

        triangles = new Intersectable[]{
                new Triangle(
                        new Point(new Double3(-1,-1,1).scale(factor)),
                        new Point(new Double3(1,-1,1).scale(factor)),
                        new Point(new Double3(1,1,1).scale(factor)))
                        .setEmission(emission).setMaterial(material),

                new Triangle(
                        new Point(new Double3(-1,-1,1).scale(factor)),
                        new Point(new Double3(1,1,1).scale(factor)),
                        new Point(new Double3(-1,1,1).scale(factor)))
                        .setEmission(emission).setMaterial(material),

                new Triangle(
                        new Point(new Double3(1,-1,1).scale(factor)),
                        new Point(new Double3(1,-1,-1).scale(factor)),
                        new Point(new Double3(1,1,-1).scale(factor)))
                        .setEmission(emission).setMaterial(material),

                new Triangle(
                        new Point(new Double3(1,-1,1).scale(factor)),
                        new Point(new Double3(1,1,-1).scale(factor)),
                        new Point(new Double3(1,1,1).scale(factor)))
                        .setEmission(emission).setMaterial(material),

                new Triangle(
                        new Point(new Double3(1,-1,-1).scale(factor)),
                        new Point(new Double3(-1,-1,-1).scale(factor)),
                        new Point(new Double3(-1,1,-1).scale(factor)))
                        .setEmission(emission).setMaterial(material),

                new Triangle(
                        new Point(new Double3(-1,-1,-1).scale(factor)),
                        new Point(new Double3(-1,1,-1).scale(factor)),
                        new Point(new Double3(1,1,-1).scale(factor)))
                        .setEmission(emission).setMaterial(material),

                new Triangle(
                        new Point(new Double3(-1,-1,-1).scale(factor)),
                        new Point(new Double3(-1,-1,1).scale(factor)),
                        new Point(new Double3(-1,1,1).scale(factor)))
                        .setEmission(emission).setMaterial(material),

                new Triangle(
                        new Point(new Double3(-1,-1,-1) .scale(factor)),
                        new Point(new Double3(-1,1,1).scale(factor)),
                        new Point(new Double3(-1,1,-1).scale(factor)))
                        .setEmission(emission).setMaterial(material),

                new Triangle(
                        new Point(new Double3(-1,1,1).scale(factor)),
                        new Point(new Double3(1,1,1).scale(factor)),
                        new Point(new Double3(1,1,-1).scale(factor)))
                        .setEmission(emission).setMaterial(material),

                new Triangle(
                        new Point(new Double3(-1,1,1) .scale(factor)),
                        new Point(new Double3 (1,1,-1).scale(factor)),
                        new Point(new Double3(-1,1,-1) .scale(factor)))
                        .setEmission(emission).setMaterial(material),

                new Triangle(
                        new Point(new Double3(1,-1,1).scale(factor)),
                        new Point(new Double3(-1,-1,-1).scale(factor)),
                        new Point(new Double3(1,-1,-1).scale(factor)))
                        .setEmission(emission).setMaterial(material),

                new Triangle(
                        new Point(new Double3(1,-1,1).scale(factor)),
                        new Point(new Double3(-1,-1,1).scale(factor)),
                        new Point(new Double3(-1,-1,-1).scale(factor)))
                        .setEmission(emission).setMaterial(material)

        };
    }

    
    @Test
    @DisplayName("test a Dedecahedron")
    void testADedecahedron() {

        Scene scene = new Scene("Dedecahedron")
                .setAmbientLight(new AmbientLight(new Color(WHITE), 0.4))
                .setBackground(new Color(BLACK))
                .addGeometries(triangles)
                .addGeometries(plane1, plane2)
                .addLights(
                        new SpotLight(new Color(WHITE).scale(0.6), new Point(0, 0, 100), new Vector(0, 0, -1))
                        .setKL(0.00004).setKQ(0.0002),

                        new SpotLight(new Color(WHITE).scale(0.6), new Point(100, 100, 100), new Vector(-1, -1, -1))
                                .setKL(0.00004).setKQ(0.0002),

                        new SpotLight(new Color(WHITE).scale(0.6), new Point(-100, 100, 100), new Vector(1, -1, -1))
                                .setKL(0.00004).setKQ(0.0002)
                );

        int n = 400;

        Camera camera = new Camera(new Point(0, 120, 120), new Vector(0, -1, 0), new Vector(0, 0, 1))
                .setVPSize(150, 150)
                .setVPDistance(100);

        camera
                .setMultiThreading(true)
                .setAliasRays(20)
                .pitchCamera(50)
                .setImageWriter( new ImageWriter("objects/1", n, n))
                .setRayTracer( new RayTracerBasic(scene) )
                .renderImage()
                .writeToImage();
    }
}
