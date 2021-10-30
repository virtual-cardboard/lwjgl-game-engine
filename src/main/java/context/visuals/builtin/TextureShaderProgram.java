package context.visuals.builtin;

import static context.visuals.lwjgl.ShaderType.FRAGMENT;

import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;

public class TextureShaderProgram extends ShaderProgram {

	public TextureShaderProgram(TexturedTransformationVertexShader vertexShader, Shader fragmentShader) {
		addShader(vertexShader);
		addShader(fragmentShader.verifyShaderType(FRAGMENT));
	}

}
