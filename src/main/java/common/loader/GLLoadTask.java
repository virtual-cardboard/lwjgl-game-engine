package common.loader;

import static org.lwjgl.opengl.GL11.glFinish;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public abstract class GLLoadTask extends LoadTask {

	private GameLinker linker;

	public GLLoadTask() {
		super();
	}

	public GLLoadTask(CountDownLatch countDownLatch) {
		super(countDownLatch);
	}

	@Override
	public final void run() {
		try {
			loadIO();
		} catch (IOException e) {
			e.printStackTrace();
		}
		linker.add(this);
	}

	final void doLoadGL() {
		loadGL();
		// Call glFinish to flush openGL buffered commands.
		glFinish();
		countDownLatch.countDown();
	}

	protected abstract void loadIO() throws IOException;

	protected abstract void loadGL();

	final void setLinker(GameLinker linker) {
		this.linker = linker;
	}

}
