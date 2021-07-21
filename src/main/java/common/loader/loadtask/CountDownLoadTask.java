package common.loader.loadtask;

import java.util.concurrent.CountDownLatch;

public abstract class CountDownLoadTask extends LoadTask {

	private CountDownLatch countDownLatch;

	public CountDownLoadTask() {
		this(new CountDownLatch(1));
	}

	public CountDownLoadTask(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public final void run() {
		super.run();
		countDownLatch.countDown();
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	public long getCount() {
		return countDownLatch.getCount();
	}

	public boolean done() {
		return getCount() == 0L;
	}

}
