package context.visuals.renderer.rectrenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import context.data.gui.GUI;
import context.visuals.vao.RectangleMeshData;
import context.visuals.vao.VAO;

public class GUIRenderer {

	private static final int QUAD_VERTEX_COUNT = 4;

	private GUIShader shader;
	private VAO guiVAO;

	public GUIRenderer() {
		this.shader = new GUIShader();
		guiVAO = new VAO();
		RectangleMeshData quadData = new RectangleMeshData();
		guiVAO.interpret(quadData); // Interpreting the raw quad data
	}

	public void render(float x, float y, float width, float height, float r, float g, float b) {
		shader.start();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		guiVAO.bindVAO();
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		for (GUI gui : guis) {
			gui.activateTextures();
			shader.loadModelMatrix(gui.getTransformationMatrix());
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, QUAD_VERTEX_COUNT);
		}
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		guiVAO.unbindVAO();
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}
}
