package context.visuals.renderer.rectrenderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector3f;
import context.visuals.gui.Gui;
import context.visuals.gui.RootGui;
import context.visuals.lwjgl.RectangleMeshData;
import context.visuals.lwjgl.VAO;

public class GuiDisplayer {

	private static final int QUAD_VERTEX_COUNT = 4;

	private GuiShader shader;
	private VAO guiVAO;

	public GuiDisplayer() {
		this.shader = new GuiShader();
		guiVAO = new VAO();
		guiVAO.interpret(new RectangleMeshData());
	}

	public void render(RootGui root) {
		shader.start();
		glDisable(GL_DEPTH_TEST);
		guiVAO.bindVAO();
		glEnableVertexAttribArray(0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		recursiveRender(root);
		glDisable(GL_BLEND);
		glDisableVertexAttribArray(0);
		guiVAO.unbindVAO();
		shader.stop();
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

		System.out.println(matrix4f);
		matrix4f.translate(new Vector2f(-1, 1));
		System.out.println("Translated by " + new Vector2f(-1, 1));
		System.out.println(matrix4f);
		matrix4f.scale(new Vector3f(w / root.getWidth(), -h / root.getHeight(), 1));
		System.out.println("Scaled by " + new Vector3f(w / root.getWidth(), -h / root.getHeight(), 1));
		System.out.println(matrix4f + "================");
//		matrix4f.translate(new Vector2f(x, y));
//		System.out.println("Translated by " + new Vector2f(x, y) + '\n');
//		int backgroundColour = gui.getBackgroundColour();
		glDrawArrays(GL_TRIANGLE_STRIP, 0, QUAD_VERTEX_COUNT);
		shader.loadModelMatrix(matrix4f);
		for (Gui child : gui.getChildren()) {
			doRecursiveRender(child, root, x, y, w, h);
		}
	}

	public void cleanUp() {
		shader.cleanUp();
	}

}
