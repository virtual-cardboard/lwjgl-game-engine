package context.visuals.renderer;

import static context.visuals.defaultvao.RectangleVertexArrayObject.rectangleVAO;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector3f;
import context.visuals.colour.Colour;
import context.visuals.gui.RootGui;
import context.visuals.lwjgl.Shader;
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

	/**
	 * Creates an {@link LineRenderer} with the given {@link ShaderProgram} and
	 * rectangular {@link VertexArrayObject}.
	 * 
	 * @param lineShaderProgram the shader program to use when rendering lines
	 * 
	 * @see ShaderProgram
	 * @see Shader
	 */
	public LineRenderer(ShaderProgram lineShaderProgram) {
		this.shaderProgram = lineShaderProgram;
	}

	/**
	 * Renders a line segment from (x1, y1) to (x2, y2) with the given line width
	 * and colour. This needs the {@link RootGui} in order to convert pixel
	 * coordinates into normalized device coordinates.
	 * 
	 * @param rectangleVao the {@link VertexArrayObject} to use
	 * @param rootGui      the <code>RootGui</code>
	 * @param x1           the x value of the first point of the line, in pixels
	 * @param y1           the y value of the first point of the line, in pixels
	 * @param x2           the x value of the second point of the line, in pixels
	 * @param y2           the y value of the second point of the line, in pixels
	 * @param width        the width or thickness of the line, in pixels
	 * @param colour       the colour of the line
	 * 
	 * @see Colour
	 */
	public void renderPixelCoords(RootGui rootGui, float x1, float y1, float x2, float y2, float width, int colour) {
		// Calculations for matrix transformations
		width = Math.abs(width);
		Vector2f rootGuiDimensions = rootGui.getDimensions();
		float halfWidth = width * 0.5f;
		Matrix4f matrix4f = new Matrix4f();
		float rectLength = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)) + width;
		float theta = (float) Math.atan2(y1 - y2, x2 - x1);

		// Matrix transformations
		matrix4f.translate(-1, 1);
		matrix4f.scale(2, -2).scale(1 / rootGuiDimensions.x, 1 / rootGuiDimensions.y);
		matrix4f.translate(x1, y1);
		matrix4f.rotate(-theta, Vector3f.Z_AXIS);
		matrix4f.translate(-halfWidth, -halfWidth);
		matrix4f.scale(rectLength, width);

		// Set uniforms
		shaderProgram.bind();
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setFloat("x1", x1);
		shaderProgram.setFloat("y1", y1);
		shaderProgram.setFloat("x2", x2);
		shaderProgram.setFloat("y2", y2);
		shaderProgram.setFloat("halfWidth", halfWidth);
		shaderProgram.setVec4("colour", Colour.toNormalizedVector(colour));

		// Display VAO
		rectangleVAO().display();
	}

}
