package engine;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

import common.loader.Loader;
import common.timestep.GameLogicTimer;
import common.timestep.TimestepTimer;
import common.timestep.WindowFrameUpdater;
import context.GameContext;
import context.GameContextWrapper;
import context.GameWindow;
import context.input.GameInputHandlerRunnable;
import context.input.event.GameInputEvent;
import context.input.event.PacketReceivedInputEvent;
import context.input.networking.SocketFinder;
import context.input.networking.UDPReceiver;
import context.input.networking.UDPSender;
import context.input.networking.packet.PacketModel;
import context.logic.TimeAccumulator;

/**
 * The object that begins a game. Initializes everything required to start a
 * game, and puts everything in motion.
 * 
 * The {@link #run()} function should be called to do this.
 * 
 * @author Lunkle, Jay
 *
 */
public final class GameEngine {

	private final String windowTitle;
	private final GameContext context;

	private boolean printProgress = false;
	private boolean rendering = true;
	private boolean networking = true;
	private boolean loading = true;
	private Integer port;

	/**
	 * Creates a {@link GameEngine} with a window title and a context.
	 * 
	 * @param windowTitle the title of the window
	 * @param context     the initial context to be used
	 * 
	 * @see GameContext
	 */
	public GameEngine(String windowTitle, GameContext context) {
		this(windowTitle, context, true);
	}

	public GameEngine(String windowTitle, GameContext context, boolean printProgress) {
		this.windowTitle = windowTitle;
		this.context = context;
		this.printProgress = printProgress;
	}

	public GameEngine enablePrintProgress() {
		printProgress = true;
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

	public GameEngine enableLoading() {
		loading = true;
		return this;
	}

	public GameEngine disableLoading() {
		loading = false;
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
	 * Initializes everything required to start a game, and puts everything in
	 * motion.
	 * 
	 * <p>
	 * Lunkle [Sep 30, 2021]: some of these methods need better documentation
	 */
	public void run() {
		Queue<GameInputEvent> inputBuffer = createInputBuffer();
		TimeAccumulator accumulator = createTimeAccumulator();
		CountDownLatch windowCountDownLatch = createWindowFrameCountdownLatch();
		WindowFrameUpdater frameUpdater = createWindowFrameUpdater(inputBuffer, windowCountDownLatch);
		Loader loader = createLoader();

		Queue<PacketReceivedInputEvent> networkReceiveBuffer = new ArrayBlockingQueue<>(10); // Please confirm if thread safety is needed
		Queue<PacketModel> networkSendBuffer = new ArrayBlockingQueue<>(10); // Please confirm if thread safety is needed
		DatagramSocket socket = createSocket(); // We should not be passing a raw socket to the wrapper
		GameContextWrapper wrapper = createWrapper(inputBuffer, accumulator, frameUpdater, loader, socket, networkReceiveBuffer, networkSendBuffer);

		UDPReceiver receiver = createUDPReceiver(socket, networkReceiveBuffer);
		UDPSender sender = createUDPSender(socket, networkSendBuffer);
		createUDPReceiverAndSenderThreads(receiver, sender);
		createRenderingOrInputThread(frameUpdater, wrapper);
		createLogicThread(accumulator, wrapper);
		waitForWindowCreation(windowCountDownLatch);

		print("Game engine now running");
	}

	private void createLogicThread(TimeAccumulator accumulator, GameContextWrapper wrapper) {
		print("Creating logic timer.");
		GameLogicTimer logicTimer = new GameLogicTimer(wrapper, accumulator);
		Thread logicThread = new Thread(logicTimer);
		logicThread.setName("gameLogicThread");
		print("Starting logic thread.");
		logicThread.start();
	}

	private GameContextWrapper createWrapper(Queue<GameInputEvent> inputBuffer, TimeAccumulator accumulator, WindowFrameUpdater frameUpdater, Loader loader, DatagramSocket socket, Queue<PacketReceivedInputEvent> networkReceiveBuffer, Queue<PacketModel> networkSendBuffer) {
		GameContextWrapper wrapper = new GameContextWrapper(context, inputBuffer, networkReceiveBuffer, networkSendBuffer, accumulator, frameUpdater, loader, socket);
		print("Initializing context parts");
		wrapper.context().init(inputBuffer, networkReceiveBuffer, loader);
		return wrapper;
	}

	/**
	 * The windowCountDownLatch is passed into the {@link WindowFrameUpdater} in
	 * {@link #createWindowFrameUpdater(Queue, CountDownLatch)
	 * createWindowFrameUpdater}. In the WindowFrameUpdater, the
	 * {@link WindowFrameUpdater#startActions() startActions} method creates the
	 * window and counts down the {@link CountDownLatch} when complete. This method
	 * {@link CountDownLatch#await() awaits} the completion of the window.
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

	/**
	 * The rendering thread handles both rendering and input updating, so if
	 * rendering is enabled we create just the rendering thread. If rendering is
	 * disabled we have to create a specific input handling thread.
	 * 
	 * @param frameUpdater
	 * @param wrapper
	 */
	private void createRenderingOrInputThread(WindowFrameUpdater frameUpdater, GameContextWrapper wrapper) {
		if (rendering) {
			print("Creating rendering thread.");
			Thread renderingThread = new Thread(frameUpdater);
			renderingThread.setName("renderingThread");
			print("Starting rendering thread.");
			renderingThread.start();
		} else {
			print("Creating input handling thread.");
			Runnable gameInputHandler = new GameInputHandlerRunnable(wrapper);
			Thread inputHandlingThread = new Thread(gameInputHandler);
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

	private UDPReceiver createUDPReceiver(DatagramSocket socket, Queue<PacketReceivedInputEvent> networkReceiveBuffer) {
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return socket;
	}

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
	 * 
	 * Create the {@link TimestepTimer} that updates the window.
	 * 
	 * @param inputBuffer
	 * @param windowCountDownLatch
	 * @return
	 */
	private WindowFrameUpdater createWindowFrameUpdater(Queue<GameInputEvent> inputBuffer, CountDownLatch windowCountDownLatch) {
		if (rendering) {
			print("Creating window.");
			GameWindow window = new GameWindow(windowTitle, inputBuffer);
			print("Binding dependencies in context wrapper.");
			return new WindowFrameUpdater(window, windowCountDownLatch);
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

	private Loader createLoader() {
		if (loading) {
			print("Creating loader");
			return new Loader();
		}
		return null;
	}

	private void print(String s) {
		if (printProgress) {
			System.out.println(s);
		}
	}

}
