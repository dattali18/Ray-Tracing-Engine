package geometries;

import primitives.*;

import java.util.Objects;


/**
 * Geometry interface represent a geometric object in 3D space
 * this interface is used in every Geometric object in our project
 * @author danielattali, itiskvales
 */
public abstract class Geometry extends Intersectable {

    // ==================== Field ====================

    /**
     * the 'color' emission of the geometry
     */
    protected Color emission = Color.BLACK;

    /**
     * the material of the geometry
     */
    private Material material = new Material();

    // ==================== Setter / Getter (Builder Pattern) ====================

    /**
     * @param emission the color
     * @return this according to the Builder Pattern
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * @param material the material
     * @return this according to the Builder Pattern
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * @return the material of the geometry
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @return the color of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    // ==================== Abstract Methods ====================

    /**
     * function getNormal() return the normal vector to the object at the Point: point
     * @param point the point which the normal vector is sitting on
     *              <br>
     *             <font color='red'><b>we assume that the point is on the object, we are <u>not</u> checking inside the function</b></font>
     * @return a new {@link Vector} which is normal to the object at the point given
     */
    public abstract Vector getNormal(Point point);


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Geometry geometry)) return false;
        return Objects.equals(getEmission(), geometry.getEmission());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmission());
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "emission=" + emission +
                "} ";
    }
}
