package context.visuals.builtin;

import common.math.Matrix4f;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.renderer.GameRenderer;

public class RectangleRenderer extends GameRenderer {

	private ShaderProgram shaderProgram;
	private RectangleVertexArrayObject vao;

	public RectangleRenderer(ShaderProgram defaultShaderProgram, RectangleVertexArrayObject vao) {
		this.shaderProgram = defaultShaderProgram;
		this.vao = vao;
	}

	public void render(float x, float y, float width, float height, int colour) {
		Matrix4f matrix4f = new Matrix4f().translate(-1, 1).scale(2f / glContext.width(), -2f / glContext.height()).translate(x, y).scale(width, height);
		render(matrix4f, colour);
	}

	public void render(float x, float y, float d, float width, float height, int colour) {
		Matrix4f matrix4f = new Matrix4f().translate(-1, 1).scale(2f / glContext.width(), -2f / glContext.height()).translate(x, y, d).scale(width, height);
		render(matrix4f, colour);
	}

	public void render(Matrix4f matrix4f, int colour) {
		shaderProgram.bind(glContext);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setColour("fill", colour);
		vao.draw(glContext);
	}

}
