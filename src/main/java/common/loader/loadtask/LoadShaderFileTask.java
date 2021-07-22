package common.loader.loadtask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import common.loader.linktask.CreateShaderLinkTask;
import common.loader.linktask.LinkTask;
import context.visuals.renderer.shader.Shader;
import context.visuals.renderer.shader.ShaderProgram;

public class LoadShaderFileTask extends CountDownLoadTask {

	private CountDownLatch createShaderLinkTaskCountDownLatch;
	private Queue<LinkTask> linkTasks;
	private ShaderProgram shaderProgram;
	private Shader shader;
	private String sourceLocation;
	private String source;

	public LoadShaderFileTask(Queue<LinkTask> linkTasks, CountDownLatch createShaderLinkTaskCountDownLatch,
			ShaderProgram shaderProgram, Shader shader, String sourceLocation) {
		this(new CountDownLatch(1), createShaderLinkTaskCountDownLatch, linkTasks, shaderProgram, shader, sourceLocation);
	}

	public LoadShaderFileTask(CountDownLatch countDownLatch, CountDownLatch createShaderLinkTaskCountDownLatch,
			Queue<LinkTask> linkTasks, ShaderProgram shaderProgram, Shader shader, String sourceLocation) {
		super(countDownLatch);
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
			throw new RuntimeException(e);
		}
		source = code.toString();
		linkTasks.add(new CreateShaderLinkTask(createShaderLinkTaskCountDownLatch, linkTasks, shaderProgram, shader, shaderPath));
	}

	public String getSource() {
		return source;
	}

	public String getSourceLocation() {
		return sourceLocation;
	}

}
