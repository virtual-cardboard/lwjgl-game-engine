package context.visuals.gui.renderer;

import common.math.Matrix4f;
import context.GameContextWrapper;
import context.visuals.gui.Gui;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.renderer.GameRenderer;

/**
 * A GameRenderer that renders {@link Gui Guis}.
 * 
 * @author Jay
 *
 * @param <T> the type of Gui that this class renders
 */
public abstract class GuiRenderer<T extends Gui> extends GameRenderer {

	public GuiRenderer(GameContextWrapper wrapper) {
		super(wrapper);
	}

	/**
	 * Casts the gui to T, then calls
	 * {@link #render(Gui, float, float, float, float) render(T, float, float,
	 * float, float)}.
	 * 
	 * @param gui          the gui to render
	 * @param matrix4f     the matrix that transforms a rectangular
	 *                     {@link VertexArrayObject} into the size of a pixel.
	 * @param parentX      the x coordinate of the top left corner of the parent
	 * @param parentY      the y coordinate of the top left corner of the parent
	 * @param parentWidth  the width of the parent
	 * @param parentHeight the height of the parent
	 */
	@SuppressWarnings("unchecked")
	final void renderGui(Gui gui, Matrix4f matrix4f, float parentX, float parentY, float parentWidth, float parentHeight) {
		try {
			render((T) gui, matrix4f, parentX, parentY, parentWidth, parentHeight);
		} catch (ClassCastException e) {
			throw new RuntimeException("Incompatible Gui and GuiRenderer types.\n"
					+ "Gui type:         " + gui.getClass() + "\n"
					+ "GuiRenderer type: " + this.getClass().getGenericSuperclass().getTypeName(), e);
		}
	}

	/**
	 * Renders a {@link Gui}.
	 * <p>
	 * Note: make sure to clone the matrix4f before transforming it.
	 * </p>
	 * 
	 * @param gui          the gui to render
	 * @param matrix4f     the matrix that transforms a rectangular
	 *                     {@link VertexArrayObject} into the size of a pixel.
	 * @param parentX      the x coordinate of the top left corner of the parent
	 * @param parentY      the y coordinate of the top left corner of the parent
	 * @param parentWidth  the width of the parent
	 * @param parentHeight the height of the parent
	 */
	public abstract void render(T gui, Matrix4f matrix4f, float parentX, float parentY, float parentWidth, float parentHeight);

}
