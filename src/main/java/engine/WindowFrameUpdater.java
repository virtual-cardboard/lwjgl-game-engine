package engine;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import context.GameContext;
import context.GameContextWrapper;

public class WindowFrameUpdater implements Runnable {

	private GameContextWrapper wrapper;
	private GameWindow window;

	public WindowFrameUpdater(GameWindow window, GameContextWrapper wrapper) {
		this.window = window;
		this.wrapper = wrapper;
	}

	@Override
	public void run() {
		window.createDisplay();
		window.attachCallbacks();
		long windowId = window.getWindowId();
		while (!glfwWindowShouldClose(windowId)) {
			System.out.println("hi");
			GameContext context = wrapper.getContext();
			glfwPollEvents();
			context.getInput().handleAll();
			context.getVisuals().render();
			glfwSwapBuffers(windowId);
		}
		window.destroy();
	}
}
