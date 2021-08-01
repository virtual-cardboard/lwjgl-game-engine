package context.visuals.renderer.shader;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import common.loader.linktask.LinkTask;
import common.loader.loadtask.LoadTask;
import common.loader.loadtask.ShaderFileLoadTask;

/**
 * <p>
 * A class used to build <b>one (1)</b> shader program. Use
 * <code>ShaderProgramBuilder</code> this way:
 * </p>
 * <code>
 * // Create a shader program builder using the LoadTask queue from Loader and<br>
 * // LinkTask queue from window frame update timer<br>
 * <br>
 * ShaderProgramBuilder builder = new ShaderProgramBuilder(loadTasks, linkTasks); <br>
 * <br>
 * // Create a shader program<br>
 * <br>
 * builder.create("your/vertex/shader/file/location", "your/fragment/shader/file/location");<br>
 * <br>
 * // After a while, the shader program builder will have finished.<br>
 * <br>
 * ...<br>
 * <br>
 * ShaderProgram shaderProgram;
 * if (builder.isBuilt()) {<br>
 * &emsp;shaderProgram = builder.getShaderProgram();<br>
 * }<br>
 * </code>
 * 
 * @author Jay
 *
 */
public final class ShaderProgramBuilder {

	private final Queue<LoadTask> loadTasks;
	private final Queue<LinkTask> linkTasks;
	private ShaderProgram shaderProgram;

	/**
	 * Creates a {@link ShaderProgramBuilder} with the given {@link LoadTask} queue
	 * and {@link LinkTask} queue.
	 * 
	 * @param loadTasks the <code>LoadTask</code> queue
	 * @param linkTasks the <code>LinkTask</code> queue
	 */
	public ShaderProgramBuilder(Queue<LoadTask> loadTasks, Queue<LinkTask> linkTasks) {
		this.loadTasks = loadTasks;
		this.linkTasks = linkTasks;
	}

	/**
	 * Adds the appropriate load tasks and link tasks to start creating the vertex
	 * and fragment shaders into the queues.
	 * 
	 * @param vertexShaderFileLocation   the file path to the vertex shader's
	 *                                   location
	 * @param fragmentShaderFileLocation the file path to the fragment shader's
	 *                                   location
	 */
	public void create(String vertexShaderFileLocation, String fragmentShaderFileLocation) {
		CountDownLatch countDownLatch = new CountDownLatch(2);
		shaderProgram = new ShaderProgram();
		ShaderFileLoadTask loadVertex = new ShaderFileLoadTask(linkTasks, countDownLatch, shaderProgram,
				new Shader(ShaderType.VERTEX), vertexShaderFileLocation);
		ShaderFileLoadTask loadFragment = new ShaderFileLoadTask(linkTasks, countDownLatch, shaderProgram,
				new Shader(ShaderType.FRAGMENT), fragmentShaderFileLocation);
		loadTasks.add(loadVertex);
		loadTasks.add(loadFragment);
	}

	/**
	 * Adds the appropriate load tasks and link tasks to start creating the vertex,
	 * fragment, and geometry shaders into the queues.
	 * 
	 * @param vertexShaderFileLocation   the file path to the vertex shader's
	 *                                   location
	 * @param fragmentShaderFileLocation the file path to the fragment shader's
	 *                                   location
	 * @param geometryShaderFileLocation the file path to the geometry shader's
	 *                                   location
	 */
	public void create(String vertexShaderFileLocation, String fragmentShaderFileLocation, String geometryShaderFileLocation) {
		CountDownLatch countDownLatch = new CountDownLatch(3);
		shaderProgram = new ShaderProgram();
		ShaderFileLoadTask loadVertex = new ShaderFileLoadTask(linkTasks, countDownLatch, shaderProgram,
				new Shader(ShaderType.VERTEX), vertexShaderFileLocation);
		ShaderFileLoadTask loadFragment = new ShaderFileLoadTask(linkTasks, countDownLatch, shaderProgram,
				new Shader(ShaderType.FRAGMENT), fragmentShaderFileLocation);
		ShaderFileLoadTask loadGeometry = new ShaderFileLoadTask(linkTasks, countDownLatch, shaderProgram,
				new Shader(ShaderType.GEOMETRY), geometryShaderFileLocation);
		loadTasks.add(loadVertex);
		loadTasks.add(loadFragment);
		loadTasks.add(loadGeometry);
	}

	/**
	 * Gets the shaderProgram. It is recommended to only call this method if
	 * {@link #isBuilt()} returns true.
	 * 
	 * @return <code>null</code> if {@link #create(String, String)} has not been
	 *         called, or a non-null shader program if
	 *         <code>create(String, String)</code> has been called. The status on
	 *         the shader program can be found by calling {@link #isBuilt()} on the
	 *         shader program builder or {@link ShaderProgram#isLinked()} on the
	 *         shader program.
	 */
	public ShaderProgram getShaderProgram() {
		return shaderProgram;
	}

	/**
	 * @return if the shader program is done being built and is ready for pickup via
	 *         {@link #getShaderProgram()}.
	 */
	public boolean isBuilt() {
		return shaderProgram != null && shaderProgram.isLinked();
	}

}
