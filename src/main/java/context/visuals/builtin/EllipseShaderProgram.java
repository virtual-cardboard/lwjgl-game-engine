package context.visuals.builtin;

import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;

public class EllipseShaderProgram extends ShaderProgram {

	public EllipseShaderProgram(TransformationVertexShader vertexShader, Shader fragmentShader) {
		attachShader(vertexShader);
		attachShader(fragmentShader);
	}

}
