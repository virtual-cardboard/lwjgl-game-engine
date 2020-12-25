package bundle.visuals.display;

import bundle.visuals.renderer.AbstractGameRenderer;

/**
 * An abstract displayer that displays a given {@link Displayable}. Each
 * {@link AbstractDisplayer} will have a corresponding {@link Displayable}.
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
 * @param <D> generic {@link Displayable}
 */
public abstract class AbstractDisplayer<D extends Displayable> {

	protected AbstractGameRenderer renderer;

	/**
	 * Takes in a {@link AbstractGameRenderer} and saves it.
	 * 
	 * @param renderer the renderer
	 */
	public AbstractDisplayer(AbstractGameRenderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * Displays the given {@link Displayable};
	 * 
	 * @param displayable the displayable
	 */
	public abstract void display(D displayable);

}
