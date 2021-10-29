package context.visuals.builtin;

import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;

public class LineShaderProgram extends ShaderProgram {

	public LineShaderProgram(TransformationVertexShader vertexShader, Shader fragmentShader) {
		attachShader(vertexShader);
		attachShader(fragmentShader);
	}

}
