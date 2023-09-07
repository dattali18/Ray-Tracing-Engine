package geometries;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/** Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan
 * */
public class Polygon extends Geometry implements Boundable {
   /** List of polygon's vertices */
   protected final List<Point> vertices;
   /** Associated plane in which the polygon lays */
   protected final Plane       plane;
   private final int           size;

   /** Polygon constructor based on vertices list. The list must be ordered by edge
    * path. The polygon must be convex.
    * @param  vertices                 list of vertices according to their order by
    *                                  edge path
    * @throws IllegalArgumentException in any case of illegal combination of
    *                                  vertices:
    *                                  <ul>
    *                                  <li>Less than 3 vertices</li>
    *                                  <li>Consequent vertices are in the same
    *                                  point
    *                                  <li>The vertices are not in the same
    *                                  plane</li>
    *                                  <li>The order of vertices is not according
    *                                  to edge path</li>
    *                                  <li>Three consequent vertices lay in the
    *                                  same line (180&#176; angle between two
    *                                  consequent edges)
    *                                  <li>The polygon is concave (not convex)</li>
    *                                  </ul>
    */
   public Polygon(Point... vertices) throws IllegalArgumentException {
      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
      this.vertices = List.of(vertices);
      size          = vertices.length;

      // Generate the plane according to the first three vertices and associate the
      // polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon
      plane         = new Plane(vertices[0], vertices[1], vertices[2]);
      if (size == 3) return; // no need for more tests for a Triangle

      Vector  n        = plane.getNormal();
      // Subtracting any subsequent points will throw an IllegalArgumentException
      // because of Zero Vector if they are in the same point
      Vector  edge1    = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector  edge2    = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException
      // because of Zero Vector if they connect three vertices that lay in the same
      // line.
      // Generate the direction of the polygon according to the angle between last and
      // first edge being less than 180 deg. It is hold by the sign of its dot product
      // with
      // the normal. If all the rest consequent edges will generate the same sign -
      // the
      // polygon is convex ("kamur" in Hebrew).
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
         // Test the consequent edges have
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);
         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
      }

   }

   @Override
   public Vector getNormal(Point point) { return plane.getNormal(point); }


   /**
    * @return List of Point, the list of all our vertices
    */
   public List<Point> getVertices() {
      // this will be useful in stage3 when we will calculate the intersections
      return this.vertices;
   }

   @Override
   protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
      // since we first find if the is intersection with the plane containing the triangle
      List<GeoPoint> intersectionPoints = this.plane.findGeoIntersections(ray, maxDistance);

      // if intersectionPoints == null there is no intersection
      if(intersectionPoints == null)
         return null;

      Point p0 = intersectionPoints.get(0).point;

      // we need to check for if the point is one of the triangle Point
      for (Point point : this.vertices) {
         if(p0.equals(point))
            return null;
      }

      List<Vector> vectors = new ArrayList<>();
      for (Point point : this.vertices) {
         vectors.add(point.subtract(p0));
      }
      // the try catch is in case of a zero vector
      try {
         List<Vector> normals = new ArrayList<>();
         for (int i = 0; i < size - 1; i++) {
            Vector vi = vectors.get(i);
            Vector vj = vectors.get(i + 1);

            normals.add(vi.crossProduct(vj).normalize());
         }
         normals.add(vectors.get(size - 1).crossProduct(vectors.get(0).normalize()));

         List<Double> t = new ArrayList<>();
         for (int i = 0; i < size; i++) {
            Vector ni = normals.get(i);
            Double ti = alignZero(ray.getDir().dotProduct(ni));
            t.add(ti);
         }

         if(t.stream().allMatch(d -> d > 0) || t.stream().allMatch(d -> d < 0))
            return intersectionPoints;
      } catch (IllegalArgumentException e) {
         return null;
      }

      return null;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Polygon polygon)) return false;
      if (!super.equals(o)) return false;
      return size == polygon.size && Objects.equals(getVertices(), polygon.getVertices()) && Objects.equals(plane, polygon.plane);
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), getVertices(), plane, size);
   }

   @Override
   public String toString() {
      return "Polygon{" +
              "vertices=" + vertices +
              ", plane=" + plane +
              ", size=" + size +
              ", emission=" + emission +
              "} ";
   }

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
