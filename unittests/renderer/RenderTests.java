package renderer;

import static java.awt.Color.YELLOW;

import geometries.Geometries;
import geometries.Plane;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import primitives.*;
import scene.Scene;

/** Test rendering a basic image
 * @author Dan */
public class RenderTests {

   /** Produce a scene with basic 3D model and render it into a png image with a
    * grid */
   @Test
   public void basicRenderTwoColorTest() {
      Scene scene = new Scene("Test scene")//
         .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                                           new Double3(1, 1, 1))) //
         .setBackground(new Color(75, 127, 90));

      scene.geometries.add(new Sphere(50d, new Point(0, 0, -100)),
                           new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
                           // left
                           new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                                        new Point(-100, -100, -100)), // down
                           // left
                           new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
      // right
      Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPDistance(100) //
         .setVPSize(500, 500) //
         .setImageWriter(new ImageWriter("base render test", 1000, 1000))
         .setRayTracer(new RayTracerBasic(scene));

      camera.renderImage();
      camera.printGrid(100, new Color(YELLOW));
      camera.writeToImage();
   }

   // For stage 6 - please disregard in stage 5
   /** Produce a scene with basic 3D model - including individual lights of the
    * bodies and render it into a png image with a grid */
    @Test
    public void basicRenderMultiColorTest() {
        Color WHITE = new Color(256, 256, 256);
        Color GREEN = new Color(0, 256, 0);
        Color RED = new Color(256, 0, 0);
        Color BLUE = new Color(0, 0, 256);



        Scene scene = new Scene("Test scene")//
        .setAmbientLight(new AmbientLight(WHITE, new Double3(0.2))); //

        scene.geometries.add( //
        new Sphere( new Point(0, 0, -100), 50),
        // up left
        new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new
        Point(-100, 100, -100))
        .setEmission(GREEN),
        // down left
        new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new
        Point(-100, -100, -100))
        .setEmission(RED),
        // down right
        new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new
        Point(100, -100, -100))
        .setEmission(BLUE));

        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1,
        0)) //
        .setVPDistance(100) //
        .setVPSize(500, 500) //
        .setImageWriter(new ImageWriter("color render test", 1000, 1000))
        .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, WHITE);
        camera.writeToImage();
    }

//   /** Test for XML based scene - for bonus */
//   @Test
//   public void basicRenderXml() {
////      Scene scene  = Parser.XmlToScene("basicRenderTestTwoColors");
////
////
////      Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0))     //
////         .setVPDistance(100)                                                                //
////         .setVPSize(500, 500).setImageWriter(new ImageWriter("xml render test", 1000, 1000))
////         .setRayTracer(new RayTracerBasic(scene));
////
////      camera.renderImage();
////      camera.printGrid(100, new Color(YELLOW));
////      camera.writeToImage();
//
//
//      Scene scene1 = Parser.XmlToScene("scene1");
//       Camera camera1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0))     //
//               .setVPDistance(5)                                                                //
//               .setVPSize(500, 500).setImageWriter(new ImageWriter("xml second scene", 1000, 1000))
//               .setRayTracer(new RayTracerBasic(scene1));
//
//       camera1.renderImage();
////       camera1.printGrid(100, new Color(YELLOW));
//       camera1.writeToImage();

//   }

//   @Test
//   @DisplayName("testing scene")
//   void testingScene() {
//
//       Scene scene = new Scene.SceneBuilder()
//               .setAmbientLight(new AmbientLight(new Color(256, 256, 256), 0.1))
//               .setBackGround(new Color(0, 0, 0))
//               .setGeometries(new Geometries(
//                       new Plane(new Point(0, 0, -15), new Vector(0, 1, 1)).setEmission(new Color(256, 0, 0)),
//                       new Plane(new Point(0, 0, -15), new Vector(0, -1, 1)).setEmission(new Color(0, 256, 0)),
//                       new Sphere(new Point(0, 0, -10), 7).setEmission(new Color(0, 0, 256))
//               ))
//               .setName("scene1")
//               .build();
//
//       Camera camera1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0))     //
//               .setVPDistance(100)                                                                //
//               .setVPSize(500, 500).setImageWriter(new ImageWriter("xml second scene", 3000, 3000))
//               .setRayTracer(new RayTracerBasic(scene));
//
//       camera1.renderImage();
////       camera1.printGrid(100, new Color(WHITE));
//       camera1.writeToImage();
//   }
}
