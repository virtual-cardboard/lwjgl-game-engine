package common.loader.linktask;

import context.visuals.renderer.shader.ShaderProgram;

public class CreateShaderProgramLinkTask extends CountDownLinkTask {

	private ShaderProgram shaderProgram;

	public CreateShaderProgramLinkTask(ShaderProgram shaderProgram) {
		this.shaderProgram = shaderProgram;
	}

	@Override
	public void doRun() {
		shaderProgram.generateId();
		shaderProgram.link();
	}

}
