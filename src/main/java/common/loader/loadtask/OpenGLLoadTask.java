package common.loader.loadtask;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import common.loader.GameLoaderLinker;

public abstract class OpenGLLoadTask extends LoadTask {

	private GameLoaderLinker loaderLinker;

	public OpenGLLoadTask() {
		super();
	}

	public OpenGLLoadTask(CountDownLatch countDownLatch) {
		super(countDownLatch);
	}

	@Override
	public final void load() throws IOException {
		loadIO();
		loaderLinker.add(this);
	}

	public abstract void loadIO() throws IOException;

	public abstract void loadGL();

	public final void setLoaderLinker(GameLoaderLinker loaderLinker) {
		this.loaderLinker = loaderLinker;
	}

}
