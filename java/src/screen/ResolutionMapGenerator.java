package hexgrinder.screen;

import java.util.ArrayList;
import hexgrinder.search.*;

public class ResolutionMapGenerator {

	public static int[] create(int originalResWidth, int originalResHeight, int newResWidth, int newResHeight) {
		
		ResolutionMapBoundingBox[] boxes = _buildScreenBins(
				originalResWidth, originalResHeight, 
				newResWidth, newResHeight);			
		
		return _buildResolutionMap(
			boxes,
			newResWidth, 
			newResHeight);
	}
	
	private static ResolutionMapBoundingBox[] _buildScreenBins(int originalResWidth, int originalResHeight, int newResWidth, int newResHeight) {
	
		int boxWidth = (int) Math.ceil((float) newResWidth / originalResWidth);
		int boxHeight = (int) Math.ceil((float) newResHeight / originalResHeight);
		int srcIndex = 0;
		int srcBound = originalResWidth * originalResHeight;
		
		ArrayList<ResolutionMapBoundingBox> boxes = new ArrayList<ResolutionMapBoundingBox>();
		
		for (int rowPrime = 0; rowPrime < newResHeight; rowPrime += boxHeight) {
			
			for(int colPrime = 0; colPrime < newResWidth; colPrime += boxWidth) {
				
				if (srcIndex >= srcBound) {
					throw new ArrayIndexOutOfBoundsException(
						String.format("Not enough source pixels to map into new resolution. Invalid source index: %d", srcIndex));
				}
				
				boxes.add(new ResolutionMapBoundingBox(
					new Coordinate(colPrime, rowPrime),
					new Coordinate(colPrime + boxWidth - 1, rowPrime + boxHeight - 1),
					srcIndex));
				
				srcIndex++;
			}
		}
		
		ResolutionMapBoundingBox[] t = new ResolutionMapBoundingBox[1];
		return boxes.toArray(t);
	}
	
	private static int[] _buildResolutionMap(ResolutionMapBoundingBox[] boxes, int newResWidth, int newResHeight) {
		
		int[] map = new int[newResWidth * newResHeight];
		
		int x = 0;
		int y = 0;
		for (int i = 0, len = map.length; i < len; i++) {
			x = i % newResWidth;
			y = i / newResWidth;
			map[i] = _assignSourceIndex(boxes, x, y);
		}
		
		return map;
	}

	private static int _assignSourceIndex(ResolutionMapBoundingBox[] boxes, int x, int y) {
		
		for (int i = 0, len = boxes.length; i < len; i++) {
			if(boxes[i].isInBoundary(x, y)) {
				return boxes[i].sourceIndex; 
			}
		}
		
		// Return failed
		return 0;
	}
	
} // ResolutionMapGenerator
