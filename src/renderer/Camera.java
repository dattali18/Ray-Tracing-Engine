package renderer;

import java.util.*;
import java.util.stream.IntStream;

import primitives.*;
import primitives.Vector;

import static primitives.Pixel.*;
import static primitives.Point.generatePoints;
import static primitives.Util.*;


/**
 * the camera class is representing the camera in a 3D space
 * <br>
 * contains:
 * <ul>
 *     <li>the starting point {@link Camera#p0}</li>
 *     <li>the up vector {@link Camera#vUp}</li>
 *     <li>the to vector {@link Camera#vTo}</li>
 *     <li>the right vector {@link Camera#vRight}</li>
 *     <li>a double value of {@link Camera#width} </li>
 *     <li>a double value of {@link Camera#height}</li>
 *     <li>a double value of {@link Camera#distance}</li>
 *     <li>a Image Writer obj of {@link ImageWriter} used to snap the image using the camera</li>
 *     <li>a Ray Tracer obj that extends the {@link RayTracerBase} and is used to trace all their ray created by the camera using {@link Camera#constructRay(int, int, int, int)}</li>
 * </ul>
 *
 * @author danielattali, itiskvales
 */
public class Camera {
    /**
     * starting point of the camera
     */
    private Point p0;
    /**
     * the up direction of our picture
     */
    private Vector vUp;
    /**
     * the direction the camera is pointing to
     */
    private Vector vTo;

    /**
     * Image Writer to write the image the camera see to a *.jpg file
     */
    private ImageWriter imageWriter;

    /**
     * Ray Tracer to trace the ray created by the camera
     */
    private RayTracerBase rayTracer;

    /**
     * the right direction
     */
    private Vector vRight;
    /**
     * the width of the picture
     */
    private double width;

    /**
     * the height of the picture
     */
    private double height;
    /**
     * the distance from the camera (p0) to the view plane (imaginary construct)
     */
    private double distance;

    /**
     * The amount of rays that will be shot in each row and column,
     * in all picture improvements.
     * (set 1 to `turn off` the action)
     */
    private int aliasRays = 1;

    /**
     * the radius of the lens for the depth of field effect
     * if the value is = 0 the effect is turned 'off'
     */
    private double lensRadius = 0;

    /**
     * the amount of rays that will be shot for each pixel
     */
    private int focalRays = 1;

    /**
     * a bool to determine if we are in multi threading mode or not
     */
    private boolean multithreading = false;

    // ========================== Ctor ===============================

    /**
     * a ctor for the camera
     * <br>
     * <font color='red'><u><b>Attention</b></u> the vUp and vTo vector must be orthogonal to each other.</font>
     * @param p0 the place of the camera in 3D space
     * @param vUp the up direction of the camera
     * @param vTo the direction the camera is pointing to
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if(!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("the vUp and vTo vector are not orthogonal one to another.");

        this.p0 = p0;

        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = this.vTo.crossProduct(this.vUp).normalize();
    }

    // ========================= Getters / Setter (builder pattern) ==========================

    /**
     * @return the place of the camera
     */
    public Point getP0() {
        return p0;
    }

    /**
     * @return the up direction of the camera
     */
    public Vector getVectorUp() {
        return vUp;
    }

    /**
     * @return the direction the camera is looking at
     */
    public Vector getVectorTo() {
        return vTo;
    }

    /**
     * @return the direction of right of the camera
     */
    public Vector getVectorRight() {
        return vRight;
    }

    /**
     * @return the width of the view plane
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the height of the view plane
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the distance of the view plane from the camera
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @return the imageWriter
     */
    public ImageWriter getImageWriter() {
        return imageWriter;
    }

    /**
     * @return the  ray tracer ending
     */
    public RayTracerBase getRayTracer() {
        return rayTracer;
    }


    /**
     * a setter for the View Plane argument
     * @param width the width of the View Plane (> 0)
     * @param height the height of the View Plane (> 0)
     * @return the camera object according to the builder pattern
     */
    public Camera setVPSize(double width, double height) {
        double temp1 = alignZero(width);
        double temp2 = alignZero(height);
        if(temp1 <= 0 || temp2 <= 0)
            throw new IllegalArgumentException("the width and height must be greater than 0.");

        this.width = width;
        this.height = height;

        return this;
    }

    /** a setter for the View Plane argument
     * @param distance  the distance from the camera to the View Plane ( >0)
     * @return the camera object according to the builder pattern
     */
    public Camera setVPDistance(double distance){
        double temp = alignZero(distance);
        if(temp <= 0)
            throw new IllegalArgumentException("the distance must be greater than 0.");

        this.distance = distance;

        return this;
    }

    /** a setter for the Image Writer
     * @param imageWriter the image writer obj
     * @return the camera object according to the builder pattern
     */
    public Camera setImageWriter(ImageWriter imageWriter){
        this.imageWriter = imageWriter;
        return this;
    }

