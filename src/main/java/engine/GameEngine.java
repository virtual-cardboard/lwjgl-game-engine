package engine;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

import context.GLContext;
import context.GameContext;
import context.GameContextWrapper;
import context.GameWindow;
import context.input.GameInput;
import context.input.GameInputHandlerRunnable;
import context.input.event.GameInputEvent;
import context.input.event.PacketReceivedInputEvent;
import context.input.networking.SocketFinder;
import context.input.networking.UDPReceiver;
import context.input.networking.UDPSender;
import context.logic.TimeAccumulator;
import engine.common.loader.GameLoader;
import engine.common.networking.packet.PacketModel;
import engine.common.timestep.AudioUpdater;
import engine.common.timestep.GameLogicTimer;
import engine.common.timestep.TimestepTimer;
import engine.common.timestep.WindowFrameUpdater;

/**
 * The object that begins a game. Initializes everything required to start a game, and puts everything in motion.
 * <p>
 * The {@link #run()} function should be called to do this.
 *
 * @author Lunkle, Jay
 */
public final class GameEngine {

	private final String windowTitle;
	private final GameContext context;

	private boolean printProgress = false;
	private boolean rendering = true;
	private boolean audio = true;
	private boolean resizable = true;
	private boolean networking = true;
	private boolean loading = true;
	private Integer port;

	private int width = 1920;
	private int height = 1012;
	private float tickRate = 10;
	private boolean fullScreen = false;

	/**
	 * Creates a {@link GameEngine} with a window title and a context.
	 *
	 * @param windowTitle the title of the window
	 * @param context     the initial context to be used
	 * @see GameContext
	 */
	public GameEngine(String windowTitle, GameContext context) {
		this(windowTitle, context, false);
	}

	public GameEngine(String windowTitle, GameContext context, boolean printProgress) {
		this.windowTitle = windowTitle;
		this.context = context;
		this.printProgress = printProgress;
	}

	/**
	 * Initializes everything required to start a game, and puts everything in motion.
	 *
	 * <p>
	 * Lunkle [Sep 30, 2021]: some of these methods need better documentation
	 */
	public void run() {
		Queue<GameInputEvent> inputBuffer = createInputBuffer();
		CountDownLatch windowCountDownLatch = createWindowFrameCountdownLatch();
		CountDownLatch audioCountDownLatch = createAudioCountdownLatch();
		CountDownLatch contextCountDownLatch = new CountDownLatch(1);

		TimeAccumulator logicAccumulator = createTimeAccumulator();
		GameLogicTimer logicTimer = createLogicThread(logicAccumulator, contextCountDownLatch);
		GLContext glContext = new GLContext();

		WindowFrameUpdater frameUpdater = createWindowFrameUpdater(glContext, inputBuffer, logicAccumulator, windowCountDownLatch, contextCountDownLatch);
		GameInputHandlerRunnable inputHandler = createInputHandler();
		createRenderingOrInputThread(frameUpdater, inputHandler);

		AudioUpdater audioUpdater = createAudioUpdater(audioCountDownLatch, contextCountDownLatch);
		createAudioThread(audioUpdater);

		DatagramSocket socket = createSocket(); // We should not be passing a raw socket to the wrapper
		ArrayBlockingQueue<PacketReceivedInputEvent> networkReceiveBuffer = new ArrayBlockingQueue<>(30); // Please confirm if thread safety is needed
		Queue<PacketModel> networkSendBuffer = new ArrayBlockingQueue<>(30); // Please confirm if thread safety is needed
		UDPReceiver receiver = createUDPReceiver(socket, networkReceiveBuffer);
		UDPSender sender = createUDPSender(socket, networkSendBuffer);
		createUDPReceiverAndSenderThreads(receiver, sender);

		waitForWindowCreation(windowCountDownLatch);
		waitForAudioInitialization(audioCountDownLatch);
		GameLoader loader = createLoader(frameUpdater, glContext);

		createWrapper(inputBuffer, logicAccumulator, frameUpdater, logicTimer, audioUpdater, inputHandler, glContext, loader, socket, networkReceiveBuffer,
				networkSendBuffer, contextCountDownLatch);

		print("Game engine now running");
	}

	private GameInputHandlerRunnable createInputHandler() {
		if (rendering) {
			return null;
		}
		return new GameInputHandlerRunnable();
	}

