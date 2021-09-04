package common.timestep;

import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import common.loader.linktask.LinkTask;
import context.GameContext;
import context.GameContextWrapper;
import context.GameWindow;

public final class WindowFrameUpdateTimer extends TimestepTimer {

	private GameContextWrapper wrapper;
	private GameWindow window;
	private long windowId;

	private CountDownLatch windowCountDownLatch;

	/**
	 * Queue containing all {@link LinkTask}s waiting to be run.
	 */
	private Queue<LinkTask> linkTasks;

	public WindowFrameUpdateTimer(GameWindow window, CountDownLatch windowCountDownLatch) {
		super(60);
		this.window = window;
		this.windowCountDownLatch = windowCountDownLatch;
		linkTasks = new LinkedBlockingQueue<>();
	}

	@Override
	protected void doUpdate() {
		GameContext context = wrapper.getContext();
		glfwPollEvents();
		// Update the root GUI
		int[] width = new int[1];
		int[] height = new int[1];
		glfwGetWindowSize(windowId, width, height);
		context.getVisuals().getRootGui().setDimensions(width[0], height[0]);
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
		wrapper.terminate();
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

	public void addLinkTask(LinkTask linkTask) {
		linkTasks.add(linkTask);
	}

	public void setWrapper(GameContextWrapper wrapper) {
		this.wrapper = wrapper;
	}

}
