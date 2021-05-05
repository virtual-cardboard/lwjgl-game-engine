package engine;

import java.util.PriorityQueue;
import java.util.Queue;

import context.GameContext;
import context.GameContextWrapper;
import context.input.event.GameInputEvent;
import context.logic.GameLogicTimer;
import context.logic.TimeAccumulator;
import context.visuals.renderer.GameRenderer;

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
	 * The constructor takes in and saves a window, a renderer, an input decorator,
	 * and a game wrapper.
	 * 
	 * @param window
	 * @param renderer
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
		print("Creating renderer.");
		GameRenderer renderer = new GameRenderer();
		print("Creating input buffer.");
		Queue<GameInputEvent> inputBuffer = new PriorityQueue<>();
		print("Creating time accumulator.");
		TimeAccumulator accumulator = new TimeAccumulator();
		print("Binding dependencies in context wrapper.");
		GameContextWrapper wrapper = new GameContextWrapper(renderer, inputBuffer, accumulator, context);

		print("Creating window.");
		GameWindow window = new GameWindow(windowTitle, inputBuffer);

		print("Creating rendering and updating threads.");
		print("Creating frame updater.");
		WindowFrameUpdateTimer frameUpdater = new WindowFrameUpdateTimer(window, wrapper);
		Thread renderingThread = new Thread(frameUpdater);
		print("Creating logic timer.");
		GameLogicTimer logicTimer = new GameLogicTimer(wrapper, accumulator);
		Thread gameLogicThread = new Thread(logicTimer);

		print("Initializing context parts");
		wrapper.getContext().init(inputBuffer);

		print("Starting rendering thread.");
		renderingThread.start();
		print("Starting update thread.");
		gameLogicThread.start();
	}

	private void print(String s) {
		if (printProgress) {
			System.out.println(s);
		}
	}

}
