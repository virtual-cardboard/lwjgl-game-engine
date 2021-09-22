package context.visuals.gui.renderer;

import common.math.Matrix4f;
import context.visuals.colour.Colour;
import context.visuals.gui.TexturedGui;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.renderer.TextureRenderer;

public class TexturedGuiRenderer extends GuiRenderer<TexturedGui> {

	protected TextureRenderer textureRenderer;
	protected ShaderProgram shaderProgram;
	protected VertexArrayObject vao;

	public TexturedGuiRenderer(TextureRenderer textureRenderer, ShaderProgram guiShaderProgram, VertexArrayObject vao) {
		this.textureRenderer = textureRenderer;
		this.shaderProgram = guiShaderProgram;
		this.vao = vao;
	}

	@Override
	public void render(TexturedGui gui, Matrix4f matrix4f, float parentX, float parentY, float parentWidth, float parentHeight) {
		float x = gui.getPosX().calculateValue(parentX, parentX + parentWidth);
		float y = gui.getPosY().calculateValue(parentY, parentY + parentHeight);
		float width = gui.getWidth().calculateValue(parentX, parentX + parentWidth);
		float height = gui.getHeight().calculateValue(parentY, parentY + parentHeight);
		matrix4f = matrix4f.clone().translate(x, y).scale(width, height);

		shaderProgram.bind();
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setVec4("fill", Colour.toNormalizedVector(gui.getBackgroundColour()));
		vao.display();
		if (gui.getTexture() != null) {
			textureRenderer.render(vao, gui.getTexture(), matrix4f);
		}
	}

}
