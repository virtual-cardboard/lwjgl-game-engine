package engine;

import java.util.PriorityQueue;
import java.util.Queue;

import context.GameContextWrapper;
import context.input.event.GameInputEvent;
import context.logic.GameLogicTimer;
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
public class GameEnabler {

	private String windowTitle;
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
	public GameEnabler(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	public GameEnabler(String windowTitle, boolean printProgress) {
		this(windowTitle);
		this.printProgress = printProgress;
	}

	/**
	 * Initializes everything required to start a game, and puts everything in
	 * motion.
	 */
	public void enable() {
		print("Creating game input buffer.");
		Queue<GameInputEvent> inputBuffer = new PriorityQueue<>();

		print("Creating game window.");
		GameWindow window = new GameWindow(windowTitle, inputBuffer);

		print("Creating game renderer.");
		GameRenderer renderer = new GameRenderer();
		print("Creating game logic timer.");
		GameLogicTimer logicTimer = new GameLogicTimer();

		print("Binding dependencies in game context wrapper.");
		GameContextWrapper wrapper = new GameContextWrapper(renderer, inputBuffer, logicTimer);

		window.setContextWrapper(wrapper);

		print("Creating rendering and updating threads.");
		Thread renderingThread = new Thread(window);
		Thread gameLogicThread = new Thread(logicTimer);

		print("Initializing bundle parts");
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
