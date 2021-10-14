package context.visuals.gui.renderer;

import common.math.Matrix4f;
import context.GameContext;
import context.visuals.colour.Colour;
import context.visuals.gui.Gui;
import context.visuals.lwjgl.ShaderProgram;

public final class DefaultGuiRenderer extends GuiRenderer<Gui> {

	protected ShaderProgram shaderProgram;

	public DefaultGuiRenderer(GameContext context) {
		super(context);
		this.shaderProgram = resourcePack().getShaderProgram("rectangle");
	}

	@Override
	public void render(Gui gui, Matrix4f matrix4f, float parentX, float parentY, float parentWidth, float parentHeight) {
		float x = gui.getPosX().calculateValue(parentX, parentX + parentWidth);
		float y = gui.getPosY().calculateValue(parentY, parentY + parentHeight);
		float width = gui.getWidth().calculateValue(parentX, parentX + parentWidth);
		float height = gui.getHeight().calculateValue(parentY, parentY + parentHeight);

		shaderProgram.bind();
		shaderProgram.setMat4("matrix4f", matrix4f.clone().translate(x, y).scale(width, height));
		shaderProgram.setVec4("fill", Colour.toNormalizedVector(gui.getBackgroundColour()));
		resourcePack().rectangleVAO().display();
	}

}
