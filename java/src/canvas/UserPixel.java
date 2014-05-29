/**
 * 
 */
package hexgrinder.canvas;

/**
 * @author hexgrinder
 *
 */
public class UserPixel extends FadePixel {

	public UserPixel() {
		super();
		this.userId = 0;
		this.changed = false;
	}
	
	public short userId;
	public boolean changed;
	
} // UserPixel
