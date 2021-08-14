package context.visuals.gui.renderer;

import common.math.Matrix4f;
import context.visuals.colour.Colour;
import context.visuals.gui.TexturedGui;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.renderer.TextureRenderer;
import context.visuals.renderer.shader.ShaderProgram;

public class TexturedGuiRenderer extends GuiRenderer<TexturedGui> {

	protected TextureRenderer textureRenderer;
	protected ShaderProgram shaderProgram;
	protected VertexArrayObject vao;

	public TexturedGuiRenderer(TextureRenderer textureRenderer, ShaderProgram guiShaderProgram, VertexArrayObject rectangleVao) {
		this.textureRenderer = textureRenderer;
		this.shaderProgram = guiShaderProgram;
		this.vao = rectangleVao;
	}

	@Override
	public void render(TexturedGui gui, Matrix4f matrix4f, float parentX, float parentY, float parentWidth, float parentHeight) {
		float x = gui.getPosXConstraint().calculateValue(parentX, parentX + parentWidth);
		float y = gui.getPosYConstraint().calculateValue(parentY, parentY + parentHeight);
		float width = gui.getWidthConstraint().calculateValue(parentX, parentX + parentWidth);
		float height = gui.getHeightConstraint().calculateValue(parentY, parentY + parentHeight);
		matrix4f = matrix4f.clone().translate(x, y).scale(width, height);

		shaderProgram.bind();
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setVec4("fill", Colour.toNormalizedVector(gui.getBackgroundColour()));
		vao.display();
		textureRenderer.render(gui.getTexture(), matrix4f);
	}

}
