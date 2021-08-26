package context.input;

import context.GameContextWrapper;

public class GameInputHandlerRunnable implements Runnable {

	private GameContextWrapper wrapper;
	private boolean isDone = false;

	public GameInputHandlerRunnable(GameContextWrapper wrapper) {
		this.wrapper = wrapper;
	}

	@Override
	public void run() {
		while (!isDone) {
			wrapper.getContext().getInput().handleAll();
			Thread.yield();
		}
	}

	public void terminate() {
		isDone = true;
	}

}
