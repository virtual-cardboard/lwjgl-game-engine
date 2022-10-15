package context.visuals.builtin;

import static context.visuals.lwjgl.ShaderType.FRAGMENT;
import static context.visuals.lwjgl.ShaderType.VERTEX;

import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;

public class EntityShaderProgram extends ShaderProgram {

	public EntityShaderProgram(Shader vertexShader, Shader fragmentShader) {
		addShader(vertexShader.verifyShaderType(VERTEX));
		addShader(fragmentShader.verifyShaderType(FRAGMENT));
	}

}
