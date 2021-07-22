package engine;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.Queue;

import org.lwjgl.glfw.GLFW;
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

	private static final int DEFAULT_WINDOW_WIDTH = 1280;
	private static final int DEFAULT_WINDOW_HEIGHT = 720;
	private static final boolean FULLSCREEN = false;
	private static final boolean RESIZABLE = true;

	private long windowId;
	private String windowTitle;
	private Queue<GameInputEvent> inputEventBuffer;

	public GameWindow(String windowTitle, Queue<GameInputEvent> inputEventBuffer) {
		this.windowTitle = windowTitle;
		this.inputEventBuffer = inputEventBuffer;
	}

	public void createDisplay() {
		IntCoordinates windowDimensions = new IntCoordinates(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		createPrint(System.err).set();
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, RESIZABLE ? GL_TRUE : GL_FALSE); // the window will be resizable
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
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
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glfwSwapInterval(1); // Enable v-sync
		glfwShowWindow(windowId); // Make the window visible
		glViewport(0, 0, windowDimensions.x, windowDimensions.y);
	}

	public void attachCallbacks() {
		GLFW.glfwSetKeyCallback(windowId, new KeyCallback(inputEventBuffer));
		GLFW.glfwSetMouseButtonCallback(windowId, new MouseButtonCallback(inputEventBuffer));
		GLFW.glfwSetScrollCallback(windowId, new MouseScrollCallback(inputEventBuffer));
		GLFW.glfwSetCursorPosCallback(windowId, new MouseMovementCallback(inputEventBuffer));
		GLFW.glfwSetFramebufferSizeCallback(windowId, new WindowResizeCallback(inputEventBuffer));
	}

	public void destroy() {
		glfwFreeCallbacks(windowId); // Release callbacks
		glfwDestroyWindow(windowId); // Release window
		glfwTerminate(); // Terminate GLFW
		glfwSetErrorCallback(null).free(); // Release the error callback
	}

	public long getWindowId() {
		return windowId;
	}

}
