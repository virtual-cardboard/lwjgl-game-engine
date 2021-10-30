package context.visuals.builtin;

import static context.visuals.lwjgl.ShaderType.FRAGMENT;

import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;

public class EllipseShaderProgram extends ShaderProgram {

	public EllipseShaderProgram(TransformationVertexShader vertexShader, Shader fragmentShader) {
		attachShader(vertexShader);
		attachShader(fragmentShader.verifyShaderType(FRAGMENT));
	}

}
