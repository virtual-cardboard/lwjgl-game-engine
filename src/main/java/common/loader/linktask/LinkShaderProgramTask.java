package common.loader.linktask;

import context.visuals.renderer.shader.FragmentShader;
import context.visuals.renderer.shader.ShaderProgram;
import context.visuals.renderer.shader.VertexShader;

public class LinkShaderProgramTask extends CountDownLinkTask {

	private ShaderProgram shaderProgram;
	private VertexShader vertexShader;
	private FragmentShader fragmentShader;

	public LinkShaderProgramTask(ShaderProgram shaderProgram, VertexShader vertexShader, FragmentShader fragmentShader) {
		this.shaderProgram = shaderProgram;
		this.vertexShader = vertexShader;
		this.fragmentShader = fragmentShader;
	}

	@Override
	public void doRun() {
		shaderProgram.generateId();
		shaderProgram.attachShader(vertexShader);
		shaderProgram.attachShader(fragmentShader);
		shaderProgram.link();
	}

}
