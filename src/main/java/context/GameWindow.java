package context;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.GL_DEBUG_OUTPUT;
import static org.lwjgl.opengl.GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS;
import static org.lwjgl.opengl.GLUtil.setupDebugMessageCallback;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.Queue;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.Callback;

import common.math.Vector2i;
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

	/**
	 * Whether or not to set a debug message callback. Turn this off for better
	 * performance.
	 */
	private static final boolean DEBUG = true;

	private boolean resizable = true;
	private long windowId = NULL;
	private String windowTitle;
	private Queue<GameInputEvent> inputEventBuffer;
	private Vector2i windowDimensions;
	private long sharedContextWindowHandle;

	private GLContext glContext;

	private Callback debugMessageCallback;
	private boolean fullScreen;

	public GameWindow(String windowTitle, GLContext glContext, Queue<GameInputEvent> inputEventBuffer, boolean resizable, int width, int height,
			boolean fullScreen) {
		this(windowTitle, glContext, inputEventBuffer, resizable, new Vector2i(width, height), fullScreen);
	}

	public GameWindow(String windowTitle, GLContext glContext, Queue<GameInputEvent> inputEventBuffer, boolean resizable, Vector2i windowDimensions,
			boolean fullScreen) {
		this.windowTitle = windowTitle;
		this.glContext = glContext;
		this.inputEventBuffer = inputEventBuffer;
		this.resizable = resizable;
		this.windowDimensions = windowDimensions;
		this.fullScreen = fullScreen;
	}

	public void createDisplay() {
		glfwSetErrorCallback(createPrint(System.err).set());
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
//		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GL_TRUE : GL_FALSE); // the window will be resizable
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3); // Use GLFW version 3.3
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		if (DEBUG) {
			glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE);
		}
		long primaryMonitor = glfwGetPrimaryMonitor();
		GLFWVidMode vidmode = glfwGetVideoMode(primaryMonitor); // Get the resolution of the primary monitor
		if (fullScreen)
			windowDimensions = new Vector2i(vidmode.width(), vidmode.height());
		windowId = glfwCreateWindow(windowDimensions.x, windowDimensions.y, windowTitle, fullScreen ? primaryMonitor : NULL, NULL); // Create the window
		glContext.setWindowDim(windowDimensions);
		if (windowId == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		glfwSetWindowPos(windowId, (vidmode.width() - windowDimensions.x) / 2, (vidmode.height() - windowDimensions.y) / 2); // Center the window
		glfwMakeContextCurrent(windowId); // Make the OpenGL context current
		createCapabilities();
		glEnable(GL_BLEND);
		if (DEBUG) {
			glEnable(GL_DEBUG_OUTPUT);
			glEnable(GL_DEBUG_OUTPUT_SYNCHRONOUS);
		}
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glfwSwapInterval(1); // Enable v-sync
		glfwShowWindow(windowId); // Make the window visible
		glViewport(0, 0, windowDimensions.x, windowDimensions.y);
	}

	public void createSharedContextWindow() {
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		sharedContextWindowHandle = glfwCreateWindow(1, 1, "", NULL, windowId);
		if (sharedContextWindowHandle == NULL) {
			throw new RuntimeException("Could not create shared context.");
		}
	}

	public void attachCallbacks() {
		if (DEBUG) {
			debugMessageCallback = setupDebugMessageCallback();
		}
		glfwSetKeyCallback(windowId, new KeyCallback(inputEventBuffer));
		glfwSetMouseButtonCallback(windowId, new MouseButtonCallback(inputEventBuffer));
		glfwSetScrollCallback(windowId, new MouseScrollCallback(inputEventBuffer));
		glfwSetCursorPosCallback(windowId, new MouseMovementCallback(inputEventBuffer));
		glfwSetFramebufferSizeCallback(windowId, new WindowResizeCallback(glContext, inputEventBuffer));
	}

	public void destroy() {
		if (DEBUG) {
			debugMessageCallback.close();
		}
		glfwFreeCallbacks(windowId); // Release callbacks
		glfwDestroyWindow(windowId); // Release window
		glfwTerminate(); // Terminate GLFW
		glfwSetErrorCallback(null).free(); // Release the error callback
	}

	public long windowId() {
		return windowId;
	}

	public long getSharedContextWindowHandle() {
		return sharedContextWindowHandle;
	}

}
