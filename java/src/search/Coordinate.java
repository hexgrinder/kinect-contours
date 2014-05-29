/**
 * 
 */
package hexgrinder.search;

/**
 * @author M Layman
 *
 */
public class Coordinate {
	
	private int _x;
	private int _y;
	
	public Coordinate() {
		this(0,0);
	}
	
	public Coordinate(int x, int y) {
		this._x = x;
		this._y = y;
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}

	public void setX(int x) {
		_x = x;
	}
	
	public void setY(int y) {
		_y = y;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(" + this._x + ", " + this._y + ")");
		return sb.toString();
	}
	
	
} // Coordinate
