package context.visuals.displayer;

import context.visuals.renderer.GameRenderer;

/**
 * A displayer that displays a given {@link Displayable}. Each
 * {@link Displayable} should have a corresponding {@link Displayer}.
 * <p>
 * 
 * @author Jay, Lunkle
 *
 * @param <D> generic {@link Displayable}
 */
public abstract class Displayer<D extends Displayable> {

	protected GameRenderer renderer;

	/**
	 * Takes in a {@link GameRenderer} and saves it.
	 * 
	 * @param renderer the renderer
	 */
	public Displayer(GameRenderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * Displays the given {@link Displayable};
	 * 
	 * @param displayable the displayable
	 */
	public abstract void display(D displayable);

}