    /** a setter for the Ray Tracer
     * @param rayTracer the ray tracer
     * @return the camera object according to the builder pattern
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * @param aliasRays the number of aliasing rays (set to 1 to 'turn of' the antialiasing)
     * @return the camera object according to the builder pattern
     */
    public Camera setAliasRays(int aliasRays) {
        if (aliasRays < 1)
            throw new IllegalArgumentException("The number of rays must be greater then 0!");
        this.aliasRays = aliasRays;
        return this;
    }

    /**
     * @param lensRadius the radius of the lens set to 0 to turn off the depth of field effect
     * @return ths camera object according to the builder pattern setters
     */
    public Camera setLensRadius(double lensRadius) {
        if(lensRadius < 0) {
            throw new IllegalArgumentException("The radius have to be greater than zero");
        }
        this.lensRadius = lensRadius;
        return this;
    }

    /**
     * @param focalRays the number of focal rays (set to 1 to 'turn of' the antialiasing)
     * @return the camera object according to the builder pattern
     */
    public Camera setFocalRays(int focalRays) {
        if (focalRays < 1)
            throw new IllegalArgumentException("The number of rays must be greater then 0!");
        this.focalRays = focalRays;
        return this;
    }

    /**
     * @param multiThreading true or false (on or off)
     * @return the camera object according to the builder pattern
     */
    public Camera setMultiThreading(boolean multiThreading) {
        this.multithreading = multiThreading;
        return this;
    }

    // ============================ Camera Methods ===================================

