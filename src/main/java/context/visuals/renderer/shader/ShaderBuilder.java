package context.visuals.renderer.shader;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import common.loader.linktask.LinkTask;
import common.loader.loadtask.LoadShaderFileTask;
import common.loader.loadtask.LoadTask;

public final class ShaderBuilder {

	private final Queue<LoadTask> loadTasks;
	private final Queue<LinkTask> linkTasks;
	private volatile ShaderProgram shaderProgram;
	private volatile boolean built;

	public ShaderBuilder(Queue<LoadTask> loadTasks, Queue<LinkTask> linkTasks) {
		this.loadTasks = loadTasks;
		this.linkTasks = linkTasks;
	}

	public void create(String vertexShaderFileLocation, String fragmentShaderFileLocation) {
		built = false;
		CountDownLatch countDownLatch = new CountDownLatch(2);
		shaderProgram = new ShaderProgram();
		LoadShaderFileTask loadVertex = new LoadShaderFileTask(linkTasks, countDownLatch, shaderProgram,
				new Shader(ShaderType.VERTEX), vertexShaderFileLocation);
		LoadShaderFileTask loadFragment = new LoadShaderFileTask(linkTasks, countDownLatch, shaderProgram,
				new Shader(ShaderType.FRAGMENT), fragmentShaderFileLocation);
		loadTasks.add(loadVertex);
		loadTasks.add(loadFragment);
	}

	public ShaderProgram getShaderProgram() {
		return built ? shaderProgram : null;
	}

	public boolean isBuilt() {
		return built;
	}

}
