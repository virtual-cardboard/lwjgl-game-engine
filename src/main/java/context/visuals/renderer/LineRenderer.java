package context.visuals.renderer;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector3f;
import context.GLContext;
import context.visuals.builtin.LineShaderProgram;
import context.visuals.builtin.RectangleVertexArrayObject;
import context.visuals.colour.Colour;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.VertexArrayObject;

/**
 * A {@link GameRenderer} that renders line segments.
 * 
 * @author Jay
 *
 */
public class LineRenderer extends GameRenderer {

	/** The shader program used to render lines. */
	private ShaderProgram shaderProgram;
	private RectangleVertexArrayObject vao;

	/**
	 * Creates a <code>LineRenderer</code> using a {@link LineShaderProgram} and a
	 * {@link RectangleVertexArrayObject}.
	 * 
	 * @param shaderProgram the <code>LineShaderProgram</code>
	 * @param vao           the <code>RectangleVertexArrayObject</code>
	 */
	public LineRenderer(LineShaderProgram shaderProgram, RectangleVertexArrayObject vao) {
		this.shaderProgram = shaderProgram;
		this.vao = vao;
	}

	/**
	 * Renders a line segment from (x1, y1) to (x2, y2) with the given line width
	 * and colour. This needs the screen dimensions in order to convert pixel
	 * coordinates into normalized device coordinates.
	 * 
	 * @param rectangleVao the {@link VertexArrayObject} to use
	 * @param screenDim    the dimensions of the screen
	 * @param x1           the x value of the first point of the line, in pixels
	 * @param y1           the y value of the first point of the line, in pixels
	 * @param x2           the x value of the second point of the line, in pixels
	 * @param y2           the y value of the second point of the line, in pixels
	 * @param width        the width or thickness of the line, in pixels
	 * @param colour       the colour of the line
	 * 
	 * @see Colour
	 */
	public void renderPixelCoords(GLContext glContext, Vector2f screenDim, float x1, float y1, float x2, float y2, float width, int colour) {
		// Calculations for matrix transformations
		width = Math.abs(width);
		float halfWidth = width * 0.5f;
		Matrix4f matrix4f = new Matrix4f();
		float rectLength = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)) + width;
		float theta = (float) Math.atan2(y1 - y2, x2 - x1);

		// Matrix transformations
		matrix4f.translate(-1, 1);
		matrix4f.scale(2, -2).scale(1 / screenDim.x, 1 / screenDim.y);
		matrix4f.translate(x1, y1);
		matrix4f.rotate(-theta, Vector3f.Z_AXIS);
		matrix4f.translate(-halfWidth, -halfWidth);
		matrix4f.scale(rectLength, width);

		// Set uniforms
		shaderProgram.bind(glContext);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setFloat("x1", x1);
		shaderProgram.setFloat("y1", y1);
		shaderProgram.setFloat("x2", x2);
		shaderProgram.setFloat("y2", y2);
		shaderProgram.setFloat("halfWidth", halfWidth);
		shaderProgram.setColour("fill", colour);

		// Display VAO
		vao.draw(glContext);
	}

}
