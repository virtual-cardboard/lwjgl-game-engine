package context.visuals.renderer.shader;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import common.loader.linktask.LinkTask;
import common.loader.loadtask.LoadTask;
import common.loader.loadtask.ShaderFileLoadTask;

public final class ShaderBuilder {

	private final Queue<LoadTask> loadTasks;
	private final Queue<LinkTask> linkTasks;
	private volatile ShaderProgram shaderProgram;

	public ShaderBuilder(Queue<LoadTask> loadTasks, Queue<LinkTask> linkTasks) {
		this.loadTasks = loadTasks;
		this.linkTasks = linkTasks;
	}

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

	public ShaderProgram getShaderProgram() {
		ShaderProgram shaderProgramCopy = shaderProgram;
		shaderProgram = null;
		return shaderProgramCopy;
	}

	public boolean isBuilt() {
		return shaderProgram != null && shaderProgram.isLinked();
	}

}