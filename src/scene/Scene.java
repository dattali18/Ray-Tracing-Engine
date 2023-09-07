package scene;

import geometries.Geometries;
import geometries.Intersectable;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * this class will represent a scene in our Ray Tracing Project
 *
 * <br>
 * contains:
 * <ul>
 *     <li>a {@link Scene#name}</li>
 *     <li>a {@link Scene#background} color</li>
 *     <li>a {@link Scene#ambientLight}</li>
 *     <li>a {@link Scene#geometries}</li>
 * </ul>
 *
 * @author danielattali, itiskvales
 */
public class Scene {

    /**
     * the name of the scene
     */
    public String name;

    /**
     * the color of the background
     */
    public Color background = Color.BLACK;

    /**
     * the ambient light, is init to NONE
     */
    public AmbientLight ambientLight = AmbientLight.NONE;


    /**
     * the 3D shapes in our scene, is init to an empty list (geometries is a composite class)
     */
    public Geometries geometries = new Geometries();

    /**
     * the light in our scene
     */
    public List<LightSource> lights = new LinkedList<>();

    // ========================= Ctor =============================

    /**
     * @param name the scenes name
     */
    public Scene(String name) {
        this.name = name;

        geometries = new Geometries();
    }

    // ====================== Builder pattern ===========================
    // by Eliezer request

    /**
     * this is a static class for building the scene according to the Builder Pattern
     */
    public static class SceneBuilder {
        /**
         * the name of the scene
         */
        private String name = null;

        /**
         * the background color
         */
        private Color background = null;

        /**
         * the ambientLight of the scene
         */
        private AmbientLight ambientLight = null;

        /**
         * the list of lights in our scene
         */
        private List<LightSource> lights = null;

        /**
         * the 3D shapes in our scene, is init to an empty list (geometries is a composite class)
         */
        private Geometries geometries = null;

        /**
         * @param name the scenes name
         * @return this according to the Builder pattern
         */
        public SceneBuilder setName(String name){
            this.name = name;
            return this;
        }
        /**
         * @param background the background color of the scene
         * @return this according to the Builder pattern
         */
        public SceneBuilder setBackGround(Color background) {
            this.background = background;
            return this;
        }
        /**
         * @param ambientLight an ambient light in the scene
         * @return this according to the Builder pattern
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }
        /**
         * @param geometries is the shapes in the scene
         * @return this according to the Builder pattern
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        /**
         * @param lights a list of light in our scene
         * @return this according to the Builder pattern
         */
        public SceneBuilder setLightSource(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        /**
         * initialize the scene class by the scene builder
         * @return the scene after the building
         */
        public Scene build() {
            if(this.name != null && this.background != null && this.ambientLight != null && this.geometries != null && this.lights != null)
                return new Scene(this);
            throw new MissingResourceException("One of the following field is null: name, background, ambientLight, geometries", "SceneBuilder", "");
        }
    }

    /**
     * @param builder the scene builder after all the field were set
     */
    private Scene(SceneBuilder builder) {
        this.name = builder.name;
        this.background = builder.background;
        this.ambientLight = builder.ambientLight;
        this.geometries = builder.geometries;
        this.lights = builder.lights;
    }

    // ======================= Setter / Getter (builder pattern) ==========================

    /**
     * @param name the name of the scene
     * @return the object itself according to the builder design pattern
     */
    public Scene setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @param background the background color of the scene
     * @return the object itself according to the builder design pattern
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * @param ambientLight the ambient light of the scene
     * @return the object itself according to the builder design pattern
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * @param geometries the geometries (Shapes) in our scene
     * @return the object itself according to the builder design pattern
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * @param light the light source
     * @return the object itself according to the builder design pattern
     */
    public Scene setLight(LightSource light) {
        this.lights = List.of(light);
        return this;
    }

    /**
     * @param geometries the geometries (Shapes) in our scene
     * @return the object itself according to the builder design pattern
     */
    public Scene addGeometries(Intersectable... geometries) {
        this.geometries.add(geometries);
        return this;
    }

    /**
     * @param geometries the geometries (Shapes) in our scene
     * @return the object itself according to the builder design pattern
     */
    public Scene addAllGeometries(List<Intersectable> geometries) {
        this.geometries.addAll(geometries);
        return this;
    }

    /**
     * @param lightSources a list of light for our scene
     * @return the object itself according to the builder design pattern
     */
    public Scene addLights(LightSource ... lightSources) {
        Collections.addAll(this.lights, lightSources);
        return this;
    }

    /**
     * @param lights a list of light for our scene
     * @return the object itself according to the builder design pattern
     */
    public Scene SetLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * @return all the geometry in our scene
     */
    public List<Intersectable> getGeometries() {
        return geometries.getGeometries();
    }
}

