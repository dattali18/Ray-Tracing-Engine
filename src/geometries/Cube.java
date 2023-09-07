package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public class Cube {
    private Geometries geometries = new Geometries();
    private final Triangle[] triangles = new Triangle[12];
    private final Point[] points = new Point[8];

    private final Vector u;

    private final Vector v;

    private final Vector w;

    public Cube(Vector u_, Vector v_, Vector w_, Point p0, double factor) {
        this.u = u_.scale(factor);
        this.v = v_.scale(factor);
        this.w = w_.scale(factor);

        points[0] = p0;
        points[1] = p0.add(u);
        points[2] = p0.add(v);
        points[3] = p0.add(u).add(v);
        points[4] = p0.add(w);
        points[5] = p0.add(u).add(w);
        points[6] = p0.add(v).add(w);
        points[7] = p0.add(v).add(u).add(w);

        // base
        triangles[0] = new Triangle(points[0], points[1], points[3]);
        triangles[1] = new Triangle(points[0], points[2], points[3]);

        // top
        triangles[2] = new Triangle(points[4], points[5], points[7]);
        triangles[3] = new Triangle(points[4], points[6], points[7]);

        // left
        triangles[4] = new Triangle(points[1], points[3], points[7]);
        triangles[5] = new Triangle(points[1], points[5], points[7]);

        // right
        triangles[6] = new Triangle(points[2], points[3], points[6]);
        triangles[7] = new Triangle(points[3], points[6], points[7]);

        // front
        triangles[8] = new Triangle(points[0], points[2], points[6]);
        triangles[9] = new Triangle(points[0], points[6], points[4]);

        // back
        triangles[10] = new Triangle(points[0], points[1], points[4]);
        triangles[11] = new Triangle(points[1], points[4], points[5]);

        geometries.add(triangles);
    }

    public Cube setMaterial(Material material) {
        for (int i = 0; i < 12; i++) {
            triangles[i].setMaterial(material);
        }
        return this;
    }

    public  Cube setEmission(Color emission) {
        for (int i = 0; i < 12; i++) {
            triangles[i].setEmission(emission);
        }
        return this;
    }

    public Triangle[] getCube() {
        return triangles;
    }

    public Geometries getGeometries() {
        return geometries;
    }
}
