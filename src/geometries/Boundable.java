package geometries;

/**
 * An interface to take care of creating Bounding Boxes
 *
 * @author Dattali , Itsick
 */
public interface Boundable {

    /**
     * Creates a box around the object, adds the object to its list.
     *
     * @return The bounding box of the object
     */
    AxisAlignedBoundingBox getAxisAlignedBoundingBox();
}
