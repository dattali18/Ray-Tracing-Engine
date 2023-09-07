package animation;

import geometries.Cube;
import geometries.Cylinder;
import geometries.Geometries;
import geometries.Sphere;
import primitives.*;

public class Queen {
    private Geometries geometries;

    private Cylinder c1, c2, c3, c4, c5;

    private Cube t1, t2, t3, t4;

    private Sphere s1, s2;

    public Queen(Point p, Vector dir, double height, Vector vX, Vector vY) {
        c1 = new Cylinder(height/3, new Ray(p, dir), height/10);
        c2 = new Cylinder(height/5, new Ray(p.add(dir.scale(height/10)), dir), height/10);
        c3 = new Cylinder(height/8, new Ray(p.add(dir.scale(height/5)), dir), height/1.5);
        c4 = new Cylinder(height/5, new Ray(p.add(dir.scale(13 * height/15)), dir), height/16);
        c5 = new Cylinder(height / 4, new Ray(p.add(dir.scale((double) 223 /240 * height)), dir), height / 16);

        double h1 = (double) 223 /240 * height;
        double h2 = (double) 1 / 10 * height;

        double epsilon = (double) 1 / 6 * height;

        Point p0 = p.add(dir.scale(h1));
        p0 = p0.add(vX.scale(-epsilon / 2)).add(vY.scale(-epsilon / 2));

        Vector v1 = vX.scale(epsilon).add(vY.scale(0.5));
        Vector v2 = vY.scale(epsilon).add(vX.scale(0.5));

        t1 = new Cube(vX.scale(1.4), vY, dir.scale(2), p0.add(v1), h2);
        t2 = new Cube(vX.scale(1.4), vY, dir.scale(2), p0.add(v1.scale(-1)), h2);
        t3 = new Cube(vY.scale(1.4), vX, dir.scale(2), p0.add(v2), h2);
        t4 = new Cube(vY.scale(1.4), vX, dir.scale(2), p0.add(v2.scale(-1)), h2);

        p0 = p.add(dir.scale(h1));

        s1 = new Sphere(epsilon, p0.add(dir.scale(epsilon / 2)));
        s2 = new Sphere(epsilon/ 3, p0.add(dir.scale(epsilon * 1.5)));

        geometries = new Geometries(c1, c2, c3, c4, c5, t1.getGeometries(), t2.getGeometries(), t3.getGeometries(), t4.getGeometries(), s1, s2);
    }

    public Queen(Point p, Vector dir, double height, Vector vX, Vector vY, Color emission, Material material) {
        this(p, dir, height, vX, vY);

        setEmission(emission);
        setMaterial(material);
    }

    public Queen setEmission(Color emission) {
        c1.setEmission(emission);
        c2.setEmission(emission);
        c3.setEmission(emission);
        c4.setEmission(emission);
        c5.setEmission(emission);

        t1.setEmission(emission);
        t2.setEmission(emission);
        t3.setEmission(emission);
        t4.setEmission(emission);

        s1.setEmission(emission);
        s2.setEmission(emission);


        return this;
    }

    public Queen setMaterial(Material material) {
        c1.setMaterial(material);
        c2.setMaterial(material);
        c3.setMaterial(material);
        c4.setMaterial(material);
        c5.setMaterial(material);

        t1.setMaterial(material);
        t2.setMaterial(material);
        t3.setMaterial(material);
        t4.setMaterial(material);

        s1.setMaterial(material);
        s2.setMaterial(material);

        return this;
    }

    public Geometries getGeometries() {
        return geometries;
    }
}

