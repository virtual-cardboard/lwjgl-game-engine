package common.timestep;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import context.GameContext;
import context.GameContextWrapper;
import context.visuals.renderer.GameRenderer;
import engine.GameWindow;

public class WindowFrameUpdateTimer extends TimestepTimer {

	private GameContextWrapper wrapper;

	private GameWindow window;
	private long windowId;
	GameRenderer gameRenderer;

	public WindowFrameUpdateTimer(GameWindow window, GameContextWrapper wrapper) {
		super(30);
		this.window = window;
		this.wrapper = wrapper;
	}

	@Override
	protected void doUpdate() {
		GameContext context = wrapper.getContext();
		glfwPollEvents();
		context.getInput().handleAll();
		context.getVisuals().render(gameRenderer);
		glfwSwapBuffers(windowId);
	}

	@Override
	protected boolean endCondition() {
		return glfwWindowShouldClose(windowId);
	}

	@Override
	protected void startActions() {
		window.createDisplay();
		window.attachCallbacks();
		gameRenderer = new GameRenderer();
		this.windowId = window.getWindowId();
	}

	@Override
	protected void endActions() {
		window.destroy();
	}

}
