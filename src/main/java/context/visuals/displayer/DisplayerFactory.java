package context.visuals.displayer;

import java.util.HashMap;
import java.util.Map;

import context.visuals.renderer.GameRenderer;

/**
 * A factory that finds the corresponding displayer for a given displayable.
 * <p>
 * If you are to add functionality to the {@link DisplayerFactory}, follow the
 * steps outlined below:
 * <p>
 * 1. Make a class implementing the {@link Displayable} interface under the
 * package specifics.bundle.visuals.displayable or bundle.visuals.displayable.
 * <p>
 * 2. Make a class extending the {@link Displayer} class under the package
 * specifics.bundle.visuals.displayer or bundle.visuals.displayer. You must give
 * the class the same name as the Displayable with the suffix "Displayer".
 * <p>
 * Example:
 * 
 * <pre>
 * public class Rectangle implements Displayable
 * public class RectangleDisplayer extends Displayer
 * </pre>
 * 
 * @author Jay
 *
 */
public class DisplayerFactory {

	private GameRenderer renderer;
	Map<Class<? extends Displayable>, Displayer<? extends Displayable>> displayableToDisplayer = new HashMap<>();

	/**
	 * Takes in a renderer and saves it.
	 * 
	 * @param renderer the renderer
	 */
	public DisplayerFactory(GameRenderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * Returns the corresponding displayer for a given displayable.
	 * 
	 * @param displayable the displayable
	 * @return the corresponding displayer
	 * @throws ClassNotFoundException corresponding displayer not found
	 */
	@SuppressWarnings("rawtypes")
	public Displayer getDisplayer(Displayable displayable) {
		Displayer displayer = displayableToDisplayer.get(displayable.getClass());
		if (displayer != null) {
			throw new RuntimeException("Displayer not found for displayable " + displayable.getClass().getName());
		}
		// Returning the displayer
		return displayer;
	}

}
