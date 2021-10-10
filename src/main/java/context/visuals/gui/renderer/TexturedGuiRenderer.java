package context.visuals.gui.renderer;

import static context.visuals.defaultvao.RectangleVertexArrayObject.rectangleVAO;

import common.math.Matrix4f;
import context.visuals.colour.Colour;
import context.visuals.gui.TexturedGui;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.renderer.TextureRenderer;

public class TexturedGuiRenderer extends GuiRenderer<TexturedGui> {

	protected TextureRenderer textureRenderer;
	protected ShaderProgram shaderProgram;

	public TexturedGuiRenderer(TextureRenderer textureRenderer, ShaderProgram guiShaderProgram) {
		this.textureRenderer = textureRenderer;
		this.shaderProgram = guiShaderProgram;
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
		rectangleVAO().display();
		if (gui.getTexture() != null) {
			textureRenderer.render(gui.getTexture(), matrix4f);
		}
	}

}
