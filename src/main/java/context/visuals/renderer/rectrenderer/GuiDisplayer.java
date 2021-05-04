package context.visuals.renderer.rectrenderer;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

import context.visuals.gui.Gui;
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

	public void render(Gui root) {
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

	private void recursiveRender(Gui gui) {
//		gui.activateTextures();
//		shader.loadModelMatrix(gui.getTransformationMatrix());
		glDrawArrays(GL_TRIANGLE_STRIP, 0, QUAD_VERTEX_COUNT);
		for (Gui child : gui.getChildren()) {
			recursiveRender(child);
		}
	}

	public void cleanUp() {
		shader.cleanUp();
	}

}
