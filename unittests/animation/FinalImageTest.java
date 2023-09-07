package animation;

import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static java.awt.Color.*;

public class FinalImageTest {

    Camera camera;

    Material material = new Material().setKD(0.7).setKS(0.7).setNShininess(53);
    Color color1 = new Color(WHITE).scale(0.5);
    Color color2 = new Color(40, 40, 40);
//    Color dark = new Color(125, 25, 53).scale(0.7);
//    Color light = new Color(230, 62, 109);
    Color light = color1;
    Color dark = color2;

    Cube Box = new Cube(
            new Vector(1, 0, 0),
            new Vector(0, 1, 0),
            new Vector(0, 0, 1),
            new Point(-1000, -1000, -1000),
            2000
    )
            .setEmission(new Color(0, 0, 45))
            .setMaterial(new Material().setNShininess(32).setKD(0.5).setKS(1));

    Cube table = new Cube(
            new Vector(500, 0, 0),
            new Vector(0, 500, 0),
            new Vector(0, 0, -40),
            new Point(-250, -250, -3),
            1
    )
            .setEmission(color1)
            .setMaterial(new Material().setNShininess(32).setKD(0.5).setKS(1));

    List<Intersectable> floor =  createFloor(
            new Point(160, -160,0),
            new Vector(-40, 0, 0),
            new Vector(0, 40, 0),
            light,
            material,
            8,
            8
    );

    List<Intersectable> floor2 =  createFloor(
            new Point(-160, -160,0),
            new Vector(40, 0, 0),
            new Vector(0, 40, 0),
            dark,
            material,
            8,
            8
    );

    double size = 50;

    Pawn pawn = new Pawn(
            new Point(0, 0, 0),
            new Vector(0, 0, 1).normalize(),
            150,
            dark,
            material
    );

    Pawn pawn1 = new Pawn(
            new Point(-60, -20, 0),
            new Vector(0, 0, 1),
            size,
            dark,
            material
    );

    Pawn pawn2 = new Pawn(
            new Point(20, -100, 0),
            new Vector(0, 0, 1),
            size,
            light,
            material
    );

    Pawn pawn3 = new Pawn(
            new Point(60, 100, 0),
            new Vector(0, 0, 1),
            size,
            dark,
            material
    );

    Pawn pawn4 = new Pawn(
            new Point(-20, 60, 0),
            new Vector(0, 0, 1),
            size,
            light,
            material
    );

    Rook rook = new Rook(
            new Point(0, 0, 0),
            new Vector(0, 0, 0.95),
            150,
            new Vector(1, 1, 0).normalize(),
            new Vector(-1, 1, 0).normalize()
    )
            .setEmission(dark)
            .setMaterial(material);

    Rook rook1 = new Rook(
            new Point(-100, 60, 0),
            new Vector(0, 0, 1),
            size,
            new Vector(1, 0, 0),
            new Vector(0, 1, 0)
    )
            .setEmission(light)
            .setMaterial(material);

    Rook rook2 = new Rook(
            new Point(100, -20, 0),
            new Vector(0, 0, 1),
            size,
            new Vector(1, 0, 0),
            new Vector(0, 1, 0)
    )
            .setEmission(dark)
            .setMaterial(material);

    Queen queen = new Queen(
            new Point(0, 0, 0),
            new Vector(0, 0, 1),
            150,
            new Vector(1, 0, 0),
            new Vector(0, 1, 0)
    )
            .setEmission(light)
            .setMaterial(material);

    Queen queen1 = new Queen(
            new Point(20, 20, 0),
            new Vector(0, 0, 1),
            size,
            new Vector(1, 0, 0),
            new Vector(0, 1, 0)
    )
            .setEmission(dark)
            .setMaterial(material);

    Queen queen2 = new Queen(
            new Point(-20, -60, 0),
            new Vector(0, 0, 1),
            size,
            new Vector(1, 0, 0),
            new Vector(0, 1, 0)
    )
            .setEmission(light)
            .setMaterial(material);

    AmbientLight ambientLight = new AmbientLight(
            new Color(WHITE),
            new Double3(0.3)
    );

    SpotLight spotLight1 = (SpotLight) new SpotLight(new Color(WHITE).scale(0.5), new Point(240, 240, 200), new Vector(-2.4, -2.4, -1))
            .setKL(4E-6).setKQ(4E-6);

    SpotLight spotLight2 = (SpotLight) new SpotLight(new Color(WHITE).scale(0.5), new Point(240, -240, 200), new Vector(-2.4, 2.4, -1))
            .setKL(4E-5).setKQ(4E-5);

    SpotLight spotLight3 = (SpotLight) new SpotLight(new Color(WHITE).scale(0.5), new Point(-240, 240, 200), new Vector(2.4, -2.4, -1))
            .setKL(4E-5).setKQ(4E-5);

    SpotLight spotLight4 = (SpotLight) new SpotLight(new Color(WHITE).scale(0.5), new Point(-240, -240, 200), new Vector(2.4, 2.4, -1))
            .setKL(4E-6).setKQ(4E-6);

