package animation;

import geometries.*;
import primitives.*;

public class Pawn {
    private Geometries geometries;
    private Cylinder c1, c2, c3, c4, c5;
    private Sphere s1;

    public Pawn(Point p, Vector dir, double height) {
        c1 = new Cylinder(height/4, new Ray(p, dir), height/10);
        c2 = new Cylinder(height/6, new Ray(p.add(dir.scale(height/10)), dir), height/10);
        c3 = new Cylinder(height/8, new Ray(p.add(dir.scale(height/5)), dir), height/2);
        c4 = new Cylinder(height/6, new Ray(p.add(dir.scale(7 * height/10)), dir), height/16);

        s1 = new Sphere(p.add(dir.scale((double) 61 / 80 * height)), height / 8);

        c5 = new Cylinder(height / 20, new Ray(p.add(dir.scale((double) 70 /80 * height)), dir), height / 18);

        geometries = new Geometries(c1, c2, c3, c4, c5, s1);

    }

    public Pawn(Point p, Vector dir, double height, Color emission, Material material) {
        this(p, dir, height);

        setEmission(emission);
        setMaterial(material);
    }

    public Pawn setEmission(Color emission) {
        c1.setEmission(emission);
        c2.setEmission(emission);
        c3.setEmission(emission);
        c4.setEmission(emission);
        c5.setEmission(emission);
        s1.setEmission(emission);

        return this;
    }

    public Pawn setMaterial(Material material) {
        c1.setMaterial(material);
        c2.setMaterial(material);
        c3.setMaterial(material);
        c4.setMaterial(material);
        c5.setMaterial(material);
        s1.setMaterial(material);

        return this;
    }

    public Geometries getGeometries() {
        return geometries;
    }
}
