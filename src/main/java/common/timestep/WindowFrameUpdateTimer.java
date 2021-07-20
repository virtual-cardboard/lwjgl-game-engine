package common.timestep;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import common.loader.linktask.LinkTask;
import context.GameContext;
import context.GameContextWrapper;
import engine.GameWindow;

public final class WindowFrameUpdateTimer extends TimestepTimer {

	private GameContextWrapper wrapper;

	private GameWindow window;
	private long windowId;

	private CountDownLatch windowCountDownLatch;

	/**
	 * Queue containing all {@link LinkTask}s waiting to be run.
	 */
	private Queue<LinkTask> linkTasks;

	public WindowFrameUpdateTimer(GameWindow window, GameContextWrapper wrapper, CountDownLatch windowCountDownLatch) {
		super(30);
		this.window = window;
		this.wrapper = wrapper;
		this.windowCountDownLatch = windowCountDownLatch;
		linkTasks = new ArrayDeque<>();
	}

	@Override
	protected void doUpdate() {
		GameContext context = wrapper.getContext();
		glfwPollEvents();
		doLinkTasks();
		context.getInput().handleAll();
		context.getVisuals().render();
		glfwSwapBuffers(windowId);
	}

	@Override
	protected boolean endCondition() {
		return glfwWindowShouldClose(windowId);
	}

	/**
	 * Creates window, attaches window callbacks, and notifies the
	 * <code>windowCountDownLatch</code>.
	 */
	@Override
	protected void startActions() {
		window.createDisplay();
		window.attachCallbacks();
		windowCountDownLatch.countDown();
		this.windowId = window.getWindowId();
	}

	/**
	 * Destroys the window.
	 */
	@Override
	protected void endActions() {
		window.destroy();
	}

	/**
	 * Polls and runs {@link LinkTask}s in the queue {@link #linkTasks linkTasks}.
	 * If <code>linkTasks</code> is empty, this function does nothing. Otherwise,
	 * this function runs one <code>LinkTask</code> guaranteed, then continues
	 * running <code>LinkTask</code>s until the next update tick arrives or until
	 * <code>linkTasks</code> becomes empty.
	 * <p>
	 * This function is run once every frame in {@link #doUpdate() doUpdate()}.
	 * </p>
	 */
	private void doLinkTasks() {
		LinkTask linkTask = linkTasks.poll();
		if (linkTask != null) {
			linkTask.run();
			// Poll ONLY IF we should not be updating. If we poll before checking for
			// !shouldUpdate(), then when shouldUpdate() returns false, the linkTask will be
			// polled yet never run.
			while (!shouldUpdate() && (linkTask = linkTasks.poll()) != null) {
				linkTask.run();
			}
		}
	}

	public Queue<LinkTask> getLinkTasks() {
		return linkTasks;
	}

}
