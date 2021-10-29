package context.visuals.builtin;

import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;

public class TextShaderProgram extends ShaderProgram {

	public TextShaderProgram(TexturedTransformationVertexShader vertexShader, Shader fragmentShader) {
		attachShader(vertexShader);
		attachShader(fragmentShader);
	}

}
