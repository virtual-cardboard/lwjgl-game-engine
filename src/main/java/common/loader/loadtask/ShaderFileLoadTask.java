package common.loader.loadtask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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

	public ShaderFileLoadTask(CountDownLatch createShaderLinkTaskCountDownLatch, Queue<LinkTask> linkTasks,
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
	public void load() throws IOException {
		File file = getFile();
		source = loadSource(file);
		CreateShaderLinkTask createShaderLinkTask = new CreateShaderLinkTask(createShaderLinkTaskCountDownLatch, linkTasks, shaderProgram, shader, source);
		linkTasks.add(createShaderLinkTask);
		countDownLatch.countDown();
	}

	private File getFile() {
		String shaderPath = Shader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		// First check relative path for built-in shaders
		File file = new File(shaderPath + sourceLocation);
		if (!file.exists()) {
			// Create file with absolute path if the relative path does not resolve
			file = new File(sourceLocation);
		}
		return file;
	}

	private String loadSource(File file) throws FileNotFoundException, IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			StringBuilder code = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				code.append(line + "\n");
			}
			return code.toString();
		}
	}

	public final CountDownLatch countDownLatch() {
		return countDownLatch;
	}

	public String getSource() {
		return source;
	}

	public String getSourceLocation() {
		return sourceLocation;
	}

}
