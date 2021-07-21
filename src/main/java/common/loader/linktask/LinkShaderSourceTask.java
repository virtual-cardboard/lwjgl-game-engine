package common.loader.linktask;

import java.util.concurrent.CountDownLatch;

import context.visuals.renderer.shader.Shader;

public class LinkShaderSourceTask extends CountDownLinkTask {

	private Shader shader;
	private String source;

	public LinkShaderSourceTask(Shader shader, String source) {
		this(new CountDownLatch(1), shader, source);
	}

	public LinkShaderSourceTask(CountDownLatch countDownLatch, Shader shader, String source) {
		super(countDownLatch);
		this.shader = shader;
		this.source = source;
	}

	@Override
	public void doRun() {
		shader.compile(source);
	}

	public Shader getShader() {
		return shader;
	}

}
