/**
 * 
 */
package hexgrinder.canvas;

/**
 * @author hexgrinder
 *
 */
public class FadeLayer extends Layer<FadePixel> {
	
	/**
	 * Default c'tor
	 * 
	 * @param width Canvas width
	 * @param height Canvas height
	 * @param lifeTime Fade value for pixel
	 */
	public FadeLayer(int width, int height, int lifeTime) {
		super(FadePixel.class, width, height);
		for (FadePixel p : this) {
			p.setCurrLifeTime(lifeTime);
		}
	}

	/* (non-Javadoc)
	 * @see hexgrinder.canvas.Canvas#update()
	 */
	@Override
	public void update() {
		for (FadePixel p : this) {
			_decayColorByTick(p);
		}
	}
	
	private void _decayColorByTick(FadePixel pixel) {
		
		float life = pixel.getCurrLifeTime();
		
		if (life <= 0) { return; }
		
		// TODO?: Decay / fade by alpha?
		//float decay = this._currLifeTime / this._maxLifeTime;
		
		// update decay time
		pixel.setCurrLifeTime(life-1);
		
		// decay color
		Color c = pixel.getColor();
		pixel.setColor(
			(c.red == 0) ? 0 :  c.red-1, 
			(c.green == 0) ? 0 : c.green-1, 
			(c.blue == 0) ? 0 : c.blue-1, 
			c.alpha
		);
		c = null;
	}
	
} // FadeLayer
