package engine;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import common.timestep.GameLogicTimer;
import common.timestep.WindowFrameUpdateTimer;
import context.GameContext;
import context.GameContextWrapper;
import context.input.event.GameInputEvent;
import context.logic.TimeAccumulator;

/**
 * The object that begins a game. Initializes everything required to start a
 * game, and puts everything in motion.
 * 
 * The enable() function should be called to do this.
 * 
 * @author Lunkle, Jay
 *
 */
public final class GameEnabler {

	private final String windowTitle;
	private final GameContext context;

	private boolean printProgress = true;

	/**
	 * The constructor takes in and saves a window, an input decorator, and a game
	 * wrapper.
	 * 
	 * @param window
	 * @param inputDecorator
	 * @param wrapper
	 */
	public GameEnabler(String windowTitle, GameContext context) {
		this.windowTitle = windowTitle;
		this.context = context;
	}

	public GameEnabler(String windowTitle, GameContext context, boolean printProgress) {
		this(windowTitle, context);
		this.printProgress = printProgress;
	}

	/**
	 * Initializes everything required to start a game, and puts everything in
	 * motion.
	 */
	public void enable() {
		print("Creating input buffer.");
		Queue<GameInputEvent> inputBuffer = new PriorityQueue<>();
		print("Creating time accumulator.");
		TimeAccumulator accumulator = new TimeAccumulator();
		print("Binding dependencies in context wrapper.");
		GameContextWrapper wrapper = new GameContextWrapper(inputBuffer, accumulator, context);

		print("Creating window.");
		GameWindow window = new GameWindow(windowTitle, inputBuffer);

		print("Creating frame updater and window count down latch.");
		CountDownLatch windowCountDownLatch = new CountDownLatch(1);
		WindowFrameUpdateTimer frameUpdater = new WindowFrameUpdateTimer(window, wrapper, windowCountDownLatch);

		print("Creating rendering and updating threads.");
		Thread renderingThread = new Thread(frameUpdater);
		renderingThread.setName("renderingThread");

		print("Creating logic timer.");
		GameLogicTimer logicTimer = new GameLogicTimer(wrapper, accumulator);
		Thread gameLogicThread = new Thread(logicTimer);
		gameLogicThread.setName("gameLogicThread");
		gameLogicThread.setDaemon(true);

		print("Starting rendering thread.");
		renderingThread.start();
		print("Starting update thread.");
		gameLogicThread.start();

		try {
			System.out.println("Waiting for window initialization");
			windowCountDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Window initialization finished");
		print("Initializing context parts");
		wrapper.getContext().init(inputBuffer);

	}

	private void print(String s) {
		if (printProgress) {
			System.out.println(s);
		}
	}

}
