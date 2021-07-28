package context.visuals.renderer;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector3f;
import context.visuals.colour.Colour;
import context.visuals.gui.RootGui;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.renderer.shader.ShaderProgram;

public class EllipseRenderer extends GameRenderer {

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
		render(rootGui, center, dimensions, innerColour, 0, outerWidth);
	}

	public void render(RootGui rootGui, Vector2f center, Vector2f dimensions, int innerColour, int outerColour, float outerWidth) {
		shaderProgram.bind();
		Matrix4f transform = new Matrix4f();
		transform.translate(new Vector2f(-0.5f, -0.5f));
		transform.scale(new Vector3f(dimensions.x * 0.5f / rootGui.getWidth(), -dimensions.y * 0.5f / rootGui.getHeight(), 1));
		transform.translate(center);
		shaderProgram.setMat4("tranform", transform);
		shaderProgram.setVec2("centerPos", center);
		shaderProgram.setVec2("dimensions", dimensions);
		shaderProgram.setVec4("innerColour", Colour.toNormalizedVector(innerColour));
		if (outerWidth > 0)
			shaderProgram.setVec4("outerColour", Colour.toNormalizedVector(outerColour));
		shaderProgram.setFloat("outerWidth", outerWidth);
		vao.display();
	}

}
