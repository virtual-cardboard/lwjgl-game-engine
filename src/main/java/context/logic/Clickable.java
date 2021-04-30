package context.logic;

/**
 * Something that executes a {@link Runnable} when clicked. When a clickable is
 * clicked, the onClick() function should be called.
 * 
 * Usually, anything that implements Clickable also implements
 * {@link Hoverable}.
 * 
 * @author Jay
 *
 */
public interface Clickable {

	/**
	 * Determines if a pixel at (x, y) is on the clickable.
	 * 
	 * @param x x coordinates of the pixel
	 * @param y y coordinates of the pixel
	 * @return Whether or not the pixel at (x, y) is on the clickable
	 */
	public boolean isOn(float x, float y);

	public default void onPress() {
		getOnPress().run();
	}

	public Runnable getOnPress();

	public default void onRelease() {
		getOnRelease().run();
	}

	public Runnable getOnRelease();

	public default void onClick() {
		getOnClick().run();
	}

	public Runnable getOnClick();

}
