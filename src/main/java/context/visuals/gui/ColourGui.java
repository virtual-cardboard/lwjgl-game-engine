package context.visuals.gui;

import common.math.Matrix4f;
import common.math.Vector2f;
import context.GLContext;
import context.visuals.builtin.RectangleVertexArrayObject;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.VertexArrayObject;

public class ColourGui extends Gui {

	private ShaderProgram shaderProgram;
	private VertexArrayObject vao;
	private int colour;

	public ColourGui(ShaderProgram defaultShaderProgram, RectangleVertexArrayObject vao, int colour) {
		shaderProgram = defaultShaderProgram;
		this.vao = vao;
		this.colour = colour;
	}

	@Override
	public void render(GLContext glContext, Vector2f screenDim, float x, float y, float width, float height) {
		Matrix4f matrix4f = rectToPixelMatrix4f(screenDim).translate(x, y).scale(width, height);
		shaderProgram.bind();
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setColour("fill", colour);
		vao.draw(glContext);
	}

}
