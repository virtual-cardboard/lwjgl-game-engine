package engine;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

import common.loader.Loader;
import common.timestep.GameLogicTimer;
import common.timestep.WindowFrameUpdateTimer;
import context.GameContext;
import context.GameContextWrapper;
import context.GameWindow;
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

	public GameEngine disableNetworking() {
		networking = false;
		return this;
	}

	/**
	 * Initializes everything required to start a game, and puts everything in
	 * motion.
	 */
	public void run() {
		print("Creating input buffer.");
		Queue<GameInputEvent> inputBuffer = new PriorityQueue<>();
		print("Creating time accumulator.");
		TimeAccumulator accumulator = new TimeAccumulator();

		CountDownLatch windowCountDownLatch = null;
		WindowFrameUpdateTimer frameUpdater = null;
		if (rendering) {
			print("Creating window.");
			GameWindow window = new GameWindow(windowTitle, inputBuffer);
			print("Creating frame updater and window count down latch.");
			windowCountDownLatch = new CountDownLatch(1);
			print("Binding dependencies in context wrapper.");
			frameUpdater = new WindowFrameUpdateTimer(window, windowCountDownLatch);
		}

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

		UDPReceiver receiver = null;
		UDPSender sender = null;
		Queue<PacketModel> networkSendBuffer = null;
		Queue<PacketReceivedInputEvent> networkReceiveBuffer = new LinkedList<>();
		if (networking) {
			try {
				print("Locating free network socket");
				DatagramSocket socket = SocketFinder.findSocket();
				networkSendBuffer = new ConcurrentLinkedQueue<>();
				receiver = new UDPReceiver(socket, networkReceiveBuffer);
				sender = new UDPSender(socket, networkSendBuffer);
				Thread receiverThread = new Thread(receiver);
				Thread senderThread = new Thread(sender);
				receiverThread.start();
				senderThread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		GameContextWrapper wrapper = new GameContextWrapper(context, inputBuffer, networkReceiveBuffer, networkSendBuffer, accumulator, frameUpdater, loader);
		print("Initializing context parts");
		wrapper.getContext().init(inputBuffer, networkReceiveBuffer);

		if (rendering) {
			print("Creating rendering thread.");
			Thread renderingThread = new Thread(frameUpdater);
			renderingThread.setName("renderingThread");
			print("Starting rendering thread.");
			renderingThread.start();
		}

		print("Creating updating thread.");
		print("Creating logic timer.");
		GameLogicTimer logicTimer = new GameLogicTimer(wrapper, accumulator);
		Thread logicThread = new Thread(logicTimer);
		logicThread.setName("gameLogicThread");
		print("Starting logic thread.");
		logicThread.start();

		if (rendering) {
			try {
				print("Waiting for window initialization");
				windowCountDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			print("Window initialization finished");
		}
		print("Game engine now running");
	}

	private void print(String s) {
		if (printProgress) {
			System.out.println(s);
		}
	}

}
