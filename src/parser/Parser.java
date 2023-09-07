//package parser;
//
//import geometries.*;
//
//import primitives.Color;
//import primitives.Double3;
//import primitives.Point;
//import primitives.Vector;
//
//import scene.Scene;
//
//import lighting.AmbientLight;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.NodeList;
//import org.w3c.dom.Node;
//import org.w3c.dom.Element;
//
//
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//
//import java.io.File;
//
//public class Parser {
//    public static void main(String[] args) {
//        Scene scene1 = XmlToScene("scene1");
//        Scene scene2 = XmlToScene("basicRenderTestTwoColors");
//
//        Scene scene3 = new Scene("scene3")
//                .setBackground(new Color(256, 0, 0))
//                .setAmbientLight(new AmbientLight(new Color(0, 0, 0), 1))
//                .addGeometries( new Sphere(50, new Point(0, 0, -100)) );
//
//        SceneToXml(scene3, "scene2");
//    }
//
//
//    public static void SceneToXml(Scene scene, String fileName){
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.newDocument();
//
//            // creating the xml
//
//            Element sceneElement = document.createElement("scene");
//            sceneElement.setAttribute("background-color", parseDouble3(scene.background.getRgb()));
//            document.appendChild(sceneElement);
//
//            Element ambientLightElement = document.createElement("ambient-light");
//            ambientLightElement.setAttribute("color",parseDouble3(scene.ambientLight.getIntensity().getRgb()));
//            sceneElement.appendChild(ambientLightElement);
//
//
//
//            Element geometries = parseGeometry(document, scene.geometries);
//            sceneElement.appendChild(geometries);
//
//
//
//            // writing to the file
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            DOMSource source = new DOMSource(document);
//            StreamResult result = new StreamResult(new File("xml/" + fileName + ".xml"));
//            transformer.transform(source, result);
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static Scene XmlToScene(String sceneName) {
//        try {
//            File file = new File("xml/" + sceneName + ".xml");
//
//            //an instance of factory that gives a document builder
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//
//            //an instance of builder to parse the specified xml file
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            Document doc = db.parse(file);
//
//            // get the root element of the XML document
//            Element root = doc.getDocumentElement();
//
//            // get the background color attribute of the scene element
//            String bgColorStr = root.getAttribute("background-color");
//            // parsing from Xml to Color
//            Color backgroundColor = parseColor(bgColorStr);
//
//            // get the ambient light element
//            Element ambientLightXml = (Element) root.getElementsByTagName("ambient-light").item(0);
//            String ambientLightColorStr = ambientLightXml.getAttribute("color");
//
//            // parsing from Xml to AmbientLight
//            Color ambientLightColor = parseColor(ambientLightColorStr);
//            AmbientLight ambientLight = new AmbientLight(ambientLightColor, 1.0);
//
//            // get the geometries element
//            Element geometriesStr = (Element) root.getElementsByTagName("geometries").item(0);
//
//            // parsing the geometries
//            Geometries geometries = new Geometries();
//
//            NodeList spheres = geometriesStr.getElementsByTagName("sphere");
//
//            for (int i = 0; i < spheres.getLength(); i++) {
//                Node sphere = spheres.item(i);
//                geometries.add(parseSphere((Element) sphere));
//            }
//
//
//            NodeList triangles = geometriesStr.getElementsByTagName("triangle");
//
//            for (int i = 0; i < triangles.getLength(); i++) {
//                Node triangle = triangles.item(i);
//                geometries.add(parseTriangle((Element) triangle));
//            }
//
//            NodeList planes = geometriesStr.getElementsByTagName("plane");
//
//            for (int i = 0; i < planes.getLength(); i++) {
//                Node plane = planes.item(i);
//                geometries.add(parsePlane((Element) plane));
//            }
//
//            return new Scene(sceneName)
//                    .setBackground(backgroundColor)
//                    .setAmbientLight(ambientLight)
//                    .addGeometries(geometries);
//
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static Sphere parseSphere(Element sphere) {
//        String sphereCenterStr = sphere.getAttribute("center");
//        String sphereRadiusStr = sphere.getAttribute("radius");
//
//        return new Sphere(Double.parseDouble(sphereRadiusStr), parsePoint(sphereCenterStr));
//    }
//
//    private static Triangle parseTriangle(Element triangle) {
//        String p0 = triangle.getAttribute("p0");
//        String p1 = triangle.getAttribute("p1");
//        String p2 = triangle.getAttribute("p2");
//
//        return new Triangle(parsePoint(p0), parsePoint(p1), parsePoint(p2));
//    }
//
//    private static Plane parsePlane(Element plane) {
//        String q0 = plane.getAttribute("q0");
//        String normal = plane.getAttribute("normal");
//
//        return new Plane(parsePoint(q0), parseVector(normal));
//    }
//
//
//    private static Color parseColor(String bgColor) {
//        return new Color(parseDouble3(bgColor));
//    }
//
//    private static Point parsePoint(String strPoint) {
//        return new Point(parseDouble3(strPoint));
//    }
//
//    private static Vector parseVector(String strVector) {
//        return new Vector(parseDouble3(strVector));
//    }
//
//    private static Double3 parseDouble3(String strPoint) {
//        String[] parts = strPoint.split("\\s+");
//        double[] result = new double[3];
//
//        for (int i = 0; i < 3; i++) {
//            result[i] = Double.parseDouble(parts[i]);
//        }
//
//        return new Double3(result[0], result[1], result[2]);
//    }
//
//    // parse Double3 to str
//    private static String parseDouble3(Double3 double3) {
//        return double3.parseString();
//    }
//
//
//    private static Element parseGeometry(Document doc, Geometries geometries) {
//        Element geometriesElement = doc.createElement("geometries");
//
//        for (Intersectable geo: geometries.getIntersectablesList()) {
//            if(geo instanceof Geometries) {
//                geometriesElement.appendChild(parseGeometry(doc, (Geometries)geo));
//            } else if(geo instanceof Sphere) {
//                geometriesElement.appendChild(parseSphere(doc, (Sphere) geo));
//            } else if(geo instanceof Plane) {
//                geometriesElement.appendChild(parsePlane(doc, (Plane) geo));
//            } else if(geo instanceof Triangle) {
//                geometriesElement.appendChild(parseTriangle(doc, (Triangle) geo));
//            }
//        }
//        return geometriesElement;
//    }
//
//    private static Element parseSphere(Document doc, Sphere sphere) {
//        Element sphereElement = doc.createElement("sphere");
//        sphereElement.setAttribute("center", parseDouble3(sphere.getCenter().getCoordinate()));
//        sphereElement.setAttribute("radius", String.valueOf(sphere.getRadius()));
//
//        return sphereElement;
//    }
//
//    private static Element parsePlane(Document doc, Plane plane) {
//        Element planeElement = doc.createElement("plane");
//        planeElement.setAttribute("q0", parseDouble3(plane.getPoint().getCoordinate()));
//        planeElement.setAttribute("normal", parseDouble3(plane.getNormal().getCoordinate()));
//
//        return planeElement;
//    }
//
//    private static Element parseTriangle(Document doc, Triangle triangle) {
//        Element triangleElement = doc.createElement("triangle");
//        triangleElement.setAttribute("p0",parseDouble3(triangle.getP1().getCoordinate()));
//        triangleElement.setAttribute("p1",parseDouble3(triangle.getP2().getCoordinate()));
//        triangleElement.setAttribute("p2",parseDouble3(triangle.getP3().getCoordinate()));
//        return triangleElement;
//    }
//}
//
