# Project Overview


This is a project of computer graphic, specifically a `Ray Tracing` engin.

This project is used to learn about some concepts and advanced topics in `Software Engineering`.

This project is the Practical course to the Intro to Software Engineering course
in this project we use java 17 to create and implement a raytracing engin
using some fundamental design pattern.

## Authors

- [@Daniel](https://github.com/dattali18/)
- [@Itzick](https://github.com/itzickw/)

Project Structure
---

Here is the tree of our project at the end of mini-project-1:

```bash
./src
├── geometries/
│   ├── AxisAlignedBoundingBox.java
│   ├── Boundable.java
│   ├── Cube.java
│   ├── Cylinder.java
│   ├── Geometries.java
│   ├── Geometry.java
│   ├── Intersectable.java
│   ├── Plane.java
│   ├── Polygon.java
│   ├── RadialGeometry.java
│   ├── Sphere.java
│   ├── Triangle.java
│   └── Tube.java
├── lighting/
│   ├── AmbientLight.java
│   ├── DirectionalLight.java
│   ├── Light.java
│   ├── LightSource.java
│   ├── PointLight.java
│   └── SpotLight.java
├── parser/
│   ├── JsonParser.java
│   └── Parser.java
├── primitives/
│   ├── Color.java
│   ├── Double3.java
│   ├── Material.java
│   ├── Pixel.java
│   ├── Point.java
│   ├── Ray.java
│   ├── Util.java
│   └── Vector.java
├── renderer/
│   ├── Camera.java
│   ├── ImageWriter.java
│   ├── RayTracerBase.java
│   └── RayTracerBasic.java
└── scene/
    └── Scene.java
```

Software Engineering Concepts
---

- `TDD` ([Test-driven-development](https://en.wikipedia.org/wiki/Test-driven_development))
  in the project we learned how to use `unittesting` in order to check and balance our project.
- `RDD` ([Responsibility-driven design](https://en.wikipedia.org/wiki/Responsibilitydriven_design#:~:text=Responsibility%2Ddriven%20design%20is%20a,information%20that%20the%20object%20shares.))
  in this project we learned how we should choose who's responsible for what
  Examples from our project:
  1. in the picture improvement we chose that the function that will be used to generate `Points` for `Super Sampling` will be in the `Point.java` class
  2. in the picture improvement we chose that the function that will be used to generate `Vector` inside a`Cone` will be in the `Vector.java` class
  3. and so on...
- `Hard Code` we learned to not make our code rigid, for example using setter for some of our picture improvement so the user as the final say on if and how the effect will work.
- `Abstarction` or looking at some of the component of our code as `Black Box`, it is very useful in order to divide our goal into smaller achiveable tasks.
- `Law of Demeter` the law that stat that "Only talk to your immediate friends", was used in  every class in our project.
- `DRY` (Don't Repeat Yourself), don't copy code, write once use everywhere else.
- `KISS` (Keep it simple stupid) don't make your code overly complicated.

Design Patterns
---

here some of the pattern you can find in the project:

- `Builder` pattern in the `Scene.java` class
- `Composit` pattern in the `Geomtries.java` class
- `Wrapepr` pattern in the `Color.java` class
- 

and more!

3D Objects
---

here some of the 3D object supported in our project:

- Cylinder
- Tube
- Plane
- Polygon
- Triangle
- Sphere
- Cube (and all [Cuboid](https://en.wikipedia.org/wiki/Cuboid))

Lights
---

here's some fo the different light supported in our project:

- Ambient Light
- Directional Light
- Point Light
- Spotlight (with an option to set the beam angle)

Camera
---

our camera support:

- changing View Plane (width, height, distance)
- changing the view angle
  - rolling 
  - yawning
  - pitching
- camera movement
  - Up and Down (according to the camera up vector)
  - Forward and Backward (according to the camera to vector)
  - Right and Left (according to the camera right vector)

### Example of changing `Camera` angle

---

<img alt="Before" height="230" src="./images/picture improvement/40.png" /> <img alt="Before" height="230" src="./images/picture improvement/49.png" /> <img alt="Before" height="230" src="./images/picture improvement/58.png" /> <img alt="Before" height="230" src="./images/picture improvement/76.png" />
 

here is a link to a wikipedia article explaining the different view angle **[here](https://en.wikipedia.org/wiki/Aircraft_principal_axes)**

## Math

Math played a big rule in the whole project so here is a glips of some of it:

Vector Math
---

$` |v| = \sqrt {x^2 + y^2 + z^2} `$

$` u \bullet v = |u| \bullet |v| \bullet cos{\theta} `$

$`normlized(v) = \frac{v}{|v|}`$

$` u \times v = |u| \bullet |v| \bullet sin{\theta} \bullet n `$

$` u \times v = \left( \begin{array}{c} v_1 \\ v_2 \\ v_3 \end{array} \right) \times  \left( \begin{array}{c} u_1 \\ u_2 \\ u_3 \end{array} \right) = 
\left( \begin{array}{c} u_2 \bullet v_3 - u_3 \bullet v_2 \\ u_3 \bullet v_1 - u_1 \bullet v_3 \\ u_1 \bullet v_2 - u_2 \bullet v_1 \end{array} \right)
`$

Surface normal/intersection
---

- Polygon/Triangle/Plane normal

   $` \vec v_1 = \vec{P_2 - P_1}  `$

   $` \vec v_2 = \vec{P_3 - P_1}  `$

   $` \vec n = normalize(\vec v_1 \times \vec v_2)  `$

- Sphere/Tube normal

   $` \vec n = normalize(\vec {P - O})  `$

   finding O for Tube

   $` t = \vec v \bullet \vec {(P -P_0)} `$

   $` O = P_0 + t\bullet \vec v`$
  
- Ray-Sphere intersection
 
  $` P = P_0 + t \bullet \vec v,   t > 0  `$

   $` |P - O|^2 - r^2 = 0 `$

   $` \vec u = \vec {(O - P_0)} `$

   $` t_m = \vec v \bullet \vec u `$

   $` d = \sqrt{ |\vec u|^2 -t_m^2 } `$

   $` t_h = \sqrt { r^2 - d^2 } `$

   $` t_{1,2} = t_m \pm t_h, P_i = P_0 + t_i \bullet \vec v `$

- Plane/Polygon/Triangle
  
   $` P = P_0 + t \bullet \vec v,   t > 0  `$

  $` t = \frac{\vec n \bullet \vec {(Q - P_0)} }{\vec n \bullet \vec v} `$

Phong Light Model
---

$` I_P = k_A \bullet I_A + I_E + \displaystyle\sum_{i}^{} \left( k_D \bullet |l_i \bullet n| + k_S \bullet (max(0, -v \bullet r))^{n_{sh}}  \right) \bullet I_{L_i}  `$

- directional light
  
  $` I_L = I_0 `$
  
- point light

   $` I_L = \frac{I_0}{k_c + k_l \bullet d + k_q \bullet d ^2 } `$
  
- spotlight

   $` I_L = \frac{I_0 \bullet max(0, \vec dir \bullet \vec l)}{k_c + k_l \bullet d + k_q \bullet d ^2 } `$

# Report Mini Project Part 1

## Picture Improvement

1. [Anti-Aliasing](https://en.wikipedia.org/wiki/Anti-aliasing) (with variable number of aliasing rays).
2. [Soft-Shadow](https://en.wikipedia.org/wiki/Shadow_mapping) (with variable number of shadow ray and light length/radius).
3. [Depth-Of-Field](https://en.wikipedia.org/wiki/Depth_of_field) (with variable number of focal rays and lens radius).
4. 1.  [Glossy Surfaces](https://en.wikipedia.org/wiki/Gloss_(optics)) (with variable randomness of rays).
   2. Diffused (Blurry) Glass (with variable randomness of rays).

---

## Anti-Aliasing

For antialiasing, we take a sample of beam of rays and take the average color of the pixel.

In order create the beam of rays, Inside the `Point.java` class we add a `static` function that will generate an amount of random point on a Plane (for antialiasing it's the `View Plane`)
inside each Pixel, using the [Jittered Pattern](https://en.wikipedia.org/wiki/Jitter) (which is a combination of randomness and grid)
inside the function of ```generatePoints() ```.

```java
public class Point {
   ...

    /**
     * @param vX the x vector of the plane
     * @param vY the y vector of the plane
     * @param amount the amount of point to generate
     * @param center the 'center' of the generation
     * @param size the size of the circle of the generation
     * @return a list of point generated using Jittered Pattern inside a circle around center
     */
    public static List<Point> generatePoints(Vector vX, Vector vY, int amount, Point center, double size) {
        List<Point> points = new LinkedList<>();

        //amount = (int) (1.273 * amount);

        double divider = Math.sqrt(amount);
        double r = size / divider;

        //double size2 = size * size;

        // divide each pixel into sub-pixel and jitter a bit inside the sub-pixel
        for (int k = 0; k < divider; k++) {
            for (int l = 0; l < divider; l++) {
                // findinf the center of the sub-pixel
                double yI = alignZero(-(k - (divider - 1) / 2) * r);
                double xJ = alignZero(-(l - (divider - 1) / 2) * r);

                Point pIJ = center;

                if (xJ != 0) pIJ = pIJ.add(vX.scale(xJ));
                if (yI != 0) pIJ = pIJ.add(vY.scale(yI));

                // adding some random jitter
                pIJ = pIJ.add(generateVector(vX, vY, r));

                //if(alignZero(pIJ.distanceSquared(center)) < size2)
                points.add(pIJ);
            }
        }

        return points;
    }
}
```

> if you uncomment the comment above it will generate point inside a circle and not a square.

In the `Camera.java` class we add a new field called `aliasRays` which is used to determine if the antialiasing is `on` or `off`, and also how many rays do we send to sample each pixel.

```java
 /**
     * The amount of rays that will be shot in each row and column,
     * in all picture improvements.
     * (set 1 to `turn off` the action)
     */
    private int aliasRays = 1;
```

Also in the `Camera.java` class we add a new method called `constructRays()` which instead or returning a `Ray` like the old method will return `List<Ray>` which is the beam of sampling rays for each pixel.

```java
public class Camera {
   ...

    /**
     * @param nX the line width
     * @param nY the column height
     * @param i the column of the pixel
     * @param j the line of the pixel
     * @return the rays from the camera to the of the pixel[i,j] + a random factor according to the Jittered Pattern
     */
    public List<Ray> constructRays(int nX, int nY, int j, int i) {
        // check if the antialiasing is 'on' or 'off'
        if (aliasRays == 1)
            return List.of(constructRay(nX, nY, j, i));

        List<Ray> rayBeam = new LinkedList<>();
        // finding the center of the pixel
        Point pCenter = p0.add(this.vTo.scale(this.distance));

        double rY = this.height / nY;
        double rX = this.width / nX;

        double size = Math.min(rY, rX);

        double yI = alignZero(-(i - (double) (nY - 1) / 2) * rY);
        double xJ = alignZero((j - (double) (nX - 1) / 2) * rX);

        Point pIJ = pCenter;

        // checking xJ != 0 and yI != 0 because if we scale by 0 we get a 0 vector which will raise an Exception
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));

        if (aliasRays > 1 && lensRadius == 0.0) {

            // Constructing (rays * rays) rays in random directions.
            // generting point around the center of the pixel aournd a circle or square
            List<Point> points = generatePoints(vRight, vUp, aliasRays, pIJ, size);

            for (int k = 0; k < points.size() && k < aliasRays; k++) {
                rayBeam.add(
                        new Ray(p0, points.get(k).subtract(p0))
                );
            }

        }

        return rayBeam;
    }
}
```

Then in the `Camera.java` class we call the new `constructRays()` function and we calc the average color of the pixel.
We create a new function called `getAveragePixelColor()` to calc the average color at the pixel.


```java
public class Camera {
   ...
   
   /**
    * @param nX the line width
    * @param nY the column height
    * @param j the column of the pixel
    * @param i the line of the pixel
    * @return the average color at the pixel[i, j]
    */
   private Color getAveragePixelColor(int nX, int nY, int i, int j) {
      List<Ray> rays = constructRays(nX, nY, i, j);

      Color color = Color.BLACK;

      for (Ray ray : rays)
         color = color.add(rayTracer.traceRay(ray));

      return color.scale((double) 1 / rays.size());
   }
}

```

Then instead of calling the `castRay()` function we call the `getAveragePixelColor()` function in the `renderImage()` function.

> In order to not make our code a `hard` we also have setter for `aliasRays`.
> ```java
> /**
> * @param aliasRays the number of aliasing rays (set to 1 to 'turn of' the antialiasing)
> * @return the camera object according to the builder pattern
> */
> public Camera setAliasRays(int aliasRays) {
>   if (aliasRays < 1)
>       throw new IllegalArgumentException("The number of rays must be greater then 0!");
>   this.aliasRays = aliasRays;
>   return this;
> }
> ```



### Example of Anti-Aliasing Inside the [Cornell Box]("https://en.wikipedia.org/wiki/Cornell_box")

---

<img alt="Before" height="400" src="./images/picture improvement/base-big.png" /> <img alt="Before" height="400" src="./images/picture improvement/anti-aliasing-big.png"/>

> You can clearly see the difference, especially in the reflection of the  `Sphere`.


---

## Soft-Shadow

For soft shadow, we need to give the light source a `width` or `radius` but our light is a "Perfect Light"
because it's only a `Point` in space it doesn't have any volume, so we will add it Synthetically by generating
`Points` around the light source on a plane which is orthogonal to the `Vector` `L` which was originally given by the `getL()` function,
so in order to do that we will add a new function to the `LightSource` interface `getL2()` which we return `List<Vector>`
which are vector from the light source to the `Point` of intersection.

```java
public interface LightSource {
   ...
   
    /**
     * Gets vectors from the given point to the light source
     *
     * @param p the point
     * @return all vectors who created
     */
    public List<Vector> getL2(Point p);
}
```

and then we will implement it inside the `PointLight` class.

```java
public class PointLight extends Light implements LightSource {
   ...
   @Override
   public List<Vector> getL2(Point p) {
       // check if soft shadow it 'On' or 'off'
      if (lengthOfTheSide == 0) return List.of(getL(p));

      List<Vector> vectors = new LinkedList<>();
      Vector l = getL(p);
      // plane of the light
      Plane plane = new Plane(this.p, l);

      // vectors of the plane
      List<Vector> vectorsOfThePlane = plane.findVectorsOfPlane();
      Vector u = vectorsOfThePlane.get(0), v = vectorsOfThePlane.get(1);

      List<Point> points = generatePoints(u, v, softShadowsRays, this.p, lengthOfTheSide);

      for (int k = 0; k < points.size() && k < softShadowsRays; k++) {
         vectors.add(
                 p.subtract(points.get(k))
         );

      }

      vectors.add(l);

      return vectors;
   }
}
```

> Note that we are not Implementing a different function in `DirectionalLight` because the shadow is always hard.
> here is the implementation of the `getL2()` function in the `DirecionalLight.java` class
> ```java
> @Override
> public List<Vector> getL2(Point p) {
>    return List.of(getL(p));
> }
> ```


> Note that we generate two `Vector` from the plane we created with `p` and `l`, the formula we used is very simple
> if our `Vector` is $\vec v=(\alpha, \beta, \gamma)$ we need to find a new `Vector` $\vec u$ such as $\vec u \bullet \vec v = 0$, so we can have a few solution to that equation
> 1. $\vec u = (-\beta, \alpha, 0)  \Rightarrow \vec u \bullet \vec v = 0$
> 2. $\vec u = (-\gamma, 0, \alpha) \Rightarrow \vec u \bullet \vec v = 0$
> 3. ....
>
> so we need to find the first none 0 item in our `Vector` and create a new `Vector` that will be orthogonal to the first
> and the can find the seconde vector by using $u \times v$.
>
> the code for this is in the `Plane.java` class
> ```java
> public List<Vector> findVectorsOfPlane() {
>   List<Vector> vectors = new LinkedList<>();
>   Vector normalVector = getNormal();
>        Point p0 = this.q0;
>
>        double nX = normalVector.getX(), nY = normalVector.getY(), nZ = normalVector.getZ();
>        double pX = p0.getX(), pY = p0.getY(), pZ = p0.getZ();
>
>        double[] normal = {nX, nY, nZ};
>        double d = -(nX * pX + nY * pY + nZ * pZ);
>
>        int i;
>        double val = 0;
>        for (i = 0; i < 3; i++) {
>            if(!isZero(normal[i])) {
>                val = normal[i];
>                break;
>            }
>        }
>
>        Vector v1 = null;
>        switch (i) {
>            case 0 -> v1 = new Vector(val, 0, 0);
>            case 1 -> v1 = new Vector(0, val, 0);
>            case 2 -> v1 = new Vector(0, 0, val);
>        }
>
>        assert v1 != null;
>        Vector v2 = v1.crossProduct(normalVector);
>       return List.of(v1.normalize(), v2.normalize());
> }
> ```

In order to make our code more customizable to the user we added a field in the `PointLight` class,
`lengthOfTheSide` to determine the `width` or `radius` of the light,
and when the value of `lenghtOfTheSide == 0` the `soft shadow` is `off` otherwise it's `on`.

The user as also another degree of freedom he can also choose how many shadow `Ray` will be cast,
we added another field in the `PointLight` class, `softShadowsRays` which will represent how much rays are we casting.

Inside the `RayTracerBasic.java` class we change the function `calcLocalEffects()` in order to calc for each `LightSource`
in the `Scene` the average value of the transparency parameter `ktr` by casting each `Ray` and calculating the value of the parameter using the `transparency()` function.

```java
class RayTracerBasic extends RayTracerBase {
   ...
   
   private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
      ...
      
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
         ...
   }
}
```

Then we use the value of `ktr` to calculate the intensity of the color the `LightSource` is emitting.


### Example of Soft-Shadow

---

<img alt="Before" height="400" src="./images/picture improvement/soft-shadow.png" /> <img alt="Before" height="400" src="./images/picture improvement/base-big-1.png" />

> You can see the difference of the shadow in the shadow of the `Cylinder`

---

## Depth Of Field

For the depth of field effect we need to create a beam of sampling `Ray` similarly to the antialiasing effect,
but instead of moving the center of each pixel we will move the starting `Point` of the `Ray`, aka the center `Point` of the `Camera`,
this will make two things:
1. will make the `Background` and the `Foreground` blurry.
2. will make the object in focus clearer.

here is a [link](https://pathtracing.home.blog/depth-of-field/) to a page that will explain it better than I could.

In order to move the starting `Point` of the `Ray` we will generate `Points` around the starting `Point` of the `Camera` `p0` using the function `generatePoint()`.

Then we will create the `Ray` that will start at a random `Point` around `p0` and the center of the pixel `pIJ`.

We add this functionality to the code by:
1. adding two more field to the `Camera` `lensRadius` and `focalRays`.
2. adding some code to the `constructRays()` function.

> Same as before, if `lensRaduis == 0` the depth of field effect is `off`,
> and also we have setter for both new field in order to make our code more customizable.

```java
class Camera {
   ...

   public List<Ray> constructRays(int nX, int nY, int j, int i) {
      // if both antialising and depth of field it turned off
      if (aliasRays == 1 && lensRadius == 0.0)
         return List.of(constructRay(nX, nY, j, i));
      
      // same as before we caclulate pIJ and also check if depth of field is off
      ...
      
      if(aliasRays == 1 && lensRadius > 0) {

         // Constructing (rays * rays) rays in random directions.
         List<Point> points = generatePoints(vRight, vUp, focalRays, p0, lensRadius);

         // Construct rays in random directions with depth of field effect
         for (int k = 0; k < focalRays && k < points.size(); k++) {
            Point p = points.get(k);
            rayBeam.add(
                    new Ray(p, pIJ.subtract(p))
            );
         }
      }
      return rayBeam;
   }
}
```

---

### Example of Depth Of Field

<img alt="Before" height="450" src="./images/picture improvement/base-2.png" /> <img alt="Before" height="450" src="./images/picture improvement/depth-of-field.png" />

> You can see clearly the difference between the two picture and the effect the `Depth Of Field` as on the `Background` and `Foregorund`.


----

## Glossy Surface

For the glossy surface effect instead of shouting one reflection `Ray`,
we need to generate a beam of `Ray` inside a cone and calculate the average color of the pixel.

In order to do that we will create two new field in the `Material.java` class:
1. `numRaysReflected` which represent how many `Ray` do we send each time we create a reflected `Ray`.
2. `coneAngleReflected` which represent the angle of the cone in which we generate the `Ray`.

> In order to make our code less `hard` we have setters for the field above, If `numRaysReflected == 0` the glossy surface is turned `off`.
> 
> Also for the `coneAngleReflected` field the setters with get a `angle` in degree and will convert it to radian.

Then we add a new function to the `RayTracerBasic.java` class that will generate a beam of `Ray` for reflection `constructReflectedRays()` 
that will return a `List<Ray>`.

```java
public class RayTracerBasic extends RayTracerBase {
    ...

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

        // checking if the glossy surface is turned on
        if (material.numRaysReflected == 1 || isZero(material.coneAngleReflected))
            return List.of(constructReflectedRay(gp, v, n));

        List<Ray> rays = new ArrayList<>();

        // Generate random direction vectors within the cone of the normal vector
        List<Vector> randomDirection = Vector.generateRandomDirectionInCone(gp,
                n, material.coneAngleReflected, material.numRaysReflected);

        // Construct rays using the random direction vectors and add them to the list
        for (int i = 0; i < randomDirection.size() && i < material.numRaysReflected; i++) {
            Vector u = randomDirection.get(i);
            Ray reflectedRay = new Ray(gp.point, u, n);
            rays.add(reflectedRay);
        }

        // adding the original reflection ray
        rays.add(constructRefractedRay(gp, v, n));

        return rays;
    }
} 
```

This is the code to generate random `Vector` inside a `Cone`. 

```java
/**
     * @param gp gp the GeoPoint at the surface of the geometry
     * @param n n the normal to the surface of the geometry at the point of gp.point
     * @param coneAngle coneAngle the angle of the cone in which the random rays will be generated (in radians)
     * @param amount the number of random vector to generate
     * @return list of random direction vector within the cone defined by the normal vector
     */
    public static List<Vector> generateRandomDirectionInCone(GeoPoint gp, Vector n, double coneAngle, int amount) {
        List<Vector> result = new LinkedList<>();

        double size = Math.tan(coneAngle) / 2;

        Plane plane = new Plane(gp.point, n);
        List<Vector> vectors = plane.findVectorsOfPlane();
        Vector v = vectors.get(0), u = vectors.get(1);

        List<Point> points = generatePoints(u, v, amount, gp.point.add(n), size);

        for (Point p: points) {
            result.add(
                    p.subtract(gp.point)
            );
        }

        return result;
    }

```

Then we add a new function `calcGlobalEffect()` that will receive a `List<Ray>` and calc the global effect of all the `Rays`.

```java
public RayTracerBasic extends RayTracerBase {
        ...

        **
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
            color = color.add(isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))
                                ? Color.BLACK : calcColor(gp, ray, level - 1, kkx).scale(kx));
            }
            return color.scale((double) 1 / rays.size());
        }
}
```

### Example of Glossy Surface

<img alt="Before" height="450" src="./images/picture improvement/img.png" />


---

## Blurry Glass

For the blurry glass effect instead of shouting one refraction `Ray`,
we need to generate a beam of `Ray` inside a cone and calculate the average color of the pixel.

n order to do that we will create two new field in the `Material.java` class:
1. `numRaysRefracted` which represent how many `Ray` do we send each time we create a refracted `Ray`.
2. `coneAngleRefracted` which represent the angle of the cone in which we generate the `Ray`.

> In order to make our code less `hard` we have setters for the field above, If `numRaysRefracted == 0` the blurry glass is turned `off`.
>
> Also for the `coneAngleRefracted` field the setters with get a `angle` in degree and will convert it to radian.

Then we add a new function to the `RayTracerBasic.java` class that will generate a beam of `Ray` for refracted `constructRefractedRays()`
that will return a `List<Ray>`.

```java
public RayTracerBasic extends RayTracerBase {
        ...

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
}
```

Then we can use the same `calcGlobalEffect()` function to calc the average color

## Example of Blurry Glass

<img alt="Before" height="400" src="./images/picture improvement/glass18.png" /> <img alt="Before" height="400" src="./images/picture improvement/glass14.png" />

# Report Mini Project Part 2

## Performance Improvement

1. Multi-Threading (with adaptive amount of thread)
2. BVH (Boundry volume hierarchy)

---

## Multi-Threading

We added multi-threading by using the `Pixel.java` class given to us, and adding a new field `multithreading` if `true` the mode is `On`.

here is the code added to the `Camera.java` class.

```java
public class Camera {
  ...
  public Camera renderImage() throws MissingResourceException {
    ...
    if(multithreading == true) {
      Pixel.initialize(nY, nX, printInterval);
      IntStream.range(0, nY).parallel().forEach(i -> {
        IntStream.range(0, nX).parallel().forEach(j -> {
          Color color = getAveragePixelColor(nX, nY, j, i);
          imageWriter.writePixel(j, i, color);
          Pixel.pixelDone();
          Pixel.printPixel();
        });
      });
    }
    ...
  }
}
```

---

## BVH

We added BVH by adding a new `Interface` `Bounable.java`

```java
public interface Boundable {

    /**
     * Creates a box around the object, adds the object to its list.
     *
     * @return The bounding box of the object
     */
    AxisAlignedBoundingBox getAxisAlignedBoundingBox();
}
```

and a new `class` `AxisAlignedBoundingBox` which implement the `Boundable` `Interface`

the class has value for `min` and `max` value for each of the axis (`x`, `y`, `z`)
and implement the function `getAxisAlignedBoundingBox()` which will return a `AxisAlignedBoundingBox` object which will 
tell us the boundary of the axis aligned box.

the class also  will create a tree, and will implement the `findGeoIntersectionsHelper()` function in `Intersectable`.

here is the code for that:

```java
@Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        //the ray's head and direction points
        Vector dir = ray.getDir();
        double xDir = dir.getX();
        double yDir = dir.getY();
        double zDir = dir.getZ();

        Point point = ray.getP0();
        double xPoint = point.getX();
        double yPoint = point.getY();
        double zPoint = point.getZ();
        double xMax, yMax, zMax, xMin, yMin, zMin;
        // if the vector's x coordinate is zero
        if (isZero(xDir)) {
            //if the point's x value is in the box,
            if (maxX >= xPoint && minX <= xPoint) {
                xMax = Double.MAX_VALUE;
                xMin = Double.MIN_VALUE;
            } else
                return null;
        }
        //if the vector's x coordinate is not zero, we need to check if we have values
        //where (MaxX - xPoint) / xDir > (MinX - xPoint) / xDir
        else {
            double t1 = (maxX - xPoint) / xDir;
            double t2 = (minX - xPoint) / xDir;
            xMin = Math.min(t1, t2);
            xMax = Math.max(t1, t2);
        }
        //if the vector's y coordinate is zero
        if (isZero(yDir)) {
            //if the point's y value is in the box,
            if (maxX >= yPoint && minY <= yPoint) {
                yMax = Double.MAX_VALUE;
                yMin = Double.MIN_VALUE;
            } else
                return null;
        }
        //if the vector's y coordinate is not zero, we need to check if we have values
        //where (MaxY - yPoint) / yDir > (MinY - yPoint) / yDir
        else {
            double t1 = (maxY - yPoint) / yDir;
            double t2 = (minY - yPoint) / yDir;
            yMin = Math.min(t1, t2);
            yMax = Math.max(t1, t2);
        }

        //if the vector's z coordinate is zero
        if (isZero(zDir)) {
            //if the point's z value is in the box,
            if (maxZ >= zPoint && minZ <= zPoint) {
                zMax = Double.MAX_VALUE;
                zMin = Double.MIN_VALUE;
            } else
                return null;
        }
        //if the vector's z coordinate is not zero, we need to check if we have values
        //where (MaxZ - zPoint) / zDir > (MinZ - zPoint) / zDir
        else {
            double t1 = (maxZ - zPoint) / zDir;
            double t2 = (minZ - zPoint) / zDir;
            zMin = Math.min(t1, t2);
            zMax = Math.max(t1, t2);
        }

        //check if such a point exists
        if (xMin > yMax || xMin > zMax || yMin > xMax || yMin > zMax || zMin > yMax || zMin > xMax)
            return null; // if not return null
            // if they do, return all the intersection points of the contents of the box
        else {
            List<GeoPoint> lst = new LinkedList<>();
            for (Boundable geo : contains) {
                List<GeoPoint> pointLst = ((Intersectable) geo).findGeoIntersections(ray, maxDistance);
                if (pointLst != null)
                    lst.addAll(pointLst);
            }
            return lst;
        }
    }
```

Then in the boundable geometries (`Sphere`, `Cylinder`, `Polygon`) will implement the `Bounable` Interface.

in the `Sphere.java` class:
```java
public class Sphere extends RadialGeometry implements Boundable {
  ...
  @Override
  public AxisAlignedBoundingBox getAxisAlignedBoundingBox() {
    double centerX = center.getX();
    double centerY = center.getY();
    double centerZ = center.getZ();
    AxisAlignedBoundingBox res = new AxisAlignedBoundingBox(
            centerX - radius,
            centerY - radius,
            centerZ - radius,
            centerX + radius,
            centerY + radius,
            centerZ + radius);
    res.addToContains(this);

    return res;
  }
}
```

in the `Cylinder.java` class:
```java
public class Cylinder extends Tube implements Boundable {
  ...
  @Override
  public AxisAlignedBoundingBox getAxisAlignedBoundingBox() {
    double minX, minY, minZ, maxX, maxY, maxZ;
    Point o1 = axisRay.getP0(); // middle of first end
    Point o2 = o1.add(axisRay.getDir().scale(length)); // middle of second end
    double o2X = o2.getX();
    double o1X = o1.getX();
    // middle point of side circles plus a radius offset is a good approximation for the bounding box
    if (o1X > o2X) {
      maxX = o1X + radius;
      minX = o2X - radius;
    } else {
      maxX = o2X + radius;
      minX = o1X - radius;
    }
    double o2Y = o2.getY();
    double o1Y = o1.getY();
    if (o1Y > o2Y) {
      maxY = o1Y + radius;
      minY = o2Y - radius;
    } else {
      maxY = o2Y + radius;
      minY = o1Y - radius;
    }
    double o2Z = o2.getZ();
    double o1Z = o1.getZ();
    if (o1Z > o2Z) {
      maxZ = o1Z + radius;
      minZ = o2Z - radius;
    } else {
      maxZ = o2Z + radius;
      minZ = o1Z - radius;
    }
    AxisAlignedBoundingBox res = new AxisAlignedBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    res.addToContains(this);

    return res;
  }
}
```

in the `Polygon.java` class:
```java
public class Polygon extends Geometry implements Boundable {
  @Override
  public AxisAlignedBoundingBox getAxisAlignedBoundingBox() {
    double minX, minY, minZ, maxX, maxY, maxZ;
    minX = maxX = vertices.get(0).getX();
    minY = maxY = vertices.get(0).getY();
    minZ = maxZ = vertices.get(0).getZ();
    //find the furthest coordinates
    for (int i = 1; i < vertices.size(); i++) {
      Point currentPoint = vertices.get(i);
      double currentX = currentPoint.getX();
      double currentY = currentPoint.getY();
      double currentZ = currentPoint.getZ();
      if (currentX > maxX)
        maxX = currentX;
      if (currentY > maxY)
        maxY = currentY;
      if (currentZ > maxZ)
        maxZ = currentZ;
      if (currentX < minX)
        minX = currentX;
      if (currentY < minY)
        minY = currentY;
      if (currentZ < minZ)
        minZ = currentZ;
    }
    AxisAlignedBoundingBox res = new AxisAlignedBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    res.addToContains(this);

    return res;
  }
}
```

Then in the `Geomtries.java` class we have a `static` field which will represent if `BVH` is `on` or `off`.
```java
    /**
     * If true, then the geometries class will use axis aligned bounding box in the calculations, and vice versa.
     */
    public static boolean axisAlignedBoundingBox = false;
```

in the ctor of the `Geomtries.java` class we add a check if the BVH is `on` and we add every `Bounable` geometry into the tree.
```java
public Geometries(Intersectable... geometries) {
        if (axisAlignedBoundingBox) {
            this.geometries = List.of(geometries);

            // create a list of all the geometries in the scene
            List<Intersectable> geos = new ArrayList<>(List.of(geometries));

            // a list of all the boundable geometries in the scene
            List<Boundable> boundables = new LinkedList<>();

            // move all the boundables from geos to boundables list
            for (Intersectable g : geometries) {
                if (g instanceof Boundable) {
                    geos.remove(g);
                    boundables.add((Boundable) g);
                }
            }

            // create an axis aligned bounding box tree for the boundable geometries and add the tree to the geometry list
            geos.add(AxisAlignedBoundingBox.createTree(boundables));
            this.geometries = geos;
        } else
            this.geometries = List.of(geometries);
    }
```

---

## Time Improvement


we ran a few tests on my computer `MacBook Air 2020` with `1.1 GHZ Dual-Core Intel Core i3` Processor:

### Test 1

testing a Chess Board `Scene` with 325+ `geometries` + 5 `LightSource` `500 x 500` Picture.

| Without any improvement | With all improvement |
|-------------------------|----------------------|
| 51.706 sec              | 30.633 sec           |

The picture:

<img alt="Before" height="400" src="./images/picture improvement/img_2.png" />

### Test 2

testing a Chess Board `Scene` with 325+ `geometries` + 5 `LightSource` `500 x 500` Picture + 32 `Rays` for `antialisasing`.

| Without any improvement | With all improvement |
|-------------------------|----------------------|
| 21 min 53 sec           | 15 min 2 sec         |


<img alt="Before" height="400" src="./images/picture improvement/img_1.png" />


---

## Creating the Chess Pieces

### Pawn

Here is the Pawn:

<img alt="Before" height="300" src="./images/picture improvement/pawn1.png" /> <img alt="Before" height="300" src="./images/picture improvement/pawn2.png" />

We created the pawn class which is just a Geometries

```java
 public Pawn(Point p, Vector dir, double height) {
        c1 = new Cylinder(height/4, new Ray(p, dir), height/10);
        c2 = new Cylinder(height/6, new Ray(p.add(dir.scale(height/10)), dir), height/10);
        c3 = new Cylinder(height/8, new Ray(p.add(dir.scale(height/5)), dir), height/2);
        c4 = new Cylinder(height/6, new Ray(p.add(dir.scale(7 * height/10)), dir), height/16);

        s1 = new Sphere(p.add(dir.scale((double) 61 / 80 * height)), height / 8);

        c5 = new Cylinder(height / 20, new Ray(p.add(dir.scale((double) 70 /80 * height)), dir), height / 18);

        geometries = new Geometries(c1, c2, c3, c4, c5, s1);

    }
```

### Rook

Here is the Rook:

<img alt="Before" height="300" src="./images/picture improvement/rook2.png" /> <img alt="Before" height="300" src="./images/picture improvement/rook1.png" />

We created the pawn class which is just a Geometries

```java
 public Rook(Point p, Vector dir, double height, Vector vX, Vector vY) {
        c1 = new Cylinder(height/4, new Ray(p, dir), height/10);
        c2 = new Cylinder(height/6, new Ray(p.add(dir.scale(height/10)), dir), height/10);
        c3 = new Cylinder(height/8, new Ray(p.add(dir.scale(height/5)), dir), height/2);
        c4 = new Cylinder(height/6, new Ray(p.add(dir.scale(7 * height/10)), dir), height/16);
        c5 = new Cylinder(height / 6, new Ray(p.add(dir.scale((double) 61 /80 * height)), dir), height / 16);

        double h1 = (double) 61 / 80 * height;
        double h2 = (double) 1 / 10 * height;

        double epsilon = (double) 1 / 8 * height;

        Point p0 = p.add(dir.scale(h1));
        p0 = p0.add(vX.scale(-epsilon / 2)).add(vY.scale(-epsilon / 2));

        Vector v1 = vX.scale(epsilon).add(vY.scale(0.5));
        Vector v2 = vY.scale(epsilon).add(vX.scale(0.5));

        t1 = new Cube(vX.scale(1.3), vY, dir.scale(2.2), p0.add(v1), h2);
        t2 = new Cube(vX.scale(1.3), vY, dir.scale(2.2), p0.add(v1.scale(-1)), h2);
        t3 = new Cube(vY.scale(1.3), vX, dir.scale(2.2), p0.add(v2), h2);
        t4 = new Cube(vY.scale(1.3), vX, dir.scale(2.2), p0.add(v2.scale(-1)), h2);

        geometries = new Geometries(c1, c2, c3, c4, c5, t1.getGeometries(), t2.getGeometries(), t3.getGeometries(), t4.getGeometries());
        }
```

### Queen

Here is the Queen:

<img alt="Before" height="300" src="./images/picture improvement/queen1.png" /> <img alt="Before" height="300" src="./images/picture improvement/queen2.png" />

We created the pawn class which is just a Geometries

```java
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
```
