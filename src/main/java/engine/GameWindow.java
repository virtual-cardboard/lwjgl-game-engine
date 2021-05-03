package engine;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import common.coordinates.IntCoordinates;
import context.GameContext;
import context.GameContextWrapper;

/**
 * 
 * @author Lunkle
 *
 */
public class GameWindow implements Runnable {

	private GameContextWrapper wrapper;

	private long windowId;
	private String windowTitle;

	private final int DEFAULT_WINDOW_WIDTH = 1280;
	private final int DEFAULT_WINDOW_HEIGHT = 720;
	private IntCoordinates windowDimensions = new IntCoordinates(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);

	private final boolean FULLSCREEN = false;
	private final boolean RESIZABLE = true;

	public GameWindow(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	@Override
	public void run() {
		createDisplay();
		while (!GLFW.glfwWindowShouldClose(windowId)) {
			GameContext bundle = wrapper.getContext();
			GLFW.glfwPollEvents();
			bundle.getInput().handleAll();
			bundle.getVisuals().render();
			GLFW.glfwSwapBuffers(windowId);
		}
		cleanUp();
	}

	private void createDisplay() {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!GLFW.glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		GLFW.glfwDefaultWindowHints(); // optional, the current window hints are already the default
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE); // the window will stay hidden after creation
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, RESIZABLE ? GL11.GL_TRUE : GL11.GL_FALSE); // the window will be resizable
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()); // Get the resolution of the primary monitor
		if (FULLSCREEN)
			windowDimensions.set(vidmode.width(), vidmode.height());
		windowId = GLFW.glfwCreateWindow(windowDimensions.x, windowDimensions.y, windowTitle, MemoryUtil.NULL, MemoryUtil.NULL); // Create the window
		if (windowId == MemoryUtil.NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		GLFW.glfwSetWindowPos(windowId, (vidmode.width() - windowDimensions.x) / 2, (vidmode.height() - windowDimensions.y) / 2); // Center our window
		GLFW.glfwMakeContextCurrent(windowId); // Make the OpenGL context current
		GL.createCapabilities();
		GLFW.glfwSwapInterval(1); // Enable v-sync
		GLFW.glfwShowWindow(windowId); // Make the window visible
		GL11.glViewport(0, 0, windowDimensions.x, windowDimensions.y);
	}

	public void cleanUp() {
//		VAO.cleanUp();
//		EBO.cleanUp();
//		VBO.cleanUp();
//		Texture.cleanUp();
		Callbacks.glfwFreeCallbacks(windowId); // Release window callbacks
		GLFW.glfwDestroyWindow(windowId); // Release window
		GLFW.glfwTerminate(); // Terminate GLFW
		GLFW.glfwSetErrorCallback(null).free(); // Release the GLFWerrorfun
	}

	public void setBundleWrapper(GameContextWrapper wrapper) {
		this.wrapper = wrapper;
	}

	public IntCoordinates getWindowDimensions() {
		return windowDimensions;
	}

	public void setWindowDimensions(IntCoordinates windowDimensions) {
		this.windowDimensions = windowDimensions;
	}

}
