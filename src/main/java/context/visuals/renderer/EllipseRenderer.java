package context.visuals.renderer;

import common.math.Matrix4f;
import common.math.Vector2f;
import context.visuals.colour.Colour;
import context.visuals.gui.RootGui;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.renderer.shader.ShaderProgram;

public class EllipseRenderer extends GameRenderer {

	private static final Vector2f NEG_ONE_ONE = new Vector2f(-1f, 1f);
	private static final Vector2f TWO_NEG_TWO = new Vector2f(2, -2);
	private ShaderProgram shaderProgram;
	private VertexArrayObject vao;

	public EllipseRenderer(ShaderProgram ellipseShaderProgram, VertexArrayObject rectangleVao) {
		this.shaderProgram = ellipseShaderProgram;
		this.vao = rectangleVao;
	}

	public void render(RootGui rootGui, float x, float y, float width, float height, int colour) {
		Vector2f center = new Vector2f(x, y);
		Vector2f dimensions = new Vector2f(width, height);
		shaderProgram.bind();
		Vector2f rootGuiDimensions = rootGui.getDimensions();
		Matrix4f transform = new Matrix4f();
		transform.translate(NEG_ONE_ONE);
		transform.scale(TWO_NEG_TWO.copy().divide(rootGuiDimensions));
		transform.translate(center.copy().sub(dimensions.copy().scale(0.5f)));
		transform.scale(dimensions);
		shaderProgram.setMat4("transform", transform);
		Vector2f transformedCenter = Matrix4f.transform(transform, new Vector2f(0.5f, 0.5f)); // [563.2188, -105.778656]
		shaderProgram.setFloat("x", transformedCenter.x);
		shaderProgram.setFloat("y", transformedCenter.y);
		Vector2f transformedDimensions = dimensions.copy().divide(rootGuiDimensions).scale(2);
		shaderProgram.setFloat("width", transformedDimensions.x);
		shaderProgram.setFloat("height", transformedDimensions.y);
		shaderProgram.setVec4("colour", Colour.toNormalizedVector(colour));
		vao.display();
	}

}
