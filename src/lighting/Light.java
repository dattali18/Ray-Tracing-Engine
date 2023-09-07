package lighting;

import primitives.Color;

/**
 * the class Light will represent a light source in our scene
 */
abstract class Light {
    /**
     * color of the light
     */
    private Color intensity;

    // ====================== Ctor ======================

    /**
     * @param intensity the color intensity of the light source
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * @return the color of the light
     */
    // ====================== Getters ======================
    public Color getIntensity() {
        return this.intensity;
    }
}
