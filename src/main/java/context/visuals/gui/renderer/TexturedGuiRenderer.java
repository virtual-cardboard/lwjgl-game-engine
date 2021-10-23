package context.visuals.gui.renderer;

import common.math.Matrix4f;
import context.GameContext;
import context.visuals.colour.Colour;
import context.visuals.gui.TexturedGui;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.renderer.TextureRenderer;

public class TexturedGuiRenderer extends GuiRenderer<TexturedGui> {

	protected TextureRenderer textureRenderer;
	protected ShaderProgram shaderProgram;

	public TexturedGuiRenderer(GameContext context, TextureRenderer textureRenderer) {
		super(context);
		this.textureRenderer = textureRenderer;
		this.shaderProgram = resourcePack.getShaderProgram("rectangle");
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
		resourcePack.rectangleVAO().display(glContext);
		if (gui.getTexture() != null) {
			textureRenderer.render(gui.getTexture(), matrix4f);
		}
	}

}
