/**
 * 
 */
package hexgrinder.search;

import java.util.*;

/**
 * @author hexgrinder
 *
 */
@SuppressWarnings("serial")
public class QuadTreeNode extends ArrayList<Coordinate> {
	
	/**
	 * Contains indexes to child nodes.  
	 * Children are index-specific:
	 * 		[Index | Child]
	 * 		[0 | NW], 
	 * 		[1 | NE],
	 * 		[2 | SE],
	 * 		[3 | SW]
	 */
	public int[] children = {-1,-1,-1,-1};
	public AxisAlignedBoundingBox boundary;
	public int elemCount;
	public boolean isLeaf;
	public boolean isEmpty;
	public QuadTreeNodeType Type = null;
	
	/**
	 * Indicates node's assigned tree level
	 */
	public int level = -1;
	
	/**
	 * QuadTreeNode constructor.
	 * @param capacity Sets the capacity for each node
	 * @param maxRecursionLevel Set the max number of region divisions
	 */
	public QuadTreeNode(Coordinate upperLeft, Coordinate lowerRight) {
		this(null,upperLeft,lowerRight);
	}

	public QuadTreeNode(QuadTreeNodeType type, Coordinate upperLeft, Coordinate lowerRight) {
		this.boundary = new AxisAlignedBoundingBox(upperLeft, lowerRight);
		this.elemCount = 0;
		this.isLeaf = true;
		this.isEmpty = true;
		this.Type = type;
	}

	/**
	 * TRUE if the object has children, FALSE otherwise.
	 * 
	 * @return Returns TRUE if the object has children, FALSE otherwise.
	 */
	public boolean hasChildren() {
		return (this.children[0] != -1);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("******\n");
		sb.append("Upper Left: " + boundary.getUpperLeft() + "\n");
		sb.append("Lower Right: " + boundary.getLowerRight() + "\n");
		sb.append("Elem Count: " + this.elemCount + "\n");
		sb.append("Level: " + this.level + "\n");
		if (this.Type != null) {
			sb.append("Type: " + this.Type.name() + "\n");
		}
		sb.append("Children: ");
		for (int i = 0, len = this.children.length; i < len; i++) {
			sb.append(this.children[i] +", ");
		}
		sb.append("\n");
		sb.append("Contained points: ");
		for (Coordinate pt : this) {
			sb.append(pt +", ");
		}
		sb.append("\n");
		return sb.toString();
	}
	
} // QuadTreeNode
