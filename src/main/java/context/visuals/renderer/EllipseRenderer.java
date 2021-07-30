package context.visuals.renderer;

import common.math.Matrix4f;
import common.math.Vector2f;
import context.visuals.colour.Colour;
import context.visuals.gui.RootGui;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.renderer.shader.ShaderProgram;

public class EllipseRenderer extends GameRenderer {

	private static final Vector2f NEG_ONE_ONE = new Vector2f(-1f, 1f);
	private static final Vector2f ONE_NEG_ONE = new Vector2f(1f, -1f);
	private static final Vector2f TWO_NEG_TWO = new Vector2f(2, -2);
	private ShaderProgram shaderProgram;
	private VertexArrayObject vao;

	public EllipseRenderer(ShaderProgram ellipseShaderProgram, VertexArrayObject rectangleVao) {
		this.shaderProgram = ellipseShaderProgram;
		this.vao = rectangleVao;
	}

	public void render(RootGui rootGui, Vector2f center, Vector2f dimensions, int innerColour) {
		render(rootGui, center, dimensions, innerColour, 0);
	}

	public void render(RootGui rootGui, Vector2f center, Vector2f dimensions, int innerColour, float outerWidth) {
		render(rootGui, center, dimensions, innerColour, 255, outerWidth);
	}

	public void render(RootGui rootGui, Vector2f center, Vector2f dimensions, int innerColour, int outerColour, float outerWidth) {
		shaderProgram.bind();
		Vector2f rootGuiDimensions = rootGui.getDimensions();
		Matrix4f transform = new Matrix4f();
		transform.translate(NEG_ONE_ONE);
		transform.scale(TWO_NEG_TWO.copy().divide(rootGuiDimensions));
		transform.translate(center.copy().sub(dimensions.copy().scale(0.5f)));
		transform.scale(dimensions);
		shaderProgram.setMat4("transform", transform);
		Vector2f transformedCenter = Matrix4f.transform(transform, new Vector2f(0.5f, 0.5f)); // [563.2188, -105.778656]
		shaderProgram.setVec2("centerPos", transformedCenter);
		Vector2f transformedDimensions = dimensions.copy().divide(rootGuiDimensions).scale(2);
		shaderProgram.setVec2("dimensions", transformedDimensions);
		shaderProgram.setVec4("innerColour", Colour.toNormalizedVector(innerColour));
		shaderProgram.setVec4("outerColour", Colour.toNormalizedVector(outerColour));
		shaderProgram.setFloat("outerWidthX", 2 * outerWidth / rootGuiDimensions.x);
		shaderProgram.setFloat("outerWidthY", 2 * outerWidth / rootGuiDimensions.y);
		vao.display();
	}

}
