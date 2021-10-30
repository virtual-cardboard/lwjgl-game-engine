package context.visuals.builtin;

import static context.visuals.lwjgl.ShaderType.FRAGMENT;

import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;

public class TextShaderProgram extends ShaderProgram {

	public TextShaderProgram(TexturedTransformationVertexShader vertexShader, Shader fragmentShader) {
		addShader(vertexShader);
		addShader(fragmentShader.verifyShaderType(FRAGMENT));
	}

}
