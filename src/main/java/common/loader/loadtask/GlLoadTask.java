package common.loader.loadtask;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public abstract class GlLoadTask extends LoadTask {

	private ExecutorService openGlExecutorService;

	public GlLoadTask() {
	}

	public GlLoadTask(CountDownLatch countDownLatch) {
		super(countDownLatch);
	}

	@Override
	public final void load() throws IOException {
		loadNonOpenGl();
		openGlExecutorService.execute(this::loadOpenGl);
		openGlExecutorService.execute(countDownLatch()::countDown);
	}

	public abstract void loadNonOpenGl() throws IOException;

	public abstract void loadOpenGl();

	public final void setOpenGlExecutorService(ExecutorService executorService) {
		this.openGlExecutorService = executorService;
	}

}
