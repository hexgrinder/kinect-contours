/**
 * 
 */
package hexgrinder.canvas;

/**
 * @author hexgrinder
 *
 */
public interface Paintable {

	/**
	 * Paints the pixels onto the canvas.
	 * 
	 * @param canvas Canvas to paint on.
	 */
	void paint(Object canvas);
	
	/**
	 * Call to perform an update.
	 */
	void update();
	
} // Paintable
