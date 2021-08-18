package common.loader.linktask;

import context.visuals.lwjgl.ShaderProgram;

public class CreateShaderProgramLinkTask extends LinkTask {

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
