package context.visuals.renderer;

import common.math.Matrix4f;
import common.math.Vector2f;
import context.GameContext;
import context.ResourcePack;
import context.visuals.colour.Colour;
import context.visuals.gui.RootGui;
import context.visuals.lwjgl.ShaderProgram;

/**
 * A {@link GameRenderer} that renders ellipses.
 * 
 * @author Jay
 *
 */
public class EllipseRenderer extends GameRenderer {

	/** The shader program used to render ellipses. */
	private ShaderProgram shaderProgram;

	/**
	 * Creates a <code>EllipseRenderer</code> using the ellipse shader program from
	 * a {@link GameContext}'s {@link ResourcePack}. The ellipse shader program
	 * should be put into the <code>ResourcePack</code> with the name
	 * <code>"ellipse"</code>.
	 * 
	 * @param context the <code>GameContext</code>
	 */
	public EllipseRenderer(GameContext context) {
		super(context);
		this.shaderProgram = resourcePack.getShaderProgram("ellipse");
	}

	/**
	 * Uses the position and dimensions of the ellipse to construct a
	 * {@link Matrix4f}, then calls {@link #renderWithMatrixOnly(Matrix4f, int)}
	 * using that matrix.
	 * 
	 * @param rootGui the {@link RootGui}
	 * @param x       the x-pos of the center of the ellipse, in pixel coordinates
	 * @param y       the y-pos of the center of the ellipse, in pixel coordinates
	 * @param width   the width of the ellipse, in pixel coordinates
	 * @param height  the height of the ellipse, in pixel coordinates
	 * @param colour  the colour of the ellipse
	 * 
	 * @see RootGui
	 * @see Colour
	 */
	public void renderPixelCoords(RootGui rootGui, float x, float y, float width, float height, int colour) {
		Vector2f center = new Vector2f(x, y);
		Vector2f dimensions = new Vector2f(width, height);
		shaderProgram.bind();
		Vector2f rootGuiDimensions = rootGui.getDimensions();
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1);
		matrix4f.scale(2, -2).scale(1 / rootGuiDimensions.x, 1 / rootGuiDimensions.y);
		matrix4f.translate(center.copy().sub(dimensions.copy().scale(0.5f)));
		matrix4f.scale(dimensions);
		renderWithMatrixOnly(matrix4f, colour);
	}

	/**
	 * Calculates the center position and dimensions of the ellipse, then calls
	 * {@link #renderNDC(Matrix4f, float, float, float, float, int)} using those
	 * calculated values.
	 * 
	 * @param matrix4f the transformation matrix
	 * @param colour   the colour of the ellipse
	 * 
	 * @see Matrix4f
	 * @see Colour
	 */
	public void renderWithMatrixOnly(Matrix4f matrix4f, int colour) {
		Vector2f transformedCenter = matrix4f.transform(0.5f, 0.5f);
		Vector2f v00 = matrix4f.transform(0, 0);
		Vector2f v01 = matrix4f.transform(0, 1);
		Vector2f v10 = matrix4f.transform(1, 0);
		float x = transformedCenter.x;
		float y = transformedCenter.y;
		float width = v10.x - v00.x;
		float height = v01.y - v00.y;
		renderNDC(matrix4f, x, y, width, height, colour);
	}

	/**
	 * Renders an ellipse using the x, y, width, height (all in normalized device
	 * coordinates), matrix, and colour.
	 * 
	 * @param matrix4f the transformation matrix
	 * @param x        the x-pos of the center of the ellipse, in normalized device
	 *                 coordinates
	 * @param y        the y-pos of the center of the ellipse, in normalized device
	 *                 coordinates
	 * @param width    the width of the ellipse, in normalized device coordinates
	 * @param height   the height of the ellipse, in normalized device coordinates
	 * @param colour   the colour of the ellipse
	 * 
	 * @see Matrix4f
	 * @see Colour
	 */
	public void renderNDC(Matrix4f matrix4f, float x, float y, float width, float height, int colour) {
		shaderProgram.bind();
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setFloat("x", x);
		shaderProgram.setFloat("y", y);
		shaderProgram.setFloat("width", width);
		shaderProgram.setFloat("height", height);
		shaderProgram.setVec4("colour", Colour.toNormalizedVector(colour));
		resourcePack.rectangleVAO().display(glContext);
	}

}
