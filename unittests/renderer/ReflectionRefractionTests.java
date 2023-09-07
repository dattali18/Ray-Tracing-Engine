package renderer;

import static java.awt.Color.*;

import geometries.Plane;
import geometries.Polygon;
import lighting.PointLight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   private Scene scene = new Scene("Test scene");

   /** Produce a picture of a sphere lighted by a spotlight */
   @Test
   public void twoSpheres() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(150, 150).setVPDistance(1000);

      scene.geometries.add( //
                           new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKD(0.4).setKS(0.3).setNShininess(100).setKT(0.3)
                                      .setConeAngleRefracted(0).setNumRaysRefracted(10)),
                           new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
                              .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100)));
      scene.lights.add( //
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                          .setKL(0.0004).setKQ(0.0000006));

      camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spotlight */
   @Test
   public void twoSpheresOnMirrors() {
      Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(2500, 2500).setVPDistance(10000); //

      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

      scene.geometries.add( //
                           new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
                              .setMaterial(new Material().setKD(0.25).setKS(0.25).setNShininess(20)
                                 .setKT(new Double3(0.5, 0, 0))),
                           new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
                              .setMaterial(new Material().setKD(0.25).setKS(0.25).setNShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKR(1)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))
                                      .setConeAngleRefracted(1)
                                      .setConeAngleReflected(60)));

      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
         .setKL(0.00001).setKQ(0.000005));

      ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of two triangles lighted by a spotlight with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(200, 200).setVPDistance(1000);

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      scene.geometries.add( //
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(60)), //
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(60)), //
                           new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKD(0.2).setKS(0.2).setNShininess(30).setKT(0.6)));

      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
         .setKL(4E-5).setKQ(2E-7));

      ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   @Test
   @DisplayName("testing More than three Object in the scene")
   void testingMoreThanThreeObjectInTheScene() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPSize(200, 200).setVPDistance(1000);

      Scene scene = new Scene("More then three object in the scene");

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      Point[] points = {
              new Point(200, 200, -150),
              new Point(-200, 200, -200),
              new Point(200, -200, -200),
              new Point(-200, -200, -150)
      };

      scene.geometries.add( //
              new Triangle(points[0], points[1], points[2])
                      .setEmission(new Color(WHITE).scale(0.3))
                      .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(60).setKR(0.9))
              ,
              new Triangle(points[1], points[2], points[3])
                      .setEmission(new Color(WHITE).scale(0.2))
                      .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(60).setKR(0.5)),

              new Sphere(new Point(-50, -50, -30), 17d).setEmission(new Color(RED)) //
                      .setMaterial(new Material().setKS(0.5).setKD(0.5).setNShininess(30)),

              new Sphere(new Point(-50, 50, -30), 15d).setEmission(new Color(GREEN)) //
                      .setMaterial(new Material().setKS(0.5).setKD(0.5).setNShininess(30)),

              new Sphere(new Point(50, 50, -30), 12d).setEmission(new Color(BLUE)) //
                      .setMaterial(new Material().setKS(0.5).setKD(0.5).setNShininess(30)),

              new Sphere(new Point(50, -50, -30), 10d).setEmission(new Color(YELLOW)) //
                      .setMaterial(new Material().setKS(0.5).setKD(0.5).setNShininess(30))

