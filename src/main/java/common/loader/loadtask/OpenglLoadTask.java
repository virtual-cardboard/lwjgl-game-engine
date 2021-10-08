package common.loader.loadtask;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import common.loader.GameLoaderLinker;

public abstract class OpenglLoadTask extends LoadTask {

	private GameLoaderLinker loaderLinker;

	public OpenglLoadTask() {
	}

	public OpenglLoadTask(CountDownLatch countDownLatch) {
		super(countDownLatch);
	}

	@Override
	public final void load() throws IOException {
		loadNonOpenGl();
		loaderLinker.add(this);
	}

	public void loadNonOpenGl() throws IOException {
	};

	public abstract void loadOpengl();

	public final void setLoaderLinker(GameLoaderLinker loaderLinker) {
		this.loaderLinker = loaderLinker;
	}

}
