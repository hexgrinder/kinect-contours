/**
 * 
 */
package hexgrinder.canvas;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author hexgrinder
 *
 */
public abstract class Layer <T extends Pixel> 
	implements Paintable, Iterable<T> 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1838302251009846228L;
	
	private final ArrayList<T> _pixels;
	
	public Layer(Class<T> type, int width, int height) {
		
		this.width = width;
		this.height = height;
		this.size = width * height;
		
		_pixels = new ArrayList<T>(width * height);
		
		try {
			for (int i = 0; i < this.size; i++) {
				_pixels.add(type.newInstance());
			}
		} catch (InstantiationException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public final int width;
	public final int height;
	public final int size;

	/**
	 * Returns the object at the given index.
	 * 
	 * @param index Index to return.
	 * @return Returns the object at the given index.
	 */
	public T get(int index) {
		return _pixels.get(index);
	}
	
	/**
	 * Clears the layer to black
	 */
	public void clear() {
		for (Pixel p : _pixels) {
			p.clear();
		}
	}
	
	/**
	 * Performs an update to the layer's pixels.
	 */
	public abstract void update();
	
	@Override
	public void paint(Object canvas) {
		for (Pixel p : _pixels) {
			// TODO: Paint color onto canvas
			p.getColor();
		}
	}
	
	
	@Override
	public Iterator<T> iterator() {
		return _pixels.iterator();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		_pixels.clear();
		super.finalize();
	}
	
	
} // Layer
