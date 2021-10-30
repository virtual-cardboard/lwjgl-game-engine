package common.loader.loadtask;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;

public final class ShaderProgramLoadTask extends GLLoadTask<ShaderProgram> {

	private ShaderProgram shaderProgram;

	public ShaderProgramLoadTask(Shader... shaders) {
		this.shaderProgram = new ShaderProgram();
		for (int i = 0; i < shaders.length; i++) {
			shaderProgram.addShader(shaders[i]);
		}
	}

	public ShaderProgramLoadTask(ShaderProgram shaderProgram) {
		this.shaderProgram = shaderProgram;
	}

	@Override
	protected ShaderProgram loadGL(GLContext glContext) {
		shaderProgram.genId();
		shaderProgram.attachShaders();
		shaderProgram.link();
		return shaderProgram;
	}

}
