package context;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.Queue;

import org.lwjgl.glfw.GLFWVidMode;

import common.coordinates.IntCoordinates;
import context.input.event.GameInputEvent;
import context.input.lwjglcallback.KeyCallback;
import context.input.lwjglcallback.MouseButtonCallback;
import context.input.lwjglcallback.MouseMovementCallback;
import context.input.lwjglcallback.MouseScrollCallback;
import context.input.lwjglcallback.WindowResizeCallback;

/**
 * 
 * @author Lunkle
 *
 */
public class GameWindow {

	private static final int DEFAULT_WINDOW_WIDTH = 1920;
	private static final int DEFAULT_WINDOW_HEIGHT = 1012;
	private static final boolean FULLSCREEN = false;
	private static final boolean RESIZABLE = true;

	private long windowId = NULL;
	private String windowTitle;
	private Queue<GameInputEvent> inputEventBuffer;
	/**
	 * Dimensions of the window.
	 */
	private IntCoordinates windowDimensions;
	private long sharedContextWindowHandle;

	public GameWindow(String windowTitle, Queue<GameInputEvent> inputEventBuffer) {
		this(windowTitle, inputEventBuffer, new IntCoordinates(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
	}

	public GameWindow(String windowTitle, Queue<GameInputEvent> inputEventBuffer, IntCoordinates windowDimensions) {
		this.windowTitle = windowTitle;
		this.inputEventBuffer = inputEventBuffer;
		this.windowDimensions = windowDimensions;
	}

	public void createDisplay() {
		glfwSetErrorCallback(createPrint(System.err).set());
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
//		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, RESIZABLE ? GL_TRUE : GL_FALSE); // the window will be resizable
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3); // Use GLFW version 3.3
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor()); // Get the resolution of the primary monitor
		if (FULLSCREEN)
			windowDimensions.set(vidmode.width(), vidmode.height());
		windowId = glfwCreateWindow(windowDimensions.x, windowDimensions.y, windowTitle, NULL, NULL); // Create the window
		if (windowId == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		glfwSetWindowPos(windowId, (vidmode.width() - windowDimensions.x) / 2, (vidmode.height() - windowDimensions.y) / 2); // Center the window
		glfwMakeContextCurrent(windowId); // Make the OpenGL context current
		createCapabilities();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glfwSwapInterval(1); // Enable v-sync
		glfwShowWindow(windowId); // Make the window visible
		glViewport(0, 0, windowDimensions.x, windowDimensions.y);
	}

	public void createSharedContextWindow() {
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		sharedContextWindowHandle = glfwCreateWindow(1, 1, "", NULL, windowId);
	}

	public void attachCallbacks() {
		glfwSetKeyCallback(windowId, new KeyCallback(inputEventBuffer));
		glfwSetMouseButtonCallback(windowId, new MouseButtonCallback(inputEventBuffer));
		glfwSetScrollCallback(windowId, new MouseScrollCallback(inputEventBuffer));
		glfwSetCursorPosCallback(windowId, new MouseMovementCallback(inputEventBuffer));
		glfwSetFramebufferSizeCallback(windowId, new WindowResizeCallback(inputEventBuffer, windowDimensions));
	}

	public void destroy() {
		glfwFreeCallbacks(windowId); // Release callbacks
		glfwDestroyWindow(windowId); // Release window
		glfwTerminate(); // Terminate GLFW
		glfwSetErrorCallback(null).free(); // Release the error callback
	}

	public long windowId() {
		return windowId;
	}

	/**
	 * Get a copy of the window dimensions. Returning a copy instead of the actual
	 * original copy prevents the programmer from directly changing the window
	 * dimensions, which would not affect the window's actual size and mess up later
	 * get methods.
	 * 
	 * @return a copy of {@link #windowDimensions windowDimensions}
	 */
	public IntCoordinates getWindowDimensionsCopy() {
		return windowDimensions.copy();
	}

	public long getSharedContextWindowHandle() {
		return sharedContextWindowHandle;
	}

}
