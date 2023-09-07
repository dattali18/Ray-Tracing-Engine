package animation;

import geometries.Cube;
import geometries.Plane;
import geometries.Sphere;
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

public class DepthOfFieldTest {

    // ======================== Materials ========================
    Material plasticMaterial = new Material()
            .setKD(new Double3(0.8, 0.8, 0.8))    // Diffuse color of the plastic (light gray)
            .setKS(new Double3(0.2, 0.2, 0.2))    // Specular color of the plastic (dark gray)
            .setNShininess(32);                    // Shininess value for the plastic (controls the size of the specular highlights);

    Material wallMaterial = new Material()
            .setKD(new Double3(0.7, 0.7, 0.7))    // Diffuse color of the wall (light gray)
            .setKS(new Double3(0.0, 0.0, 0.0))    // No specular reflection (black)
            .setNShininess(0);                     // No shininess (no specular highlights)

    // ======================== Color ========================
    Color tileColor = new Color(179,156,131);
    Color wallColor = new Color(134,115,109).scale(1.5);

    // ======================== create a Box ========================
    Plane top = (Plane) new Plane(new Point(0, 0, 150), new Vector(0, 0, -1))
            .setMaterial(wallMaterial)
            .setEmission(wallColor);
    Plane bottom = (Plane) new Plane(new Point(0, 0, -150), new Vector(0, 0, 1))
            .setMaterial(wallMaterial)
            .setEmission(wallColor.scale(0.6));

    Plane left = (Plane) new Plane(new Point(-250, 0, 0), new Vector(1, 0, 0))
            .setMaterial(wallMaterial)
            .setEmission(new Color(75, 0, 0));
    Plane right = (Plane) new Plane(new Point(250, 0, 0), new Vector(-1, 0, 0))
            .setMaterial(wallMaterial)
            .setEmission(new Color(0, 0, 75));

    Plane front = (Plane) new Plane(new Point(0, -300, 0), new Vector(0, -1, 0))
            .setMaterial(wallMaterial)
            .setEmission(wallColor)
//            .setEmission(new Color(BLUE))
            ;
    Plane back = (Plane) new Plane(new Point(0, 300, 0), new Vector(0, 1, 0))
            .setMaterial(wallMaterial)
            .setEmission(wallColor)
//            .setEmission(new Color(RED))
            ;

    // ======================== Object ========================

    Sphere sphere1 = (Sphere) new Sphere(new Point(0, 0, 0), 30)
            .setMaterial(plasticMaterial)
            .setEmission(new Color(30, 87, 32))
            ;

    Vector u = new Vector(0, -1, 0);
    Vector v = new Vector(3, 0, 0);
    Vector w = new Vector(0, 0, 6);

    double scale = 30;
    double factor = 0.75;

    Cube tile7 = new Cube(u, v, w, new Point(-120, -30, -150) , scale)
            .setMaterial(plasticMaterial)
            .setEmission(tileColor)
            ;

    Cube tile8 = new Cube(u, v, w, new Point(45, -30, -150) , scale)
            .setMaterial(plasticMaterial)
            .setEmission(tileColor)
            ;

    Cube tile1 = new Cube(u, v, w, new Point(-120, 0, -150).add(v.scale(-scale/ 4)) , scale * factor)
            .setMaterial(plasticMaterial)
            .setEmission(tileColor)
            ;

    Cube tile2 = new Cube(u, v, w, new Point(45, 0, -150).add(v.scale(scale/ 2)) , scale * factor)
            .setMaterial(plasticMaterial)
            .setEmission(tileColor)
            ;

    Cube tile3 = new Cube(u, v, w, new Point(-120, 30, -150).add(v.scale(-scale / 2)) , scale * factor * factor)
            .setMaterial(plasticMaterial)
            .setEmission(tileColor)
            ;

    Cube tile4 = new Cube(u, v, w, new Point(40, 30, -150).add(v.scale(scale)) , scale * factor * factor)
            .setMaterial(plasticMaterial)
            .setEmission(tileColor)
            ;


    Cube tile5 = new Cube(u, v, w, new Point(-120, 60, -150).add(v.scale(-3 * scale / 4)) , scale * factor * factor * factor)
            .setMaterial(plasticMaterial)
            .setEmission(tileColor)
            ;

    Cube tile6 = new Cube(u, v, w, new Point(35, 60, -150).add(v.scale(3 * scale/ 2)) , scale * factor * factor * factor)
            .setMaterial(plasticMaterial)
            .setEmission(tileColor)
            ;


    // ======================== Lights ========================

    AmbientLight ambientLight = new AmbientLight(
            new Color(WHITE),
            new Double3(0.2)
    );

    SpotLight spotLight1 = (SpotLight) new SpotLight(
            new Color(WHITE),
            new Point(148, 120, 148),
            new Vector(-2, -1, -3)
    )
            .setKL(1E-5)
            .setKQ(1E-5);

    PointLight pointLight1 = new PointLight(
            new Color(WHITE).scale(0.5),
            new Point(0, 0, 0)
    )
            .setKL(4E-4)
            .setKQ(2E-4);



    // ======================== Scene ========================
    Scene scene = new Scene("DepthOfFieldTest")
            .setAmbientLight(ambientLight)
            .addGeometries(top, bottom, left, right, front, back) // adding the box
            .addGeometries(tile1.getCube())
            .addGeometries(tile2.getCube())
            .addGeometries(tile3.getCube())
            .addGeometries(tile4.getCube())
            .addGeometries(tile5.getCube())
            .addGeometries(tile6.getCube())
            .addGeometries(tile7.getCube())
            .addGeometries(tile8.getCube())
            .addLights(spotLight1, pointLight1)
            ;

    // ======================== Creating the camera ========================
    int n = 500; // resolution

    Camera camera = new Camera(new Point(0, 250, -50), new Vector(0, -1, 0), new Vector(0, 0, 1))
            .setVPSize(550, 550)
            .setVPDistance(280)
            .setImageWriter( new ImageWriter("depth of field/depthOfField4", n, n))
            .setRayTracer( new RayTracerBasic(scene) )
            ;


    @Test
    @DisplayName("testing depth of field")
    void testingDepthOfField() {
        camera
                .setLensRadius(25)
                .setFocalRays(30)
//                .setAliasRays(15)
                .renderImage()
                .writeToImage();
    }

}
