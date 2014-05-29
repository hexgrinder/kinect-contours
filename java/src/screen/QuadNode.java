package hexgrinder.screen;

import java.util.ArrayList;

import hexgrinder.search.AxisAlignedBoundingBox;
import hexgrinder.search.Coordinate;
import hexgrinder.search.QuadTreeNodeType;

public class QuadNode {

	/**
	 * QuadTreeNode constructor.
	 * @param capacity Sets the capacity for each node
	 * @param maxRecursionLevel Set the max number of region divisions
	 */
	public QuadNode(Coordinate upperLeft, Coordinate lowerRight) {
		this(null,upperLeft,lowerRight);
	}

	public QuadNode(QuadTreeNodeType type, Coordinate upperLeft, Coordinate lowerRight) {
		this.boundary = new AxisAlignedBoundingBox(upperLeft, lowerRight);
		this.isLeaf = true;
		this.isEmpty = true;
		this.Type = type;
		this.quads = new ArrayList<ResolutionMapBoundingBox>();
	}
	
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
	public boolean isLeaf;
	public boolean isEmpty;
	public QuadTreeNodeType Type;

	public final ArrayList<ResolutionMapBoundingBox> quads;
}
