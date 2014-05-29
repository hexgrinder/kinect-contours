/**
 * 
 */
package hexgrinder.canvas;

/**
 * FadePixel.java
 * 
 * Pixel whose color fades in/out over time.  Extends 
 * the Pixel class.
 * 
 * @author hexgrinder
 */
public class FadePixel extends Pixel {

	private float _currLifeTime = 0;

	public FadePixel () {
		this(0);
	}
	
	/**
	 * Default c'tor.
	 * 
	 * Initialization:
	 * >> All colors to 0
	 * >> Alpha to 255
	 * >> LifeTime to lifeTime
	 * >> isDirty to false
	 */
	public FadePixel(float lifeTime) {
		super(0,0,0,255);
		this.setCurrLifeTime(lifeTime);
		this.isDirty = false;
	}

	public boolean isDirty;
	
	public float getCurrLifeTime() {
		return this._currLifeTime;
	}
	
	public void setCurrLifeTime(float lifeTime) {
		this._currLifeTime = lifeTime;
	}
	
} // FadePixel
