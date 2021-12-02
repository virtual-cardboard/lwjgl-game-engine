package context.visuals.builtin;

import common.math.Matrix4f;
import common.math.Vector2f;
import context.GLContext;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.renderer.GameRenderer;

public class RectangleRenderer extends GameRenderer {

	private ShaderProgram shaderProgram;
	private RectangleVertexArrayObject vao;

	public RectangleRenderer(ShaderProgram defaultShaderProgram, RectangleVertexArrayObject vao) {
		this.shaderProgram = defaultShaderProgram;
		this.vao = vao;
	}

	public void render(GLContext glContext, Vector2f screenDim, float x, float y, float width, float height, int colour) {
		Matrix4f matrix4f = new Matrix4f().translate(-1, 1).scale(2f / screenDim.x, -2f / screenDim.y).translate(x, y).scale(width, height);
		render(glContext, matrix4f, colour);
	}

	public void render(GLContext glContext, Matrix4f matrix4f, int colour) {
		shaderProgram.bind(glContext);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setColour("fill", colour);
		vao.draw(glContext);
	}

}
