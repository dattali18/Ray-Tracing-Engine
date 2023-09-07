package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import static java.awt.Color.BLACK;
import static lighting.PointLight.softShadowsRays;
import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.List;

/**
 * the RayTracerBasic class is the simplest ray tracer we can use, and it's used to trace ray
 *
 * @author danielattali, itiskvales
 */
public class RayTracerBasic extends RayTracerBase {
    /**
     * the const value of the maximum amount of recursion in calculating refracted and reflected rays
     */
    private static final int MAX_CALC_COLOR_LEVEL = 2;

    /**
     * the const value for the stopping condition in the recursion
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * the stating value of the parameter K in the function {@link RayTracerBasic#calcColor(GeoPoint, Ray, int, Double3)}
     */
    private static final double INITIAL_K = 1.0;


    // =========================== Ctor ===========================

    /**
     * ctor for the RayTracerBase
     *
     * @param scene the scene for our ray tracer
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    // =========================== Methods ===========================

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closetPoint = findClosestIntersection(ray);

        // calc the color at the point
        return closetPoint == null ? scene.background : calcColor(closetPoint, ray);
    }

    /**
     * @param gp the point which we want the color of
     * @return the color at the point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K));
    }

    /**
     * @param gp the point at which we want to calc the color
     * @param ray the ray hitting the geometry
     * @param level the level of recursion
     * @param k the parameter helping us calculate how much color each ray is giving to the final pixel
     * @return the color at the gp
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * @param gp the point at which we calculate the color
     * @param ray the ray hitting the geometry
     * @param k the parameter helping us calculate how much each ray is giving to the final pixel
     * @return the color at gp
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();

        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;

        Material material = gp.geometry.getMaterial();

        for (LightSource lightSource : scene.lights) {

            List<Vector> vectors = lightSource.getL2(gp.point);

            Double3 ktr = new Double3(0);

            for(Vector l : vectors) {
                double nl = alignZero(n.dotProduct(l));

                // sign(nl) == sing(nv)
                if (nl * nv > 0) {
                    ktr = ktr.add(transparency(gp, lightSource, l, n));

                }
            }

            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) {
                ktr = ktr.scale(((softShadowsRays == 0) ? 1 : ((double) 1 / softShadowsRays)));

                if(ktr.product(k).greaterThan(MIN_CALC_COLOR_K)) {

                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)
                            ));
                }
            }

        }
        return color;
    }

    /**
     * @param gp the point at which we calculate the color
     * @param ray the ray hitting the geometry
     * @param level the level of recursion
     * @param k the parameter helping us calculate how much color each ray is giving to the final pixel
     * @return the color at the gp
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRays(gp, v, n), level, k, material.kR)
                .add(calcGlobalEffect(constructRefractedRays(gp, v, n), level, k, material.kT));
    }


    /**
     * @param ray the ray hitting the geometry
     * @param level the level of recursion if level == 1 we stop the recursion
     * @param k the parameter helping us calculate how much color each ray is giving to the final pixel
     * @param kx a parameter helping us stop the recursion is the effect of the recursion is too small to notice
     * @return the color at the intersection with ray
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * @param rays the list of rays hitting the geometry
     * @param level the level of recursion if level == 1 we stop the recursion
     * @param k the parameter helping us calculate how much color each ray is giving to the final pixel
     * @param kx a parameter helping us stop the recursion is the effect of the recursion is too small to notice
     * @return the color at the intersection with ray
     */
    private Color calcGlobalEffect(List<Ray> rays, int level, Double3 k, Double3 kx) {
        Color color = new Color(BLACK);

        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;

        for(Ray ray: rays) {
            GeoPoint gp = findClosestIntersection(ray);
            if (gp == null) return scene.background.scale(kx);
            color = color.add(isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK : calcColor(gp, ray, level - 1, kkx).scale(kx));
        }
        return color.scale((double) 1 / rays.size());
    }

    /**
     * @param mat the material of the geometry
     * @param nl the value of the dot product between the normal to the surface and the vector from the light source to the geometry
     * @return the diffusive part of the light in the Phonge light model
     */
    private Double3 calcDiffusive(Material mat, double nl) {
        return mat.kD.scale(Math.abs(nl));
    }

    /**
     * @param mat the material of the geometry
     * @param n the normal from the surface of the geometry
     * @param l the vector from the light source to the geometry
     * @param nl the dot product of n and l
     * @param v the direction of the ray hitting the surface of the geometry
     * @return the specular part of the light in the Phonge light model
     */
    private Double3 calcSpecular(Material mat, Vector n , Vector l, double nl, Vector v) {
        Vector r = l.add(n.scale(-2 * nl)).normalize();

        double val = alignZero(Math.pow(-v.dotProduct(r), mat.nShininess));
        return mat.kS.scale(Math.max(0.0d, val));
    }


