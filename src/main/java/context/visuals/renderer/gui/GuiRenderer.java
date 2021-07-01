package context.visuals.renderer.gui;

import static org.lwjgl.opengl.GL11.*;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector3f;
import context.visuals.colour.Colour;
import context.visuals.gui.Gui;
import context.visuals.gui.RootGui;
import context.visuals.lwjgl.RectangleMeshData;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.renderer.GameRenderer;
import context.visuals.renderer.shader.FragmentShader;
import context.visuals.renderer.shader.ShaderProgram;
import context.visuals.renderer.shader.VertexShader;

public class GuiRenderer extends GameRenderer {

	private ShaderProgram shaderProgram;
	private VertexArrayObject guiVAO;

	public GuiRenderer() {
		this.shaderProgram = new ShaderProgram();
		shaderProgram.attachShader(new VertexShader("/shaders/guiVertexShader.vs"));
		shaderProgram.attachShader(new FragmentShader("/shaders/guiFragmentShader.fs"));
		shaderProgram.link();
		guiVAO = RectangleMeshData.generateRectangle();
	}

	public void render(RootGui root) {
		shaderProgram.bind();
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		recursiveRender(root);
		glDisable(GL_BLEND);
	}

	private void recursiveRender(RootGui root) {
		for (Gui child : root.getChildren()) {
			doRecursiveRender(child, root, 0, 0, root.getWidth(), root.getHeight());
		}
	}

	private void doRecursiveRender(Gui gui, RootGui root, float posX, float posY, float width, float height) {
		Matrix4f matrix4f = new Matrix4f();
		float x = gui.getPosXConstraint().calculateValue(posX, posX + width);
		float y = gui.getPosYConstraint().calculateValue(posY, posY + height);
		float w = gui.getWidthConstraint().calculateValue(posX, posX + width);
		float h = gui.getHeightConstraint().calculateValue(posY, posY + height);

		int rootWidth = root.getWidth();
		int rootHeight = root.getHeight();
		matrix4f.translate(new Vector2f(-1, 1));
		matrix4f.scale(new Vector3f(2 * w / rootWidth, -2 * h / rootHeight, 1));
		matrix4f.translate(new Vector2f(x / w, y / h));

		shaderProgram.setMat4("modelMatrix", matrix4f);
		shaderProgram.setVec4("fill", Colour.toNormalizedVector(gui.getBackgroundColour()));
		guiVAO.display();
		for (Gui child : gui.getChildren()) {
			doRecursiveRender(child, root, x, y, w, h);
		}
	}

}
