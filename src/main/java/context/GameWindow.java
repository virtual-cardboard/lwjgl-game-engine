package context;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.Queue;

import org.lwjgl.glfw.GLFWVidMode;

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

	private static final boolean FULLSCREEN = false;
	private static final boolean RESIZABLE = true;

	private long windowId = NULL;
	private String windowTitle;
	private Queue<GameInputEvent> inputEventBuffer;
	private Vector2i windowDimensions;
	private long sharedContextWindowHandle;

	public GameWindow(String windowTitle, Queue<GameInputEvent> inputEventBuffer, int width, int height) {
		this(windowTitle, inputEventBuffer, new Vector2i(width, height));
	}

	public GameWindow(String windowTitle, Queue<GameInputEvent> inputEventBuffer, Vector2i windowDimensions) {
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
		if (sharedContextWindowHandle == NULL) {
			throw new RuntimeException("Could not create shared context.");
		}
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
	public Vector2i getWindowDimensionsCopy() {
		return windowDimensions.copy();
	}

	public long getSharedContextWindowHandle() {
		return sharedContextWindowHandle;
	}

}
