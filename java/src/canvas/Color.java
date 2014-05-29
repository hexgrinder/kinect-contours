/**
 * 
 */
package hexgrinder.canvas;

/**
 * Color.java
 * 
 * RGB color representation
 * 
 * @author hexgrinder
 *
 */
public class Color {

	/**
	 * Color constructor.
	 * 
	 * @param R Red contribution
	 * @param G Green contribution
	 * @param B Blue contribution
	 * @param alpha Alpha transparency
	 */
	public Color(float R, float G, float B, float alpha) {
		this.red = R;
		this.green = G;
		this.blue = B;
		this.alpha = alpha;
	}
	
	/**
	 * Red contribution
	 */
	public float red;
	
	/**
	 * Green contribution
	 */
	public float green;
	
	/**
	 * Blue contribution
	 */
	public float blue;
	
	/**
	 * Alpha contribution
	 */
	public float alpha;

} // Color
