package context.input.lwjglcallbacks;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL11;

import common.coordinates.IntCoordinates;
import engine.GameWindow;

public class ResizeCallback extends GLFWFramebufferSizeCallback {

	private GameWindow window;

	public ResizeCallback(GameWindow window) {
		this.window = window;
	}

	@Override
	public void invoke(long windowID, int width, int height) {
		if (width > 0 && height > 0) {
			window.setWindowDimensions(new IntCoordinates(width, height));
			GL11.glViewport(0, 0, width, height);
		}
	}

}
