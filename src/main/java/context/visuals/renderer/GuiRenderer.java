package context.visuals.renderer;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector3f;
import context.visuals.colour.Colour;
import context.visuals.gui.Gui;
import context.visuals.gui.RootGui;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.renderer.shader.ShaderProgram;

public class GuiRenderer extends GameRenderer {

	private ShaderProgram shaderProgram;
	private VertexArrayObject guiVao;

	public GuiRenderer(ShaderProgram shaderProgram, VertexArrayObject guiVao) {
		this.shaderProgram = shaderProgram;
		this.guiVao = guiVao;
	}

	public void render(RootGui root) {
		shaderProgram.bind();
		recursiveRender(root);
	}

	private void recursiveRender(RootGui root) {
		for (Gui child : root.getChildren()) {
			doRecursiveRender(child, root, 0, 0, root.getWidth(), root.getHeight());
		}
	}

	private void doRecursiveRender(Gui gui, RootGui root, float posX, float posY, float width, float height) {
		float x = gui.getPosXConstraint().calculateValue(posX, posX + width);
		float y = gui.getPosYConstraint().calculateValue(posY, posY + height);
		float w = gui.getWidthConstraint().calculateValue(posX, posX + width);
		float h = gui.getHeightConstraint().calculateValue(posY, posY + height);

		Matrix4f matrix4f = new Matrix4f();
		int rootWidth = root.getWidth();
		int rootHeight = root.getHeight();
		matrix4f.translate(new Vector2f(-1, 1));
		matrix4f.scale(new Vector3f(2 * w / rootWidth, -2 * h / rootHeight, 1));
		matrix4f.translate(new Vector2f(x / w, y / h));

		shaderProgram.setMat4("transform", matrix4f);
		shaderProgram.setVec4("fill", Colour.toNormalizedVector(gui.getBackgroundColour()));
		guiVao.display();

		for (Gui child : gui.getChildren()) {
			if (child.isEnabled()) {
				doRecursiveRender(child, root, x, y, w, h);
			}
		}
	}

}
