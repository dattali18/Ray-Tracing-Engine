package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * this abstract class will represent a basic ray tracer for our project
 * <br>
 * contains:
 * <ul>
 *     <li>a {@link RayTracerBase#scene} object</li>
 * </ul>
 *
 * @author danielattali, itiskvales
 */
public abstract class RayTracerBase {
    /**
     * the {@link Scene} will represent a scene
     */
    protected Scene scene;

    // ====================== Ctor ==========================

    /**
     * ctor for the RayTracerBase
     * @param scene the scene for our ray tracer
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    // ==================== Methods ==========================

    /**
     * @param ray the ray we will trace
     * @return the color at the first intersection of the ray
     */
    public abstract Color traceRay(Ray ray);
}
