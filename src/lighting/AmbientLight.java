package lighting;

import primitives.Color;
import primitives.Double3;

import java.util.Objects;

/**
 * this class is representing an AmbientLight in our Ray Tracing Project
 * <br>
 * contains:
 * <ul>
 *     <li>a {@link AmbientLight#NONE} content which represent no ambient light</li>
 * </ul>
 * @author danielattali, itiskvales
 */
public class AmbientLight extends Light {

    /**
     * NONE is a const that represent no ambient light
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * ctor receive param to calculate the intensity (Color) of the background
     * @param IA original intensity (Color) of the background
     * @param kA the parameter deciding the intensity of the color
     */
    public AmbientLight(Color IA, Double3 kA) {
        super(IA.scale(kA));
    }

    /**
     * ctor receive param to calculate the intensity (Color) of the background
     * @param IA original intensity (Color) of the background
     * @param kA the parameter deciding the intensity of the color
     */
    public AmbientLight(Color IA, double kA) {
        super(IA.scale(kA));
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmbientLight that)) return false;
        return Objects.equals(getIntensity(), that.getIntensity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIntensity());
    }

    @Override
    public String toString() {
        return "AmbientLight{" +
                "intensity=" + this.getIntensity() +
                '}';
    }
}
