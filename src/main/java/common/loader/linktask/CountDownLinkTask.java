package common.loader.linktask;

import java.util.concurrent.CountDownLatch;

public abstract class CountDownLinkTask extends LinkTask {

	private CountDownLatch countDownLatch;

	public CountDownLinkTask() {
		this(new CountDownLatch(1));
	}

	public CountDownLinkTask(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		countDownLatch.countDown();
		super.run();
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	public long getCount() {
		return countDownLatch.getCount();
	}

	public boolean atZero() {
		return getCount() == 0L;
	}

}