    SpotLight spotLight = (SpotLight) new SpotLight(new Color(WHITE).scale(0.8), new Point(0, 150, 500), new Vector(0, -1.5, -5))
            .setKL(1E-6).setKQ(1E-6);

    PointLight pointLight = new PointLight(new Color(WHITE), new Vector(0, 0, 300))
            .setKL(8E-5).setKQ(8E-5);

    Scene scene = new Scene("CornelBoxTest")
            .setAmbientLight(ambientLight)
            .addLights(pointLight)
            .addLights(spotLight1)
            .addLights(spotLight2)
            .addLights(spotLight3)
            .addLights(spotLight4)
//            .addLights(spotLight)

            .addGeometries(table.getGeometries())

            .addGeometries(pawn1.getGeometries())
            .addGeometries(pawn2.getGeometries())
            .addGeometries(pawn3.getGeometries())
            .addGeometries(pawn4.getGeometries())

            .addGeometries(rook1.getGeometries())
            .addGeometries(rook2.getGeometries())

            .addGeometries(queen1.getGeometries())
            .addGeometries(queen2.getGeometries())

            .addAllGeometries(floor)
            .addAllGeometries(floor2)
            ;
    public List<Intersectable> createTile(Point p, Vector v, Vector u, Color emission, Material material) {
       List<Intersectable> res = new LinkedList<>();

       Point p0, p1, p2, p3;

       p0 = p;
       p1 = p0.add(v);
       p2 = p0.add(u);
       p3 = p1.add(u);

       res.add(new Triangle(p0, p1, p2).setEmission(emission).setMaterial(material));
       res.add(new Triangle(p1, p2, p3).setEmission(emission).setMaterial(material));

       return res;
    }

    public List<Intersectable> createFloor(Point p, Vector x, Vector y, Color emission, Material material, int maxWidth, int maxHeight) {
       List<Intersectable> res = new LinkedList<>();
       maxHeight = maxHeight / 2;

       Point tempP = p;
       Point tempP2 = p;
       Vector y2 = y.scale(2);

       for (int i = 0; i < maxWidth; i++) {
          tempP = tempP2;

          if(i % 2 == 1)
                 tempP = tempP.add(y);

          for (int j = 0; j < maxHeight; j++) {
             res.addAll(createTile(tempP, x, y, emission, material));
             tempP = tempP.add(y2);

          }

           tempP2 = tempP2.add(x);
       }

       return res;
    }

    public static int n = 400;


   @Test
   @DisplayName("testing without multi threading")
   void testingWithoutMultiThreading() {
       // turning BVH off
       Geometries.axisAlignedBoundingBox = false;

       camera = new Camera(new Point(-500, 100, 400), new Vector(5, -1, 0), new Vector(0, 0, 1))
               .setVPSize(500, 500)
               .setVPDistance(660)
               .setAliasRays(32)
               .pitchCamera(40);
       camera
               .setRayTracer(new RayTracerBasic(scene))
               .setImageWriter(new ImageWriter("/objects/test1", n, n))
               .setMultiThreading(false)
               .renderImage()
               .writeToImage();
   }

   // TODO Test number 1
   @Test
   @DisplayName("testing with multi threading")
   void testingWithMultiThreading() {
       // turning BVH off
       Geometries.axisAlignedBoundingBox = false;

       camera = new Camera(new Point(-500, 100, 400), new Vector(5, -1, 0), new Vector(0, 0, 1))
               .setVPSize(500, 500)
               .setVPDistance(660)
               .setAliasRays(32)
               .pitchCamera(40);
       camera
               .setRayTracer(new RayTracerBasic(scene))
               .setImageWriter(new ImageWriter("/objects/test8", n, n))
               .setMultiThreading(true)
               .renderImage()
               .writeToImage();

   }

   @Test
   @DisplayName("testing with BVH")
   void testingWithBvh() {
        // turning BVH on
        Geometries.axisAlignedBoundingBox = true;

       camera = new Camera(new Point(-500, 100, 400), new Vector(5, -1, 0), new Vector(0, 0, 1))
               .setVPSize(500, 500)
               .setVPDistance(660)
               .pitchCamera(40);
       camera
               .setRayTracer(new RayTracerBasic(scene))
               .setImageWriter(new ImageWriter("/objects/test3", n, n))
               .setMultiThreading(false)
               .renderImage()
               .writeToImage();
   }

    // TODO Test number 2
   @Test
   @DisplayName("testing with all improvement")
   void testingWithAllImprovement() {
       // turning BVH on
       Geometries.axisAlignedBoundingBox = true;

       camera = new Camera(new Point(0, 320, 150), new Vector(0, -1, 0), new Vector(0, 0, 1))
               .setVPSize(100, 100)
               .setVPDistance(120)
               .setAliasRays(32)
               .pitchCamera(30)
                ;
       camera
               .setRayTracer(new RayTracerBasic(scene))
               .setImageWriter(new ImageWriter("/objects/test9", n, n))
               .setMultiThreading(true)
               .renderImage()
               .writeToImage();
   }
}