    /**
     * construct a ray from the camera to the <u>center</u> of the pixel[i,j]
     * @param nX the line width
     * @param nY the column height
     * @param j the column of the pixel
     * @param i the line of the pixel
     * @return the ray from the camera to the <u>center</u> of the pixel[i,j]
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // using the mathematical model given to us in the theoretical course

        Point pCenter = p0.add(this.vTo.scale(this.distance));

        double rY = this.height / nY;
        double rX = this.width  / nX;

        double yI = alignZero( -(i - (double) (nY - 1) / 2) * rY);
        double xJ = alignZero(  (j - (double) (nX - 1) / 2) * rX);

        Point pIJ = pCenter;

        // checking xJ != 0 and yI != 0 because if we scale by 0 we get a 0 vector which will raise an Exception
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));

//        System.out.println(pIJ);
        Vector vIJ = pIJ.subtract(p0);

        return new Ray(p0, vIJ);
    }

    /**
     * @param nX the line width
     * @param nY the column height
     * @param i the column of the pixel
     * @param j the line of the pixel
     * @return the rays from the camera to the of the pixel[i,j] + a random factor according to the Jittered Pattern
     */
    public List<Ray> constructRays(int nX, int nY, int j, int i) {
        // if both antialiasing and depth of field it turned off
        if (aliasRays == 1 && lensRadius == 0.0)
            return List.of(constructRay(nX, nY, j, i));

        List<Ray> rayBeam = new LinkedList<>();
        // Choosing the biggest scalar to scale the vectors.
        Point pCenter = p0.add(this.vTo.scale(this.distance));

        double rY = this.height / nY;
        double rX = this.width  / nX;

        double size = Math.min(rY, rX);

        double yI = alignZero( -(i - (double) (nY - 1) / 2) * rY);
        double xJ = alignZero(  (j - (double) (nX - 1) / 2) * rX);

        Point pIJ = pCenter;

        // checking xJ != 0 and yI != 0 because if we scale by 0 we get a 0 vector which will raise an Exception
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));

        if(aliasRays > 1 && lensRadius == 0.0) {

            // Constructing (rays * rays) rays in random directions.
            List<Point> points = generatePoints(vRight, vUp, aliasRays, pIJ, size);

            for (int k = 0; k < points.size() && k < aliasRays; k++) {
                rayBeam.add(
                        new Ray(p0, points.get(k).subtract(p0))
                );
            }

        }
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





    /**
     * will render an image using all the data above
     * @throws MissingResourceException if one of the field is null we will throw the exception above
     * @return this according to the Builder patter
     */
    public Camera renderImage() throws MissingResourceException {
        // we first make sure all the necessary field aren't null
        if(
                (this.imageWriter == null)  ||
                (this.rayTracer == null)

        ) {
            // for now the exception is Missing Resource but in the future we need a better Exception
            throw new MissingResourceException("image write of ray tracer", "camera", "image writer or ray tracer");
        }

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        if(!multithreading) {

            // for each pixel in our image we loop over and cast a ray through the center of the pixel,
            // and we calculate the color at the pixel and color it accordingly
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {
                    Color color = getAveragePixelColor(nX, nY, i, j);

                    imageWriter.writePixel(i, j, color);
                }
            }
        } else {
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


        return this;
    }

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

    /**
     * cast the ray and find the color of object at the intersection
     * @param nx the line width
     * @param ny the column height
     * @param j the column of the pixel
     * @param i the line of the pixel
     * @return the color at the intersection or the background color if the isn't one
     */
    private Color castRay(int nx, int ny, int i, int j) {
        /* construct the ray at the center of the pixel[i, j] and we trace it using our ray tracer object */
        Ray ray = this.constructRay(nx, ny, i, j);
        return this.rayTracer.traceRay(ray);
    }

    /**
     * will print a grid onto the image with a certain interval
     * @param interval the interval of the grid
     * @param color the color of the grid
     * @return this according to the Builder Pattern
     */
    public Camera printGrid(int interval, Color color) {
        if(this.imageWriter == null)
            throw new MissingResourceException("from", "to", "else");

        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if(i % interval == 0 || j % interval == 0)
                {
                    imageWriter.writePixel(i, j ,color);
                }
            }
        }
        return this;
    }

    /**
     * will write the image to the file
     */
    public Camera writeToImage() {
        // we check first that the image writer is not null, and then we call the write to image function to write the image to the file
        if(this.imageWriter == null)
            throw new MissingResourceException("from", "to", "else");

        imageWriter.writeToImage();
        return this;
    }


    @Override
    public String toString() {
        return "Camera{" +
                "p0=" + p0 +
                ", vUp=" + vUp +
                ", vTo=" + vTo +
                ", vRight=" + vRight +
                ", width=" + width +
                ", height=" + height +
                ", distance=" + distance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Camera camera)) return false;
        return Double.compare(camera.getWidth(), getWidth()) == 0 && Double.compare(camera.getHeight(), getHeight()) == 0 && Double.compare(camera.getDistance(), getDistance()) == 0 && Objects.equals(getP0(), camera.getP0()) && Objects.equals(getVectorUp(), camera.getVectorUp()) && Objects.equals(getVectorTo(), camera.getVectorTo()) && Objects.equals(getVectorRight(), camera.getVectorRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getP0(), getVectorUp(), getVectorTo(), getVectorRight(), getWidth(), getHeight(), getDistance());
    }


    //===================== Camera Movement ==============================
    /**
     * @param amount the amount to move up (if amount is negative we move down)
     * @return this according to the Builder Pattern
     */
    public Camera moveUp(double amount) {
        if(isZero(amount))
            return this;

        p0 = p0.add(vUp.scale(amount));
        return this;
    }

    /**
     * @param amount the amount to move right (if amount is negative we move left)
     * @return this according to the Builder Pattern
     */
    public Camera moveRight(double amount) {
        if(isZero(amount))
            return this;

        p0 = p0.add(vRight.scale(amount));
        return this;
    }

    /**
     * @param amount the amount to move forward (if amount is negative we move backward)
     * @return this according to the Builder Pattern
     */
    public Camera moveForward(double amount) {
        if(isZero(amount))
            return this;

        p0 = p0.add(vTo.scale(amount));
        return this;
    }

    //===================== Rotation Bonus ==============================

    /**
     * rotate() rotate the camara by alpha, beta, gama degrees
     * @param alpha the angle in degree (0 to 360) of the rotation in the x-axis
     * @param beta the angle in degree (0 to 360) of the rotation in the y-axis
     * @param gama the angle in degree (0 to 360) of the rotation in the z-axis
     * @return the camera after the rotation
     */
    public Camera rotate(double alpha, double beta, double gama) {

        this.vUp = this.vUp.rotate(alpha, 'x').rotate(beta, 'y').rotate(gama, 'z');
        this.vTo = this.vTo.rotate(alpha, 'x').rotate(beta, 'y').rotate(gama, 'z');
        this.vRight = this.vTo.crossProduct(this.vUp);

        return this;
    }

    /**
     * yaw is the rotation of the camera left/right around the vUp vector
     * @param angle the angle in degree (0 to 360)
     * @return the camera after the rotation
     */
    public Camera yawCamera(double angle) {
        if(isZero(angle))
            return this;
        double radian = Math.toRadians(angle);

        double cos = Math.cos(radian);
        double sin = Math.sin(radian);

        Vector newVTo = vTo.scale(cos).add(vRight.scale(sin));
        Vector newVRight = vRight.scale(cos).subtract(vTo.scale(sin));

        vTo = newVTo;
        vRight = newVRight;

        return this;
    }


    /**
     * roll is the rotation of the camera around the vTo vector
     * @param angle the angle in degree (0 to 360)
     * @return the camera after the rotation
     */
    public Camera rollCamera(double angle) {
        if(isZero(angle))
            return this;
        double radian = Math.toRadians(angle);

        double cos = Math.cos(radian);
        double sin = Math.sin(radian);

        Vector newVUp = vUp.scale(cos).subtract(vRight.scale(sin));
        Vector newVRight = vRight.scale(cos).add(vUp.scale(sin));

        vUp = newVUp;
        vRight = newVRight;

        return this;
    }

    /**
     * pitch is the rotation of the camera up/down around the vRight vector
     * @param angle the angle in degree (0 to 360)
     * @return the camera after the rotation
     */
    public Camera pitchCamera(double angle) {
        if(isZero(angle))
            return this;
        double radian = Math.toRadians(angle);

        double cos = Math.cos(radian);
        double sin = Math.sin(radian);

        Vector newVTo = vTo.scale(cos).subtract(vUp.scale(sin));
        Vector newVUp = vUp.scale(cos).add(vTo.scale(sin));

        vTo = newVTo;
        vUp = newVUp;

        return this;
    }
}
