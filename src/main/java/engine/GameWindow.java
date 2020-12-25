package engine;

import bundle.GameBundleWrapper;
import common.coordinates.PixelCoordinates;

/**
 * A basic engine interface. Has a init() function, startEngine() function,
 * getWrapper() function, and setBundleWrapper() function.
 * <p>
 * init() initializes the game engine. Everything in here would run once at the
 * beginning.
 * <p>
 * startWindow() is similar to the init(), but it would start everything
 * running.
 * <p>
 * getBundleWrapper() returns the attached bundle wrapper.
 * <p>
 * setBundleWrapper() attaches the provided bundle wrapper to the game engine.
 * 
 * @author Jay
 *
 */
public interface GameWindow {

	public default void init() {
	}

	public abstract void startWindow();

	public abstract GameBundleWrapper getBundleWrapper();

	public abstract void setBundleWrapper(GameBundleWrapper wrapper);

	public abstract String getWindowTitle();

	public abstract void setWindowTitle(String windowTitle);

	public abstract PixelCoordinates getWindowDimensions();

	public abstract void setWindowDimensions(PixelCoordinates windowDimensions);

}
