package common.timestep;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
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
		WindowFrameUpdater windowFrameUpdater = wrapper.windowFrameUpdater();
		if (windowFrameUpdater == null) return;
		try {
			windowFrameUpdater.getWindowCountDownLatch().await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long sharedContextWindowHandle = windowFrameUpdater.window().getSharedContextWindowHandle();
		if (sharedContextWindowHandle != NULL) {
			glfwMakeContextCurrent(sharedContextWindowHandle);
			createCapabilities();
			System.out.println("Yayyy shared context!!!");
			System.out.println(glGenVertexArrays());
		} else {
			System.err.println("Nooo");
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
