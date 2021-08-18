package common.loader.linktask;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;

public class CreateShaderLinkTask extends LinkTask {

	private Queue<LinkTask> linkTasks;
	private ShaderProgram shaderProgram;
	private Shader shader;
	private String source;
	private CountDownLatch countDownLatch;

	public CreateShaderLinkTask(Queue<LinkTask> linkTasks, ShaderProgram shaderProgram, Shader shader, String source) {
		this(new CountDownLatch(1), linkTasks, shaderProgram, shader, source);
	}

	public CreateShaderLinkTask(CountDownLatch countDownLatch, Queue<LinkTask> linkTasks, ShaderProgram shaderProgram, Shader shader, String source) {
		this.countDownLatch = countDownLatch;
		this.linkTasks = linkTasks;
		this.shaderProgram = shaderProgram;
		this.shader = shader;
		this.source = source;
	}

	@Override
	public void doRun() {
		shader.generateId();
		shader.compile(source);
		shaderProgram.attachShader(shader);
		countDownLatch.countDown();
		if (countDownLatch.getCount() == 0) {
			linkTasks.add(new CreateShaderProgramLinkTask(shaderProgram));
		}
	}

	public Shader getShader() {
		return shader;
	}

}
