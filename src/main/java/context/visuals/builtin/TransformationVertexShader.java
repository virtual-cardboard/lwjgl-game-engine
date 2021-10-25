package context.visuals.builtin;

import static context.visuals.lwjgl.ShaderType.VERTEX;

import context.visuals.lwjgl.Shader;

public final class TransformationVertexShader extends Shader {

	private static final String TRANSFORMATION_VERTEX_SHADER_SOURCE = "#version 330 core\r\n"
			+ "layout (location = 0) in vec3 vertexPos;"
			+ "uniform mat4 matrix4f;"
			+ "void main() { gl_Position = matrix4f * vec4(vertexPos, 1);}";

	private TransformationVertexShader() {
		super(VERTEX);
	}

	public static TransformationVertexShader createTransformationVertexShader() {
		TransformationVertexShader shader = new TransformationVertexShader();
		shader.setId(VERTEX.genId());
		shader.compile(TRANSFORMATION_VERTEX_SHADER_SOURCE);
		return shader;
	}

}
