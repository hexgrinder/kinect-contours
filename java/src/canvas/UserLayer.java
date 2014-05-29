/**
 * 
 */
package hexgrinder.canvas;

/**
 * @author a
 *
 */
public class UserLayer extends Layer<UserPixel> {

	
	public UserLayer(int width, int height) {
		this(width,height,0);
	}
	
	public UserLayer(int width, int height, int lifeTime) {
		super(UserPixel.class, width, height);
		for (UserPixel p : this) {
			p.setCurrLifeTime(lifeTime);
		}
	}
	
	// TODO??: Should this not be here?? Should be in processing layer??
	// Revealing the iterator gives access to the pixels
	/* (non-Javadoc)
	 * @see hexgrinder.canvas.Canvas#update()
	 */
	@Override
	public void update() {
		/*
		for (UserPixel p : this) {
			if (p.userId == 0) {
				// Pixel is empty -> fade pixel color
				_decayColorByTick(p);
			} else if (p.changed) { 
				// Same user occupying pixel -> stay on
				p.setCurrLifeTime(MAX_LIFETIME);
				p.changed = false;
			} else {
				// New user in pixel -> overwrite color & reset time
				p.setCurrLifeTime(MAX_LIFETIME);
				p.setColor(100, 100, 100, 255);
			}
		}
		*/
	}
	
	// TODO: RECEIVE USER DATA FROM KINECT, THEN UPDATE BASED ON USER PIXEL
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		int cnt = 0;
		for (UserPixel p : this) {
			
			sb.append(p.getCurrLifeTime() + " ");
			//sb.append(p.userId + " ");
			
			cnt++;
			if ((cnt % this.width) == 0) {
				sb.append("\n");
			}
		
		}
		
		sb.append("\nCount: " + cnt);
		
		return sb.toString();
	}
	
} // UserLayer
