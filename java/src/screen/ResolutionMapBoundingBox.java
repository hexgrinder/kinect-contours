package hexgrinder.screen;

import hexgrinder.search.AxisAlignedBoundingBox;
import hexgrinder.search.Coordinate;

public class ResolutionMapBoundingBox extends AxisAlignedBoundingBox {

	public ResolutionMapBoundingBox() {
		super();
		this.sourceIndex = 0;
	}
	
	public ResolutionMapBoundingBox(Coordinate upperLeft, Coordinate lwrRight, int sourceIndex) {
		super(upperLeft, lwrRight);
		this.sourceIndex = sourceIndex;
	}
	
	public final int sourceIndex;
}
