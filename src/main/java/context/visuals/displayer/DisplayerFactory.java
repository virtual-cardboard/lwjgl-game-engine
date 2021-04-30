package context.visuals.displayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
 * 2. Make a class extending the {@link AbstractDisplayer} class under the package
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
	@SuppressWarnings("rawtypes")
	Map<Class<? extends Displayable>, AbstractDisplayer> displayableToDisplayer = new HashMap<>();

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
	public AbstractDisplayer getDisplayer(Displayable displayable) throws ClassNotFoundException {
		// Checking first to see if the corresponding displayer already exists in the
		// hashmap
		AbstractDisplayer displayer = displayableToDisplayer.get(displayable.getClass());
		if (displayer != null) {
			return displayer;
		}

		String className = displayable.getDisplayerName();
		try {
			// Instantiate the displayer using the dark magic
			Class<? extends AbstractDisplayer> displayerClass = Class.forName(className).asSubclass(AbstractDisplayer.class);
			Constructor<? extends AbstractDisplayer> displayerConstructor = displayerClass.getConstructor(GameRenderer.class);
			displayer = displayerConstructor.newInstance(renderer);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		// Saving it into the hashmap for future use
		displayableToDisplayer.put(displayable.getClass(), displayer);

		// Returning the displayer
		return displayer;
	}

}
