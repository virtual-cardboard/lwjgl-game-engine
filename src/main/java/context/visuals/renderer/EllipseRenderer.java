package context.visuals.renderer;

import common.math.Matrix4f;
import common.math.Vector2f;
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

	public void renderPixelCoords(final RootGui rootGui, float x, float y, float width, float height, final int colour) {
		Vector2f center = new Vector2f(x, y);
		Vector2f dimensions = new Vector2f(width, height);
		shaderProgram.bind();
		Vector2f rootGuiDimensions = rootGui.getDimensions();
		Matrix4f transform = new Matrix4f();
		transform.translate(-1, 1);
		transform.scale(2, -2).scale(1 / rootGuiDimensions.x, 1 / rootGuiDimensions.y);
		transform.translate(center.copy().sub(dimensions.copy().scale(0.5f)));
		transform.scale(dimensions);
		renderWithMatrixOnly(transform, colour);
	}

	public void renderWithMatrixOnly(final Matrix4f matrix4f, final int colour) {
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

	public void renderNDC(final Matrix4f matrix4f, float x, float y, float width, float height, int colour) {
		shaderProgram.bind();
		shaderProgram.setMat4("transform", matrix4f);
		shaderProgram.setFloat("x", x);
		shaderProgram.setFloat("y", y);
		shaderProgram.setFloat("width", width);
		shaderProgram.setFloat("height", height);
		shaderProgram.setVec4("colour", Colour.toNormalizedVector(colour));
		vao.display();
	}

}