    /**
     * @param gp the geo-point of the intersection
     * @param l the vector from the light source to the geometry
     * @param n the normal from the surface of the geometry
     * @return true is there is no shadow else return true
     */
    @Deprecated
    @SuppressWarnings("unused")
    private boolean unshaded(GeoPoint gp, LightSource ls,  Vector l, Vector n) {

        Vector lightDirection = l.scale(-1);
        Ray rayShadow = new Ray(gp.point, lightDirection, n);

        double maxDistance = ls.getDistance(gp.point);

        List<GeoPoint> intersection = this.scene.geometries.findGeoIntersections(rayShadow, maxDistance);

        return (intersection == null);
    }

    /**
     * @param gp the geo-point of the intersection
     * @param ls the light-source
     * @param l the vector from the light source to the geometry
     * @param n the normal from the surface of the geometry
     * @return a value that will help us calc how much to shed or not
     */
    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n) {

        Vector lightDirection = l.scale(-1);
        Ray rayShadow = new Ray(gp.point, lightDirection, n);
        double maxDistance = ls.getDistance(gp.point);
        List<GeoPoint> intersection = this.scene.geometries.findGeoIntersections(rayShadow, maxDistance);

        if(intersection == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;
        Double3 MIN_VALUE = new Double3(0.00001d);

        for (GeoPoint gPoint: intersection) {
            ktr = gPoint.geometry.getMaterial().kT.product(ktr);
            if(ktr.lowerThan(MIN_VALUE))
                return Double3.ZERO;
        }

        return ktr;
    }

    /**
     * @param gp the GeoPoint at the surface of the geometry
     * @param v the dir of the original ray
     * @param n the normal to the surface of the geometry at the point of gp.point
     * @return the retracted ray
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
    }

    /**
     * @param gp the GeoPoint at the surface of the geometry
     * @param v the dir of the original ray
     * @param n the normal to the surface of the geometry at the point of gp.point
     * @return the retracted ray
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        Vector r = v.add(n.scale(-2 * n.dotProduct(v))).normalize();
        return new Ray(gp.point, r, n);
    }

    /**
     * Constructs a list of random reflected rays within the cone of the normal vector at the given surface point.
     *
     * @param gp The GeoPoint at the surface of the geometry.
     * @param v The direction of the original ray.
     * @param n The normal to the surface of the geometry at the point of gp.point.
     * @return A list of random reflected rays within the cone of the normal vector.
     */
    private List<Ray> constructReflectedRays(GeoPoint gp, Vector v, Vector n) {
        Material material = gp.geometry.getMaterial();

        if (material.numRaysReflected == 1 || isZero(material.coneAngleReflected))
            return List.of(constructReflectedRay(gp, v, n));

        List<Ray> rays = new ArrayList<>();

        // Generate random direction vectors within the cone of the normal vector
        List<Vector> randomDirection = Vector.generateRandomDirectionInCone(gp, n, material.coneAngleReflected, material.numRaysReflected);

        // Construct rays using the random direction vectors and add them to the list
        for (int i = 0; i < randomDirection.size() && i < material.numRaysReflected; i++) {
            Vector u = randomDirection.get(i);
            Ray reflectedRay = new Ray(gp.point, u, n);
            rays.add(reflectedRay);
        }

        rays.add(constructRefractedRay(gp, v, n));

        return rays;
    }

    /**
     * Constructs a list of random refracted rays within the cone of the inverted normal vector at the given surface point.
     *
     * @param gp The GeoPoint at the surface of the geometry.
     * @param v The direction of the original ray.
     * @param n The normal to the surface of the geometry at the point of gp.point.
     * @return A list of random refracted rays within the cone of the inverted normal vector.
     */
    private List<Ray> constructRefractedRays(GeoPoint gp, Vector v, Vector n) {
        Material material = gp.geometry.getMaterial();

        if (material.numRaysRefracted == 1 || isZero(material.coneAngleRefracted))
            return List.of(constructRefractedRay(gp, v, n));

        List<Ray> rays = new ArrayList<>();

        // Generate random direction vectors within the cone of the inverted normal vector
        List<Vector> randomDirection = Vector.generateRandomDirectionInCone(gp, v, material.coneAngleRefracted, material.numRaysRefracted);

        // Construct rays using the random direction vectors and add them to the list
        for (int i = 0; i < randomDirection.size() && i < material.numRaysRefracted; i++) {
            Vector u = randomDirection.get(i);
            Ray refractedRay = new Ray(gp.point, u, n);
            rays.add(refractedRay);
        }
        rays.add(constructRefractedRay(gp, v, n));

        return rays;
    }


    /**
     * @param ray the ray from which we get the intersection
     * @return the closest intersection as GeoPoint or null if there is no intersection
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> points = this.scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(points);
    }

}
