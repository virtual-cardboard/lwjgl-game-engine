package engine;

import bundle.GameBundleWrapper;
import bundle.input.inputdecorator.GameInputDecorator;
import bundle.logic.GameLogicTimer;
import bundle.visuals.renderer.AbstractGameRenderer;

/**
 * A game kickstarter. Initializes everything required to start a game, and puts
 * everything in motion.
 * 
 * The enable() function should be called to do this.
 * 
 * @author Jay
 *
 */
public class GameEnabler {

	private GameWindow window;
	private AbstractGameRenderer renderer;
	private GameInputDecorator inputDecorator;
	private GameBundleWrapper wrapper;

	/**
	 * The constructor takes in and saves a window, a renderer, an input buffer, and
	 * a game wrapper.
	 * 
	 * @param window
	 * @param renderer
	 * @param inputDecorator
	 * @param wrapper
	 */
	public GameEnabler(GameWindow window, AbstractGameRenderer renderer, GameInputDecorator inputDecorator, GameBundleWrapper wrapper, String windowTitle) {
		this.window = window;
		this.renderer = renderer;
		this.inputDecorator = inputDecorator;
		this.wrapper = wrapper;
		window.setWindowTitle(windowTitle);
	}

	/**
	 * Initializes everything required to start a game, and puts everything in
	 * motion.
	 */
	public void enable() {
		// Attach the bundleWrapper to the engine.
		// Don't change.
		window.setBundleWrapper(wrapper);
		window.startWindow();
		wrapper.setRenderer(renderer);
		inputDecorator.setGameInputBuffer(wrapper.getInputBuffer());
		inputDecorator.setBundleWrapper(wrapper);
		wrapper.getBundle().initBundleParts();
		GameLogicTimer timer = new GameLogicTimer(wrapper);
		wrapper.setLogicTimer(timer);
		Thread gameLogicThread = new Thread(timer);
		gameLogicThread.start();
	}

}
