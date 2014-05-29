/**
 * 
 */
package hexgrinder.search;

import java.util.Iterator;

/*

/**
 * @author hexgrinder
 *
 */
public class QuadTree implements Iterable<QuadTreeNode> {

	private QuadTreeNodeCollection _nodes;
	
	/**
	 * QuadTree constructor.
	 * 
	 * Initializes the tree.  The tree will be initialized with a root as
	 * defined by the upper-left and lower-right bounding box parameters.
	 * 
	 * @param upperLeft Upper-left bounds of the root node
	 * @param lowerRight Lower-right bounds of the root node
	 * @param maxNodeCapacity Maximum capacity of each node
	 */
	public QuadTree(Coordinate upperLeft, Coordinate lowerRight, int maxNodeCapacity) {
		_nodes = new QuadTreeNodeCollection(upperLeft, lowerRight, maxNodeCapacity);
	}

	/**
	 * Inserts a coordinate into the tree.
	 *
	 * @param point Coordinate to insert.
	 * @param maxLevel Max depth to insert at.
	 * @return
	 */
	public void insert(Coordinate point, int maxLevel) {
		_nodes.insert(point, maxLevel);
	}
	
	// TODO: Implement
	public Integer[] getNearestNeighbors(Coordinate point, int blockRadius) {
		return null;
	}
	
	// TODO?: SOME SORT OF REPEATABLE STATIC METHOD?
	// REVEAL ITERATOR
	public Integer[] getBoxes() {
		return _nodes.getBoxes();
	}
	
	/*
	// TODO?: SOME SORT OF REPEATABLE STATIC METHOD?
	// REVEAL ITERATOR
	public Integer[] getCenters() {
		return _nodes.getCenters();
	}
	*/
	
	public void setShowAllQuads(boolean state) {
		this._nodes.showAllQuads = state;
	}
	
	public int size() {
		return _nodes.size();
	}
	
	/**
	 * Clears the tree of all nodes.
	 */
	public void clear() {
		_nodes.clear();
	}

	@Override
	public Iterator<QuadTreeNode> iterator() {
		return this._nodes.iterator();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, len = _nodes.size(); i < len; i++) {
			sb.append("***** " + i + "\n");
			sb.append(_nodes.get(i).toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	 
} // QuadTree
