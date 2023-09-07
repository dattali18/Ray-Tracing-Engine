package animation;

import geometries.Geometries;
import geometries.Sphere;
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

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

public class AnimationTest {
    private final SpotLight spotLight1 = (SpotLight) new SpotLight(
            new Color(256, 256, 256),
            new Point(-50, -50, 25),
            new Vector(1, 1, -0.5)
    ).setKL(0.001).setKQ(0.0002);

    private final SpotLight spotLight2 = (SpotLight) new SpotLight(
            new Color(256, 256, 256),
            new Point(50, 50, 25),
            new Vector(-1, -1, -0.5)
    ).setKL(0.001).setKQ(0.0002);

    private final AmbientLight ambientLight = new AmbientLight(
            new Color(WHITE),
            new Double3(0.15)
    );

    private final Sphere sphere1 = (Sphere) new Sphere(
            new Point(0, 0, -50),
            30.0d
    )
            .setEmission(new Color(127, 0, 0))
            .setMaterial(new Material()
                    .setKD(KD)
                    .setKS(KS)
                    .setKT(0.6)
                    .setNShininess(SHININESS));

    private final Sphere sphere2 = (Sphere) new Sphere(
            new Point(40, 0, -20),
            25.0d
    )
            .setEmission(new Color(0, 127, 0))
            .setMaterial(new Material()
                    .setKD(KD)
                    .setKS(KS)
                    .setKR(1)
                    .setNShininess(SHININESS));

    private final Sphere sphere3 = (Sphere) new Sphere(
            new Point(-45, -10, -70),
            30.0d
    ).setEmission(new Color(0, 0, 127))
            .setMaterial(new Material()
                    .setKD(KD)
                    .setKS(KS)
                    .setNShininess(SHININESS));



    private final Scene scene = new Scene.SceneBuilder()
            .setLightSource( List.of(spotLight1, spotLight2) )
            .setAmbientLight( ambientLight )
            .setGeometries( new Geometries(sphere1, sphere2, sphere3) )
            .setBackGround(new Color(BLACK))
            .setName("many sphere")
            .build();


    private final Camera camera1 = new Camera(new Point(0, 0, 1000),
            new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPSize(150, 150).setVPDistance(1000);

    private static final int     SHININESS               = 302;
    private static final double  KD                      = 0.5;
    private static final Double3 KD3                     = new Double3(0.2, 0.6, 0.4);
    private static final double  KS                      = 0.5;
    private static final Double3 KS3                     = new Double3(0.2, 0.4, 0.3);
    private final Material material = new Material().setKD(KD3).setKS(KS3).setNShininess(SHININESS);

    private final int frames = 60;

    @Test
    @DisplayName("testing a scene with more than one object")
    void testingASceneWithMoreThanOneObject() {
        List<ImageWriter> imageWriterList = new ArrayList<>();

        for (int i = 1; i <= frames; i++) {
            imageWriterList.add(new ImageWriter("animation/" + i, 400, 400));
        }

        Geometries.axisAlignedBoundingBox = true;

        camera1.setRayTracer(new RayTracerBasic(scene))
                .setMultiThreading(true)
                ;

        double alpha = 0.0d;
//        double beta = 0.0d;

        for (int i = 0; i < frames; i++) {
            camera1.setImageWriter(imageWriterList.get(i))
                    .rotate(0, 0, alpha)
                    .renderImage()
                    .writeToImage();

            alpha += 5;
//            beta += 0.01;
        }
    }
}
