package parser;

import com.google.gson.Gson;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;
import scene.Scene;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.Files.exists;

/**
 * a class to parse scene from json
 */
public class JsonParser {

    /**
     * @param args testing
     */
    public static void main(String[] args) {
        Scene scene = new Scene.SceneBuilder()
                .setName("Scene1")
                .setGeometries(
                        new Geometries(new Sphere(new Point(0, 0, 0), 50.0d))
                )
                .setBackGround(new Color(0, 0, 0))
                .setAmbientLight(new AmbientLight(new Color(30, 30, 30), 0.50d))
                .setLightSource(
                        List.of(new DirectionalLight(new Color(255, 0, 0), new Vector(1, 1, -0.5)))
                )
                .build();

        Scene scene1 = jsonToScene("testing");
    }

    /**
     * @param scene the scene
     * @param fileName the file
     */
    public static void sceneToJson(Scene scene, String fileName) {
        Gson gson = new Gson();

        try {
            FileWriter writer = new FileWriter("json/" + fileName + ".json");
            gson.toJson(scene, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param fileName the name of the file containing the scene
     * @return the scene object after it was parsed
     */
    public static Scene jsonToScene(String fileName) {
        Gson gson = new Gson();

        try {
            FileReader reader = new FileReader("json/" + fileName + ".json");
            Scene scene = gson.fromJson(reader, Scene.class);
            reader.close();
            return scene;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param filename the file containing the scene
     * @param scene the scene
     * @return the scene
     * @throws Exception some exception
     */
    public static Scene parseSceneFromJson(String filename, Scene scene) throws Exception {

        Path path = Paths.get(filename);
        if (!exists(path)) {
            return null;
        }

        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(filename);
            JsonScene jsonScene = gson.fromJson(fileReader, JsonScene.class);

            // Parsing background color
            String background = jsonScene.scene.background;
            Point temp = parsePointFromString(background);
            scene.setBackground(new Color(temp.getX(), temp.getY(), temp.getZ()));

            // Parsing ambient light intensity

            Point ambientLightIntensity = parsePointFromString(jsonScene.scene.ambientLight.intensity);

            try {
                double ambientLightEmission = Double.parseDouble(jsonScene.scene.ambientLight.emission);

                scene.setAmbientLight(
                        new AmbientLight(
                                new Color(
                                        ambientLightIntensity.getX(),
                                        ambientLightIntensity.getY(),
                                        ambientLightIntensity.getZ()),
                                ambientLightEmission));
            } catch (Exception a) {

                scene.setAmbientLight(
                        new AmbientLight(
                                new Color(
                                        ambientLightIntensity.getX(),
                                        ambientLightIntensity.getY(),
                                        ambientLightIntensity.getZ()),
                                new Double3(1, 1, 1)));
            }


            // Parsing geometries
            GeometryData geometryData = jsonScene.scene.geometries;

            Geometries geometries = new Geometries();

            // Parsing sphere
            if(geometryData.sphere != null) {
                for (SphereData sphereData : geometryData.sphere) {
                    Point center = parsePointFromString(sphereData.center);
                    double radius = Double.parseDouble(sphereData.radius);


                    try {
                        Point emission = parsePointFromString(sphereData.emission);

                        geometries.add(new Sphere(center, radius)
                                .setEmission(new Color(emission.getX(), emission.getY(), emission.getZ())));
                    } catch (Exception a) {

                        geometries.add(new Sphere(center, radius));
                    }


                }
            }

            // Parsing triangles
            if(geometryData.triangle != null) {
                for (TriangleData triangleData : geometryData.triangle) {
                    Point p0 = parsePointFromString(triangleData.p0);
                    Point p1 = parsePointFromString(triangleData.p1);
                    Point p2 = parsePointFromString(triangleData.p2);


                    try {
                        Point emission = parsePointFromString(triangleData.emission);

                        geometries.add(new Triangle(p0, p1, p2)
                                .setEmission(new Color(emission.getX(), emission.getY(), emission.getZ())));
                    } catch (Exception a) {

                        geometries.add(new Triangle(p0, p1, p2));
                    }

                }
            }

            // Parsing planes
            if(geometryData.plane != null) {
                for (PlaneData planeData : geometryData.plane) {
                    Point p0 = parsePointFromString(planeData.p0);
                    Point p1 = parsePointFromString(planeData.p1);
                    Point p2 = parsePointFromString(planeData.p2);


                    try {
                        Point emission = parsePointFromString(planeData.emission);

                        geometries.add(new Plane(p0, p1, p2)
                                .setEmission(new Color(emission.getX(), emission.getY(), emission.getZ())));
                    } catch (Exception a) {

                        geometries.add(new Plane(p0, p1, p2));
                    }

                }
            }

            if(geometryData.polygon != null) {
                // Parsing polygon
                for (PolygonData polygonData : geometryData.polygon) {
                    List<Point> vertices = new LinkedList<>();
                    for (String vertice : polygonData.vertices) {
                        vertices.add(parsePointFromString(vertice));
                    }
                    Point[] points = vertices.toArray(new Point[0]);


                    try {
                        Point emission = parsePointFromString(polygonData.emission);

                        geometries.add(new Polygon(points)
                                .setEmission(new Color(emission.getX(), emission.getY(), emission.getZ())));
                    } catch (Exception a) {

                        geometries.add(new Polygon(points));
                    }

                }
            }

            scene.setGeometries(geometries);

            return scene;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Point parsePointFromString(String pointString) {
        String[] values = pointString.split(" ");
        int x = Integer.parseInt(values[0]);
        int y = Integer.parseInt(values[1]);
        int z = Integer.parseInt(values[2]);
        return new Point(x, y, z);
    }


    static class GeometryData {
        SphereData[] sphere;
        TriangleData[] triangle;
        PlaneData[] plane;
        PolygonData[] polygon;
    }

    static class PlaneData {
        String p0;
        String p1;
        String p2;
        String emission;
    }

    static class PolygonData {
        List<String> vertices;
        String emission;
    }

    static class RayData {
        String p0;
        String dir;
    }

    static class SceneData {
        String background;
        JsonAmbientLight ambientLight;
        GeometryData geometries;
    }

    static class SphereData {
        String center;
        String radius;
        String emission;
    }

    static class TriangleData {
        String p0;
        String p1;
        String p2;
        String emission;
    }

    static class TubeData {
        RayData axisRay;
        String radius;
        String emission;
    }

    static class JsonAmbientLight {
        String intensity;
        String emission;
    }

    static class JsonScene {
        SceneData scene;
    }
}
