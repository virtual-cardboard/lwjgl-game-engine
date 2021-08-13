package context.visuals.gui.renderer;

import common.math.Matrix4f;
import context.visuals.colour.Colour;
import context.visuals.gui.Gui;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.renderer.shader.ShaderProgram;

public class DefaultGuiRenderer extends GuiRenderer<Gui> {

	protected ShaderProgram shaderProgram;
	protected VertexArrayObject vao;

	public DefaultGuiRenderer(ShaderProgram guiShaderProgram, VertexArrayObject rectangleVao) {
		this.shaderProgram = guiShaderProgram;
		this.vao = rectangleVao;
	}

	@Override
	public void render(Gui gui, Matrix4f matrix4f, float parentX, float parentY, float parentWidth, float parentHeight) {
		float x = gui.getPosXConstraint().calculateValue(parentX, parentX + parentWidth);
		float y = gui.getPosYConstraint().calculateValue(parentY, parentY + parentHeight);
		float width = gui.getWidthConstraint().calculateValue(parentX, parentX + parentWidth);
		float height = gui.getHeightConstraint().calculateValue(parentY, parentY + parentHeight);

		shaderProgram.bind();
		shaderProgram.setMat4("matrix4f", matrix4f.clone().translate(x, y).scale(width, height));
		shaderProgram.setVec4("fill", Colour.toNormalizedVector(gui.getBackgroundColour()));
		vao.display();
	}

}