//              new Sphere(new Point(60, 50, -30), 15d).setEmission(new Color(RED)) //
//                      .setMaterial(new Material().setKD(0.2).setKS(0.2).setNShininess(30)),
//
//              new Sphere(new Point(60, 50, -40), 30d).setEmission(new Color(BLUE)) //
//                     .setMaterial(new Material().setKD(0.2).setKS(0.2).setNShininess(30).setKT(0.6))
                     );

      scene.lights.add(new PointLight(new Color(WHITE).reduce(2), new Point(0, 0, 100)) //
              .setKL(4E-5).setKQ(2E-7));

      ImageWriter imageWriter = new ImageWriter("threeOrMoreObject", 600, 600);
      camera.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene)) //
              .renderImage() //
              .writeToImage();
   }

   @Test
   @DisplayName("testing for a cube")
   void testingForACube() {
      Camera camera = new Camera(new Point(1000, -1000, 0), new Vector(-1, 1, 0), new Vector(0, 0, 1)) //
              .setVPSize(200, 200).setVPDistance(1000);

      Scene scene = new Scene("triangles");

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      Material material = new Material().setKD(0.5).setKS(0.5).setNShininess(60);

//      Point[] points1 =  {
//         new Point(10, 20, -10),
//         new Point(0, 20, -10),
//         new Point(-10, 20, -10),
//         new Point(-20, 20, -10),
//
//         new Point(10, 20, 0),
//         new Point(0, 20, 0),
//         new Point(-10, 20, 0),
//         new Point(-20, 20, 0),
//
//        new Point(10, 20, 10),
//        new Point(0, 20, 10),
//        new Point(-10, 20, 10),
//        new Point(-20, 20, 10),
//
//        new Point(10, 20, 20),
//        new Point(0, 20, 20),
//        new Point(-10, 20, 20),
//        new Point(-20, 20, 20),
//      };
//
//      Triangle[] triangles = {
//              // face 1 bottom row
//              (Triangle) new Triangle(points1[0], points1[1], points1[4]).setMaterial(material).setEmission(new Color(YELLOW)),
//              (Triangle) new Triangle(points1[4], points1[5], points1[1]).setMaterial(material).setEmission(new Color(YELLOW)),
//              (Triangle) new Triangle(points1[1], points1[2], points1[5]).setMaterial(material).setEmission(new Color(BLUE)),
//              (Triangle) new Triangle(points1[5], points1[6], points1[2]).setMaterial(material).setEmission(new Color(BLUE)),
//              (Triangle) new Triangle(points1[2], points1[3], points1[6]).setMaterial(material).setEmission(new Color(RED)),
//              (Triangle) new Triangle(points1[6], points1[7], points1[3]).setMaterial(material).setEmission(new Color(RED)),
//
//              // face 1 middle row
//              (Triangle) new Triangle(points1[4], points1[5], points1[8]).setMaterial(material).setEmission(new Color(GREEN)),
//              (Triangle) new Triangle(points1[8], points1[9], points1[5]).setMaterial(material).setEmission(new Color(GREEN)),
//              (Triangle) new Triangle(points1[5], points1[6], points1[9]).setMaterial(material).setEmission(new Color(YELLOW)),
//              (Triangle) new Triangle(points1[9], points1[10], points1[6]).setMaterial(material).setEmission(new Color(YELLOW)),
//              (Triangle) new Triangle(points1[6], points1[7], points1[10]).setMaterial(material).setEmission(new Color(BLUE)),
//              (Triangle) new Triangle(points1[10], points1[11], points1[7]).setMaterial(material).setEmission(new Color(BLUE)),
//
//              // face 1 top row
//              (Triangle) new Triangle(points1[8], points1[9], points1[12]).setMaterial(material).setEmission(new Color(YELLOW)),
//              (Triangle) new Triangle(points1[12], points1[13], points1[9]).setMaterial(material).setEmission(new Color(YELLOW)),
//              (Triangle) new Triangle(points1[9], points1[10], points1[13]).setMaterial(material).setEmission(new Color(GREEN)),
//              (Triangle) new Triangle(points1[13], points1[14], points1[10]).setMaterial(material).setEmission(new Color(GREEN)),
//              (Triangle) new Triangle(points1[10], points1[11], points1[14]).setMaterial(material).setEmission(new Color(RED)),
//              (Triangle) new Triangle(points1[14], points1[15], points1[11]).setMaterial(material).setEmission(new Color(RED))
//      };

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

      double kT = 0.9;

      Triangle[] cube = {
              (Triangle) new Triangle(points2[0], points2[1], points2[5]).setMaterial(material.setKT(kT)).setEmission(new Color(YELLOW)),
              (Triangle) new Triangle(points2[4], points2[5], points2[0]).setMaterial(material.setKT(kT)).setEmission(new Color(YELLOW)),
              (Triangle) new Triangle(points2[1], points2[2], points2[6]).setMaterial(material.setKT(kT)).setEmission(new Color(WHITE)),
              (Triangle) new Triangle(points2[5], points2[6], points2[1]).setMaterial(material.setKT(kT)).setEmission(new Color(WHITE)),
              (Triangle) new Triangle(points2[2], points2[3], points2[6]).setMaterial(material.setKT(kT)).setEmission(new Color(RED)),
              (Triangle) new Triangle(points2[6], points2[7], points2[2]).setMaterial(material.setKT(kT)).setEmission(new Color(RED)),
              (Triangle) new Triangle(points2[0], points2[3], points2[4]).setMaterial(material.setKT(kT)).setEmission(new Color(GREEN)),
              (Triangle) new Triangle(points2[4], points2[7], points2[3]).setMaterial(material.setKT(kT)).setEmission(new Color(GREEN)),
              (Triangle) new Triangle(points2[4], points2[5], points2[6]).setMaterial(material.setKT(kT)).setEmission(new Color(BLUE)),
              (Triangle) new Triangle(points2[6], points2[7], points2[4]).setMaterial(material.setKT(kT)).setEmission(new Color(BLUE)),
              (Triangle) new Triangle(points2[0], points2[1], points2[2]).setMaterial(material.setKT(kT)).setEmission(new Color(ORANGE)),
              (Triangle) new Triangle(points2[2], points2[3], points2[0]).setMaterial(material.setKT(kT)).setEmission(new Color(ORANGE))

      };


      scene.geometries.add(
              new Sphere(new Point(0, 0, 0), 30d).setMaterial(material).setEmission(new Color(BLUE))
      );

      scene.geometries.add(
              cube
      );

      scene.lights.add(new PointLight(new Color(700, 400, 400).scale(0.5), new Point(0, 0, 100)) //
              .setKL(4E-5).setKQ(2E-7));

      ImageWriter imageWriter = new ImageWriter(scene.name, 600, 600);

      camera.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene)) //
              .renderImage() //
              .writeToImage();

   }
}
