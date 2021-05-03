package engine;

import context.GameContextWrapper;
import context.input.inputdecorator.GameInputDecorator;
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

	private GameWindow window;
	private GameRenderer renderer;
	private GameInputDecorator inputDecorator;
	private GameContextWrapper wrapper;

	private boolean printProgress = false;

	/**
	 * The constructor takes in and saves a window, a renderer, an input decorator,
	 * and a game wrapper.
	 * 
	 * @param window
	 * @param renderer
	 * @param inputDecorator
	 * @param wrapper
	 */
	public GameEnabler(GameWindow window, GameRenderer renderer, GameInputDecorator inputDecorator, GameContextWrapper wrapper) {
		this.window = window;
		this.renderer = renderer;
		this.inputDecorator = inputDecorator;
		this.wrapper = wrapper;
	}

	public GameEnabler(GameWindow window, GameRenderer renderer, GameInputDecorator inputDecorator, GameContextWrapper wrapper, boolean printProgress) {
		this(window, renderer, inputDecorator, wrapper);
		this.printProgress = printProgress;
	}

	/**
	 * Initializes everything required to start a game, and puts everything in
	 * motion. Returns the wrapper class that contains everything.
	 */
	public void enable() {
		print("Creating game logic timer.");
		GameLogicTimer timer = new GameLogicTimer(wrapper);
		print("Binding bundle wrapper with renderer and input decorator.");
		window.setBundleWrapper(wrapper);
		wrapper.setRenderer(renderer);
		inputDecorator.setBundleWrapper(wrapper);
		inputDecorator.setGameInputBuffer(wrapper.getInputBuffer());
		wrapper.setLogicTimer(timer);
		print("Creating rendering and updating threads.");
		Thread renderingThread = new Thread(window);
		Thread gameLogicThread = new Thread(timer);
		print("Initializing bundle parts");
		wrapper.getContext().initParts();
		print("Starting update thread.");
		gameLogicThread.start();
		print("Starting rendering thread.");
		renderingThread.start();
	}

	private void print(String s) {
		if (printProgress) {
			System.out.println(s);
		}
	}

}
