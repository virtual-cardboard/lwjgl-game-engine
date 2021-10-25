package context.visuals.gui;

import common.math.Matrix4f;
import context.GLContext;
import context.GameContext;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.VertexArrayObject;

public class ColourGui extends Gui {

	private ShaderProgram shaderProgram;
	private VertexArrayObject rectangleVAO;
	private GLContext glContext;

	public ColourGui(GameContext context) {
		shaderProgram = context.resourcePack().getShaderProgram("default");
		rectangleVAO = context.resourcePack().rectangleVAO();
		glContext = context.glContext();
	}

	@Override
	public void render(Matrix4f matrix4f, float x, float y, float width, float height) {
		matrix4f.translate(x, y).scale(width, height);
		shaderProgram.bind();
		shaderProgram.setMat4("matrix4f", matrix4f);
		rectangleVAO.display(glContext);
	}

}