	private GameLogicTimer createLogicThread(TimeAccumulator accumulator, CountDownLatch contextCountDownLatch) {
		print("Creating logic timer.");
		GameLogicTimer logicTimer = new GameLogicTimer(tickRate, accumulator, contextCountDownLatch);
		Thread logicThread = new Thread(logicTimer);
		logicThread.setName("gameLogicThread");
		logicThread.setDaemon(true);
		print("Starting logic thread.");
		logicThread.start();
		return logicTimer;
	}

	private GameContextWrapper createWrapper(Queue<GameInputEvent> inputBuffer, TimeAccumulator accumulator, WindowFrameUpdater frameUpdater,
	                                         GameLogicTimer logicTimer, AudioUpdater audioUpdater, GameInputHandlerRunnable inputHandler, GLContext glContext, GameLoader loader,
	                                         DatagramSocket socket, Queue<PacketReceivedInputEvent> networkReceiveBuffer, Queue<PacketModel> networkSendBuffer,
	                                         CountDownLatch contextCountDownLatch) {
		GameContextWrapper wrapper = new GameContextWrapper(inputBuffer, networkReceiveBuffer, networkSendBuffer, accumulator, frameUpdater, logicTimer,
				audioUpdater, inputHandler, glContext, loader, socket);
		print("Initializing context parts");
		wrapper.transition(context);
		contextCountDownLatch.countDown();
		return wrapper;
	}

