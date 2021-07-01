package common.timestep;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.util.concurrent.CountDownLatch;

import context.GameContext;
import context.GameContextWrapper;
import engine.GameWindow;

public class WindowFrameUpdateTimer extends TimestepTimer {

	private GameContextWrapper wrapper;

	private GameWindow window;
	private long windowId;

	private CountDownLatch windowCountDownLatch;

	public WindowFrameUpdateTimer(GameWindow window, GameContextWrapper wrapper, CountDownLatch windowCountDownLatch) {
		super(30);
		this.window = window;
		this.wrapper = wrapper;
		this.windowCountDownLatch = windowCountDownLatch;
	}

	@Override
	protected void doUpdate() {
		GameContext context = wrapper.getContext();
		glfwPollEvents();
		context.getInput().handleAll();
		context.getVisuals().render();
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
		windowCountDownLatch.countDown();
		System.out.println("Created display++++!+!+!+!++");
		this.windowId = window.getWindowId();
	}

	@Override
	protected void endActions() {
		window.destroy();
	}

}
