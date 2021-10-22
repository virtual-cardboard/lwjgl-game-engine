package common.timestep;

import static context.visuals.builtin.ColourFragmentShader.createColourFragmentShader;
import static context.visuals.builtin.IdentityVertexShader.createIdentityVertexShader;
import static context.visuals.builtin.RectangleVertexArrayObject.createRectangleVAO;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.util.concurrent.CountDownLatch;

import common.loader.loadtask.ShaderProgramLoadTask;
import context.GameContext;
import context.GameContextWrapper;
import context.GameWindow;
import context.visuals.builtin.ColourFragmentShader;
import context.visuals.builtin.IdentityVertexShader;
import context.visuals.lwjgl.ShaderProgram;

public final class WindowFrameUpdater extends TimestepTimer {

	private GameContextWrapper wrapper;
	private GameWindow window;
	private long windowId;

	private CountDownLatch windowCountDownLatch;
	private CountDownLatch contextCountDownLatch;

	public WindowFrameUpdater(GameWindow window, CountDownLatch windowCountDownLatch, CountDownLatch contextCountDownLatch) {
		super(60);
		this.window = window;
		this.windowCountDownLatch = windowCountDownLatch;
		this.contextCountDownLatch = contextCountDownLatch;
	}

	@Override
	protected void update() {
		GameContext context = wrapper.context();
		glfwPollEvents();
		// Update the root GUI
		int[] width = new int[1];
		int[] height = new int[1];
		glfwGetWindowSize(windowId, width, height);
		context.visuals().getRootGui().setDimensions(width[0], height[0]);
		context.input().handleAll();
		context.visuals().render();
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
		window.createSharedContextWindow();
		windowCountDownLatch.countDown();
		this.windowId = window.windowId();
		try {
			contextCountDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		loadBuiltIn();
	}

	private void loadBuiltIn() {
		wrapper.resourcePack().init(createRectangleVAO(wrapper.glContext()));
		IdentityVertexShader identityVertexShader = createIdentityVertexShader();
		ColourFragmentShader colourFragmentShader = createColourFragmentShader();
		try {
			ShaderProgram defaultShaderProgram = new ShaderProgramLoadTask(identityVertexShader, colourFragmentShader).call();
			wrapper.resourcePack().putShaderProgram("default", defaultShaderProgram);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Destroys the window.
	 */
	@Override
	protected void endActions() {
		window.destroy();
		wrapper.terminate();
	}

	public void setWrapper(GameContextWrapper wrapper) {
		this.wrapper = wrapper;
	}

	public CountDownLatch getWindowCountDownLatch() {
		return windowCountDownLatch;
	}

	public GameWindow window() {
		return window;
	}

}
