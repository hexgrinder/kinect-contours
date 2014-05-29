/**
 * 
 */
package hexgrinder.search;

/**
 * @author hexgrinder
 *
 */
public class AxisAlignedBoundingBox {
	
	private Coordinate _upperLeft;
	private Coordinate _lowerRight;
	private Coordinate _center;
	
	/**
	 * Default AABB constructor
	 */
	public AxisAlignedBoundingBox() {}
	
	/**
	 * AABB constructor
	 * 
	 * @param upperLeft Upper-left 2D coordinate of the bounding box
	 * @param lowerRight Lower-right 2D coordinate of the bounding box
	 */
	public AxisAlignedBoundingBox(Coordinate upperLeft, Coordinate lowerRight) {
		this._upperLeft = upperLeft;
		this._lowerRight = lowerRight;
		this._center = new Coordinate(
			(lowerRight.getX() - upperLeft.getX()) >> 1,
			(lowerRight.getY() - upperLeft.getY()) >> 1);
	}
	
	/**
	 * TRUE if the point is within the bounding box, FALSE otherwise.
	 * 
	 * @param point Coordinate in question
	 * @return Returns TRUE if the point is within the bounding box, FALSE otherwise.
	 */
	public boolean isInBoundary(Coordinate point) {
		return this.isInBoundary(point.getX(), point.getY());
	}
	
	/**
	 * TRUE if the point is within the bounding box, FALSE otherwise.
	 *
	 * @param pX x-coordinate 
	 * @param pY y-coordinate
	 * @return Returns TRUE if the point is within the bounding box, FALSE otherwise.
	 */
	public boolean isInBoundary(int pX, int pY) {
		return (
			pX >= this._upperLeft.getX()
			&& pY >= this._upperLeft.getY()
			&& pX <= this._lowerRight.getX()
			&& pY <= this._lowerRight.getY()
		);
	}
	
	public boolean intersectsBox(AxisAlignedBoundingBox box) {
		// TODO: Collision tracking
		return false;
	}
	
	/**
	 * Gets the upper left coordinate of the bounding box
	 * 
	 * @return Returns the upper-left coordinate of the bounding box
	 */
	public Coordinate getUpperLeft() {
		return _upperLeft;
	}
	
	/**
	 * Gets the lower right coordinate of the bounding box
	 * 
	 * @return Returns the lower-right coordinate of the bounding box
	 */
	public Coordinate getLowerRight() {
		return _lowerRight;
	}
	
	/**
	 * Gets the boundary center.
	 * 
	 * @return Returns an array of 2 ints, [x-coordinate y-coordinate], 
	 * defining the boundary center.
	 */
	public int[] getCenter() {
		return new int[] {_center.getX(), _center.getY()};
	}
	
	
} // AxisAlignedBounding
