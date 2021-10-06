package common.timestep;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;

import context.GameContextWrapper;
import context.logic.GameLogic;
import context.logic.TimeAccumulator;

public class GameLogicTimer extends TimestepTimer {

	private final GameContextWrapper wrapper;
	private boolean isDone = false;

	public GameLogicTimer(GameContextWrapper wrapper, TimeAccumulator accumulator) {
		super(10, accumulator);
		this.wrapper = wrapper;
	}

	@Override
	protected void startActions() {
		glfwSetErrorCallback(createPrint(System.err).set());
		if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		long window2 = glfwCreateWindow(100, 100, "", MemoryUtil.NULL, wrapper.windowFrameUpdater().window().windowId());
		PointerBuffer pointBuffer = PointerBuffer.allocateDirect(100);
		while (glfwGetError(pointBuffer) != GLFW_NO_ERROR) {
			System.err.println(pointBuffer.getStringUTF8());
		}
		System.out.println("window2: " + window2);
	}

	@Override
	protected void doUpdate() {
		GameLogic gameLogic = wrapper.context().logic();
		gameLogic.update();
	}

	public void end() {
		isDone = true;
	}

	@Override
	protected boolean endCondition() {
		return isDone;
	}

}
