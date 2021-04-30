package context.logic;

/**
 * Something that executes a {@link Runnable} when the cursor first hovers over
 * it. When a hoverable is first hovered over, the onHover() function should be
 * called, and when it is first dehovered, the onDehover() function should be
 * called.
 * 
 * @author Jay
 *
 */
public interface Hoverable {

	/**
	 * Determines if a pixel at (x, y) is on the hoverable.
	 * 
	 * @param x x coordinates of the pixel
	 * @param y y coordinates of the pixel
	 * @return Whether or not the pixel at (x, y) is on the hoverable
	 */
	public boolean isOn(float x, float y);

	public default void onHover() {
		getOnHover().run();
	}

	public default void onDehover() {
		getOnDehover().run();
	}

	public Runnable getOnHover();

	public Runnable getOnDehover();

}
