package common.loader.loadtask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import common.loader.linktask.CreateShaderLinkTask;
import common.loader.linktask.LinkTask;
import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;

public class ShaderFileLoadTask extends LoadTask {

	private CountDownLatch createShaderLinkTaskCountDownLatch;
	private Queue<LinkTask> linkTasks;
	private ShaderProgram shaderProgram;
	private Shader shader;
	private String sourceLocation;
	private String source;
	private CountDownLatch countDownLatch;

	public ShaderFileLoadTask(Queue<LinkTask> linkTasks, CountDownLatch createShaderLinkTaskCountDownLatch,
			ShaderProgram shaderProgram, Shader shader, String sourceLocation) {
		this(new CountDownLatch(1), createShaderLinkTaskCountDownLatch, linkTasks, shaderProgram, shader, sourceLocation);
	}

	public ShaderFileLoadTask(CountDownLatch countDownLatch, CountDownLatch createShaderLinkTaskCountDownLatch,
			Queue<LinkTask> linkTasks, ShaderProgram shaderProgram, Shader shader, String sourceLocation) {
		this.countDownLatch = countDownLatch;
		this.createShaderLinkTaskCountDownLatch = createShaderLinkTaskCountDownLatch;
		this.linkTasks = linkTasks;
		this.shaderProgram = shaderProgram;
		this.shader = shader;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void doRun() {
		StringBuilder code = new StringBuilder();
		String shaderPath = Shader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		File file = new File(shaderPath + sourceLocation);
		if (!file.exists()) {
			file = new File(sourceLocation);
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				code.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		source = code.toString();
		CreateShaderLinkTask createShaderLinkTask = new CreateShaderLinkTask(createShaderLinkTaskCountDownLatch, linkTasks, shaderProgram, shader, source);
		linkTasks.add(createShaderLinkTask);
		countDownLatch.countDown();
	}

	public String getSource() {
		return source;
	}

	public String getSourceLocation() {
		return sourceLocation;
	}

}
