package common.loader.loader;

import common.loader.graph.loader.GLLoader2Arg;
import context.GLContext;
import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;

public final class ShaderProgramLoader implements GLLoader2Arg<ShaderProgram, Shader, Shader> {

	@Override
	public ShaderProgram loadGL(GLContext glContext, Shader vertexShader, Shader fragmentShader) {
		ShaderProgram shaderProgram = new ShaderProgram();
		shaderProgram.addShader(vertexShader);
		shaderProgram.addShader(fragmentShader);
		shaderProgram.genId();
		shaderProgram.attachShaders();
		shaderProgram.link();
		return shaderProgram;
	}

}
