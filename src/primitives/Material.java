package primitives;

/**
 * this class is a PDS for the material of geometry
 * <br>
 * contains:
 * <ul>
 *     <li> a {@link Double3} value {@link Material#kD}</li>
 *     <li> a {@link Double3} value {@link Material#kS}</li>
 *     <li> a {@link Double3} value {@link Material#kT}</li>
 *     <li> a {@link Double3} value {@link Material#kR}</li>
 *     <li> a int value {@link Material#nShininess}</li>
 *     <li> a int value {@link Material#numRaysReflected}</li>
 *     <li> a double value {@link Material#coneAngleReflected}</li>
 *     <li> a int value {@link Material#numRaysRefracted}</li>
 *     <li> a double value {@link Material#coneAngleRefracted}</li>
 * </ul>
 *
 * @author danielattali, itiskvales
 */
public class Material {
    /**
     * the parameter for the Diffusive part of the Phonge Light Model
     */
    public Double3 kD = Double3.ZERO;

    /**
     * the parameter for the Specular part of the Phonge Light Model
     */
    public Double3 kS = Double3.ZERO;

    /**
     * the parameter for the Transparency part of the Phonge Light Model
     */
    public Double3 kT = Double3.ZERO;

    /**
     * the parameter for the Reflective part of the Phonge Light Model
     */
    public Double3 kR = Double3.ZERO;

    /**
     * the Shininess parameter in the Phonge Light Model
     */
    public int nShininess = 0;

    /**
     * the number of reflected rays, set to 1 to turn off Glossy Surfaces
     */
    public int numRaysReflected = 1;

    /**
     * the angle of the cone which we generate rays the bigger the cone angle the more the rays are scattered
     */
    public double coneAngleReflected = 0.0;

    /**
     * the number of reflected rays, set to 1 to turn off Diffused Glass
     */
    public int numRaysRefracted = 1;

    /**
     * the angle of the cone which we generate rays the bigger the cone angle the more the rays are scattered
     */
    public double coneAngleRefracted = 0.0;

    // =============== Setters ==================

    /**
     * @param kD the Diffusive Parameter for the Material
     * @return this According to the Builder Pattern
     */
    public Material setKD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * @param kS the Specular Parameter for the Material
     * @return this According to the Builder Pattern
     */
    public Material setKS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * @param kR the Reflectiveness Parameter for the Material
     * @return this According to the Builder Pattern
     */
    public Material setKR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * @param kT the Transparency Parameter for the Material
     * @return this According to the Builder Pattern
     */
    public Material setKT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * @param kD the Diffusive Parameter for the Material
     * @return this According to the Builder Pattern
     */
    public Material setKD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * @param kS the Specular Parameter for the Material
     * @return this According to the Builder Pattern
     */
    public Material setKS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * @param kR the Reflectiveness Parameter for the Material
     * @return this According to the Builder Pattern
     */
    public Material setKR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * @param kT the Transparency Parameter for the Material
     * @return this According to the Builder Pattern
     */
    public Material setKT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * @param nShininess the shininess Parameter for the Material
     * @return this According to the Builder Pattern
     */
    public Material setNShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * @param amount the amount of rays for Glossy Surfaces
     * @return this According to the Builder Pattern
     */
    public Material setNumRaysReflected(int amount) {
        this.numRaysReflected = amount;
        return this;
    }

    /**
     * @param angle the angle of the cone in degree
     * @return this According to the Builder Pattern
     */
    public Material setConeAngleReflected(double angle) {
        this.coneAngleReflected = Math.toRadians(angle);
        return this;
    }

    /**
     * @param amount the amount of rays for Diffused Glass
     * @return this According to the Builder Pattern
     */
    public Material setNumRaysRefracted(int amount) {
        this.numRaysRefracted = amount;
        return this;
    }

    /**
     * @param angle the angle of the cone in degree
     * @return this According to the Builder Pattern
     */
    public Material setConeAngleRefracted(double angle) {
        this.coneAngleRefracted = Math.toRadians(angle);
        return this;
    }
}
