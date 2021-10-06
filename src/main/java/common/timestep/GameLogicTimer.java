package common.timestep;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

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
		long sharedContextWindowHandle = wrapper.windowFrameUpdater().window().getSharedContextWindowHandle();
		if (sharedContextWindowHandle != NULL) {
			glfwMakeContextCurrent(sharedContextWindowHandle);
			createCapabilities();
		}
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
