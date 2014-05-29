/**
 * 
 */
package hexgrinder.canvas;

/**
 * Pixel.java
 * 
 * Base functionality for all pixel.
 * 
 * @author hexgrinder
 */
public abstract class Pixel {

	private Color _color;
	
	/**
	 * Pixel constructor.
	 * 
	 * Sets pixel to default settings:
	 * 	  Black color, 
	 *    Alpha = 1
	 */
	public Pixel() {
		this(0,0,0,255);
	}

	/**
	 * Pixel constructor.
	 * 
	 * Initializes pixel an RGBA value.
	 * 
	 * @param r Red component
	 * @param g Green component
	 * @param b Blue component
	 * @param alpha Alpha transparency
	 */
	public Pixel(float r, float g, float b, float alpha) {
		this._color = new Color(r,g,b,alpha);
	}

	/**
	 * Get pixels color.  
 	 * 
	 * Will return the actual color object, so 
	 * do not set color using this method.  Use
	 * the setColor method instead.
     *
     * @return Returns pixel color.
	 */
	public Color getColor() {
		return this._color;
	}

	/**
	 * Sets pixel color.
	 * 
	 * @param f Red contribution
	 * @param g Green contribution
	 * @param h Blue contribution
	 * @param alpha Alpha transparency
	 */
	public void setColor(float f, float g, float h, float alpha) {
		this._color.red = f;
		this._color.green = g;
		this._color.blue = h;
		this._color.alpha = alpha;
	}
	
	/**
	 * Resets the pixel to black, alpha = 255
	 */
	public void clear() {
		this._color.red = 0;
		this._color.green = 0;
		this._color.blue = 0;
		this._color.alpha = 255;
	}
	
} // Pixel
