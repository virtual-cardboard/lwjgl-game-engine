package engine;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

import common.loader.Loader;
import common.timestep.GameLogicTimer;
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
	 */
	public void run() {
		Queue<GameInputEvent> inputBuffer = createInputBuffer();
		TimeAccumulator accumulator = createTimeAccumulator();
		CountDownLatch windowCountDownLatch = createWindowFrameCountdownLatch();
		WindowFrameUpdater frameUpdater = createWindowFrameUpdater(inputBuffer, windowCountDownLatch);
		Loader loader = createLoader();

		DatagramSocket socket = createSocket();
		Queue<PacketReceivedInputEvent> networkReceiveBuffer = new ArrayBlockingQueue<>(10); // Please confirm if thread safety is needed
		UDPReceiver receiver = createUDPReceiver(socket, networkReceiveBuffer);
		Queue<PacketModel> networkSendBuffer = new ArrayBlockingQueue<>(10); // Please confirm if thread safety is needed
		UDPSender sender = createUDPSender(socket, networkSendBuffer);
		createUDPReceiverAndSenderThreads(receiver, sender);
		GameContextWrapper wrapper = createWrapper(inputBuffer, accumulator, frameUpdater, loader, socket, networkReceiveBuffer, networkSendBuffer);
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
		wrapper.context().init(inputBuffer, networkReceiveBuffer);
		return wrapper;
	}

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
	 * The rendering thr
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
			GameInputHandlerRunnable gameInputHandler = new GameInputHandlerRunnable(wrapper);
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
			Thread senderThread = new Thread(sender);
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

	private WindowFrameUpdater createWindowFrameUpdater(Queue<GameInputEvent> inputBuffer, CountDownLatch windowCountDownLatch) {
		WindowFrameUpdater frameUpdater = null;
		if (rendering) {
			print("Creating window.");
			GameWindow window = new GameWindow(windowTitle, inputBuffer);
			print("Binding dependencies in context wrapper.");
			frameUpdater = new WindowFrameUpdater(window, windowCountDownLatch);
		}
		return frameUpdater;
	}

	private CountDownLatch createWindowFrameCountdownLatch() {
		CountDownLatch windowCountDownLatch = null;
		if (rendering) {
			print("Creating frame updater and window count down latch.");
			windowCountDownLatch = new CountDownLatch(1);
		}
		return windowCountDownLatch;
	}

	private Loader createLoader() {
		Loader loader = null;
		if (loading) {
			print("Creating loader");
			loader = new Loader();
			Thread loaderThread = new Thread(loader);
			loaderThread.setName("loaderThread");
			loaderThread.setDaemon(true);
			print("Starting loader thread,");
			loaderThread.start();
		}
		return loader;
	}

	private void print(String s) {
		if (printProgress) {
			System.out.println(s);
		}
	}

}