	/**
	 * The windowCountDownLatch was previously passed into the {@link WindowFrameUpdater} in {@link
	 * #createWindowFrameUpdater(GLContext, Queue, TimeAccumulator, CountDownLatch, CountDownLatch)}
	 * createWindowFrameUpdater}. In the WindowFrameUpdater's start actions, it creates the window and counts down the
	 * {@link CountDownLatch} when complete. This method {@link CountDownLatch#await() awaits} the completion of the
	 * window.
	 *
	 * @param windowCountDownLatch
	 */
	private void waitForWindowCreation(CountDownLatch windowCountDownLatch) {
		if (rendering) {
			try {
				print("Waiting for window initialization");
				windowCountDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			print("Window initialization finished");
		}
	}

	private void waitForAudioInitialization(CountDownLatch audioCountDownLatch) {
		if (audio) {
			try {
				print("Waiting for audio initialization");
				audioCountDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			print("Audio initialization finished");
		}
	}

	/**
	 * The rendering thread handles both rendering and input updating, so if rendering is enabled we create just the
	 * rendering thread. If rendering is disabled we have to create a specific input handling thread.
	 * <p>
	 * One of the two parameters must be {@code null}
	 *
	 * @param frameUpdater
	 * @param inputHandler
	 */
	private void createRenderingOrInputThread(WindowFrameUpdater frameUpdater, GameInputHandlerRunnable inputHandler) {
		if (rendering) {
			print("Creating rendering thread.");
			Thread renderingThread = new Thread(frameUpdater);
			renderingThread.setName("renderingThread");
			print("Starting rendering thread.");
			renderingThread.start();
		} else {
			print("Creating input handling thread.");
			Thread inputHandlingThread = new Thread(inputHandler);
			inputHandlingThread.setName("inputHandlingThread");
			inputHandlingThread.setDaemon(true);
			print("Starting input handling thread.");
			inputHandlingThread.start();
		}
	}

	private void createUDPReceiverAndSenderThreads(UDPReceiver receiver, UDPSender sender) {
		if (networking) {
			Thread receiverThread = new Thread(receiver);
			receiverThread.setDaemon(true);
			receiverThread.setName("networkReceiverThread");
			Thread senderThread = new Thread(sender);
			senderThread.setDaemon(true);
			senderThread.setName("networkSenderThread");
			receiverThread.start();
			senderThread.start();
		}
	}

	private UDPSender createUDPSender(DatagramSocket socket, Queue<PacketModel> networkSendBuffer) {
		UDPSender sender = null;
		if (networking) {
			sender = new UDPSender(socket, networkSendBuffer);
		}
		return sender;
	}

	private UDPReceiver createUDPReceiver(DatagramSocket socket, ArrayBlockingQueue<PacketReceivedInputEvent> networkReceiveBuffer) {
		UDPReceiver receiver = null;
		if (networking) {
			receiver = new UDPReceiver(socket, networkReceiveBuffer);
		}
		return receiver;
	}

	private DatagramSocket createSocket() {
		DatagramSocket socket = null;
		if (networking) {
			try {
				print("Locating free network socket");
				socket = SocketFinder.findSocket(port);
				print("Started UDP socket at " + socket.getLocalSocketAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return socket;
	}

	/**
	 * Creates a non thread-safe buffer for GameInputEvents. The input buffer is only updated in the {@link
	 * WindowFrameUpdater} thread.
	 * <p>
	 * Events are added to the inputBuffer through callbacks, which are called in the <code>WindowFrameUpdater</code>.
	 * <p>
	 * Events are polled from the inputBuffer by {@link GameInput#handleAll()}, which is also called in the
	 * <code>WindowFrameUpdater</code>.
	 *
	 * @return the input buffer
	 */
	private Queue<GameInputEvent> createInputBuffer() {
		print("Creating input buffer.");
		Queue<GameInputEvent> inputBuffer = new PriorityQueue<>();
		return inputBuffer;
	}

	private TimeAccumulator createTimeAccumulator() {
		print("Creating time accumulator.");
		TimeAccumulator accumulator = new TimeAccumulator();
		return accumulator;
	}

	/**
	 * Create the {@link TimestepTimer} that updates the window.
	 *
	 * @param inputBuffer
	 * @param logicAccumulator
	 * @param windowCountDownLatch
	 * @param contextCountDownLatch
	 * @param glContext
	 * @return
	 */
	private WindowFrameUpdater createWindowFrameUpdater(GLContext glContext, Queue<GameInputEvent> inputBuffer, TimeAccumulator logicAccumulator,
	                                                    CountDownLatch windowCountDownLatch, CountDownLatch contextCountDownLatch) {
		if (rendering) {
			print("Creating window.");
			GameWindow window = new GameWindow(windowTitle, glContext, inputBuffer, resizable, width, height, fullScreen);
			print("Binding dependencies in context wrapper.");
			return new WindowFrameUpdater(window, logicAccumulator, windowCountDownLatch, contextCountDownLatch);
		}
		return null;
	}

	private CountDownLatch createWindowFrameCountdownLatch() {
		if (rendering) {
			print("Creating frame updater and window count down latch.");
			return new CountDownLatch(1);
		}
		return null;
	}

	private CountDownLatch createAudioCountdownLatch() {
		if (audio) {
			print("Creating audio count down latch.");
			return new CountDownLatch(1);
		}
		return null;
	}

	private AudioUpdater createAudioUpdater(CountDownLatch audioCountDownLatch, CountDownLatch contextCountDownLatch) {
		if (audio) {
			print("Creating audio updater.");
			return new AudioUpdater(audioCountDownLatch, contextCountDownLatch);
		}
		return null;
	}

	private void createAudioThread(AudioUpdater audioUpdater) {
		if (audio) {
			Thread thread = new Thread(audioUpdater);
			thread.setName("audioUpdaterThread");
			print("Starting audio thread.");
			thread.start();
		}
	}

	private GameLoader createLoader(WindowFrameUpdater frameUpdater, GLContext glContext) {
		if (loading) {
			print("Creating loader");
			if (rendering) {
				return new GameLoader(frameUpdater.window().getSharedContextWindowHandle(), glContext);
			}
			return new GameLoader();
		}
		return null;
	}

	private void print(String s) {
		if (printProgress) {
			System.out.println("[Game Engine]: " + s);
		}
	}

	public GameEngine enablePrintProgress() {
		printProgress = true;
		return this;
	}

	public GameEngine windowDimensions(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public GameEngine disablePrintProgress() {
		printProgress = false;
		return this;
	}

	public GameEngine enableRendering() {
		rendering = true;
		return this;
	}

	public GameEngine disableRendering() {
		rendering = false;
		return this;
	}

	public GameEngine enableResizing() {
		resizable = true;
		return this;
	}

	public GameEngine disableResizing() {
		resizable = false;
		return this;
	}

	public GameEngine enableLoading() {
		loading = true;
		return this;
	}

	public GameEngine disableLoading() {
		loading = false;
		return this;
	}

	public GameEngine enableAudio() {
		audio = true;
		return this;
	}

	public GameEngine disableAudio() {
		audio = false;
		return this;
	}

	public GameEngine enableNetworking() {
		networking = true;
		return this;
	}

	public GameEngine enableNetworking(int port) {
		this.port = port;
		networking = true;
		return this;
	}

	public GameEngine disableNetworking() {
		networking = false;
		return this;
	}

	/**
	 * Sets the {@link GameLogicTimer}'s number of ticks per second.
	 * <p>
	 * The default rate is 10.
	 *
	 * @param tickRate The new tick rate
	 * @return The GameEngine object
	 */
	public GameEngine setTickRate(float tickRate) {
		this.tickRate = tickRate;
		return this;
	}

	public GameEngine enableFullScreen() {
		this.fullScreen = true;
		return this;
	}

	public GameEngine disableFullScreen() {
		this.fullScreen = false;
		return this;
	}

}
