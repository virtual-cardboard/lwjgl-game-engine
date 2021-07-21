package common.loader.loadtask;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import common.loader.linktask.LinkTask;
import context.visuals.renderer.shader.FragmentShader;
import context.visuals.renderer.shader.ShaderFactory;
import context.visuals.renderer.shader.ShaderProgram;
import context.visuals.renderer.shader.VertexShader;

public class CreateShaderProgramLoadTask extends LoadTask {

	private String vertexShaderSourceLocation;
	private String fragmentShaderSourceLocation;
	private Queue<LoadTask> loadTasks;
	private Queue<LinkTask> linkTasks;
	private ShaderProgram shaderProgram;

	public CreateShaderProgramLoadTask(String vertexShaderSourceLocation, String fragmentShaderSourceLocation,
			Queue<LoadTask> loadTasks, Queue<LinkTask> linkTasks) {
		this.vertexShaderSourceLocation = vertexShaderSourceLocation;
		this.fragmentShaderSourceLocation = fragmentShaderSourceLocation;
		this.loadTasks = loadTasks;
		this.linkTasks = linkTasks;
	}

	@Override
	public void doRun() {
		CountDownLatch countDownLatch = new CountDownLatch(3);
		shaderProgram = new ShaderProgram();
		Thread createShaderProgramThread = new Thread(() -> {
			final LoadShaderSourceTask loadVertex = ShaderFactory.loadShaderSource(vertexShaderSourceLocation, loadTasks);
			final LoadShaderSourceTask loadFragment = ShaderFactory.loadShaderSource(fragmentShaderSourceLocation, loadTasks);
			String vertexSource;
			String fragmentSource;
			try {
				loadVertex.getCountDownLatch().await();
				loadFragment.getCountDownLatch().await();
				vertexSource = loadVertex.getSource();
				fragmentSource = loadFragment.getSource();
				VertexShader vertexShader = new VertexShader();
				FragmentShader fragmentShader = new FragmentShader();
				ShaderFactory.compileShaders(vertexShader, fragmentShader, vertexSource, fragmentSource, linkTasks).await();
				ShaderFactory.createShaderProgram(shaderProgram, vertexShader, fragmentShader, linkTasks).getCountDownLatch().await();
			} catch (final InterruptedException e1) {
				e1.printStackTrace();
			}
			countDownLatch.countDown();
		});
		createShaderProgramThread.setName("createShaderProgramThread");
		createShaderProgramThread.setDaemon(true);
		createShaderProgramThread.start();
	}

	public ShaderProgram getShaderProgram() {
		return shaderProgram;
	}

}
