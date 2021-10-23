package common.loader.loadtask;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;

public final class ShaderProgramLoadTask extends GLLoadTask<ShaderProgram> {

	private Shader[] shaders;

	public ShaderProgramLoadTask(Shader... shaders) {
		this.shaders = shaders;
	}

	@Override
	protected ShaderProgram loadGL(GLContext glContext) {
		ShaderProgram shaderProgram = new ShaderProgram();
		shaderProgram.generateId();
		for (int i = 0; i < shaders.length; i++) {
			shaderProgram.attachShader(shaders[i]);
		}
		shaderProgram.link();
		return shaderProgram;
	}

}
