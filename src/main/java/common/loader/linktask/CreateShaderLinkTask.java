package common.loader.linktask;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import context.visuals.renderer.shader.Shader;
import context.visuals.renderer.shader.ShaderProgram;

public class CreateShaderLinkTask extends LinkTask {

	private Queue<LinkTask> linkTasks;
	private ShaderProgram shaderProgram;
	private Shader shader;
	private String source;

	public CreateShaderLinkTask(Queue<LinkTask> linkTasks, ShaderProgram shaderProgram, Shader shader, String source) {
		this(new CountDownLatch(1), linkTasks, shaderProgram, shader, source);
	}

	public CreateShaderLinkTask(CountDownLatch countDownLatch, Queue<LinkTask> linkTasks, ShaderProgram shaderProgram, Shader shader, String source) {
		super(countDownLatch);
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
		if (isDone()) {
			linkTasks.add(new CreateShaderProgramLinkTask(shaderProgram));
		}
	}

	public Shader getShader() {
		return shader;
	}

}
