package hexgrinder.screen;

import hexgrinder.search.Coordinate;
import hexgrinder.search.QuadTreeNodeType;

import java.util.ArrayList;

public class ResolutionMapBoundingBoxCollection {

	private ArrayList<QuadNode> _nodes = new ArrayList<QuadNode>();
	
	public ResolutionMapBoundingBoxCollection(int resWidth, int resHeight, int levels) {
		
		// add root
		QuadNode root = new QuadNode(
			new Coordinate(0,0),
			new Coordinate(resWidth,resHeight));
		
		_nodes.add(root);
		
		_subDivide(root, levels);
	}
	
	public boolean add(ResolutionMapBoundingBox box) {
		
		// find the correct bounding box
		return false;
	}
	
	private QuadNode _searchIndex(QuadNode node, int origX, int origY) {
		
		// not bounded -> kick out
		if (node.boundary.isInBoundary(origX, origY)) {
			return null;
		}
		
		// TODO??: USE STACK IMPLEMENTATION RATHER THAN SIMPLE ARRAY??
		// FEELS LIKE A BOUNDED DEPTH FIRST SEARCH / TRAVERSAL SHOULD DO IT
		// BOUNDED FUNCTION [T|F] : Is point contained within Quad?
		//		TRUE: CONTINUE DOWN THE PATH
		//		FALSE: STOP. LOOK AT NEIGHBORING PATH
		// AT BOTTOM OF PATH:
		//		>> ASSUMPTION: BOTTOM OF PATH HAS THE BOX YOU NEED
		//		LOOK AT THE BOXES TO FIND THE INDEX
		//		RETURN THE INDEX
		
		// IF INTERIOR NODE, KEEP SEARCHING
		// IF AT BOTTOM OF TREE, SEARCH NODE'S BOUNDING BOXES
		// IF BOUNDING BOX CONTAINS POINT, RETURN THE BOX'S SRC INDEX, ELSE RETURN -1
		
		// TODO:  HOW TO KNOW WHEN TO SEE KIDS?
		
		return null;
	}
	
	public int findIndex(int x, int y) {
		return 0;
	}
	
	private void _subDivide(QuadNode parent, int level) {
		
		if (level <= 0) { return; }
		
		// calc boundaries
		int origin_X = parent.boundary.getUpperLeft().getX();
		int origin_Y = parent.boundary.getUpperLeft().getY();
		int bound_X = parent.boundary.getLowerRight().getX();
		int bound_Y = parent.boundary.getLowerRight().getY();
		int half_X = origin_X + ((bound_X - origin_X) >> 1);	// divide by 2
		int half_Y = origin_Y + ((bound_Y - origin_Y) >> 1);  	// divide by 2
		
		// create the 4 children -> split the current bounds into EQUAL fourths
		
		QuadNode child;
			
		// *** NW
		child = _addNewChild(
			parent,
			QuadTreeNodeType.NW_NODE,
			new Coordinate(origin_X, origin_Y),
			new Coordinate(half_X, half_Y),
			level - 1);

		_subDivide(child, level - 1);
		
		// *** NE
		child = _addNewChild(
			parent,
			QuadTreeNodeType.NE_NODE,
			new Coordinate(half_X+1, origin_Y),
			new Coordinate(bound_X, half_Y),
			level - 1);
		
		_subDivide(child, level - 1);
		
		// *** SE
		child = _addNewChild(
			parent,
			QuadTreeNodeType.SE_NODE,
			new Coordinate(half_X+1, half_Y+1),
			new Coordinate(bound_X, bound_Y),
			level - 1);
		
		_subDivide(child, level - 1);
		
		// *** SW
		child = _addNewChild(
			parent,
			QuadTreeNodeType.SW_NODE,
			new Coordinate(origin_X, half_Y+1),
			new Coordinate(half_X, bound_Y),
			level - 1);
		
		_subDivide(child, level - 1);
	}
	
	/**
	 * Adds a new child to the array and maps the children's indexes
	 * to the parents children property.
	 *  
	 * @param parent Parent node
	 * @param qtnode QuadTree.Node type
	 * @param upperLeft Upper-left boundary of the new child
	 * @param lowerRight Lower-right boundary of the new child
	 * @param level Indicates child's tree level
	 * @return Returns the newly created child
	 */
	private QuadNode _addNewChild(QuadNode parent, QuadTreeNodeType qtnode, Coordinate upperLeft, Coordinate lowerRight, int level) {
		QuadNode newElem = null;
		newElem = new QuadNode(
			upperLeft,
			lowerRight);
		_nodes.add(newElem);
		parent.children[qtnode.ordinal()] = _nodes.lastIndexOf(newElem);
		return newElem;
	}

} // ResolutionMapBoundingBoxCollection
