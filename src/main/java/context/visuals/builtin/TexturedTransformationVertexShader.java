package context.visuals.builtin;

import static context.visuals.lwjgl.ShaderType.VERTEX;

import context.visuals.lwjgl.Shader;

public class TexturedTransformationVertexShader extends Shader {

	public TexturedTransformationVertexShader() {
		super(VERTEX);
	}

	private static final String TEXTURED_TRANSFORMATION_VERTEX_SHADER_SOURCE = "#version 330 core\r\n"
			+ "layout (location = 0) in vec3 vertexPos;"
			+ "layout (location = 1) in vec2 textureCoord;"
			+ "out vec2 texCoord;"
			+ "uniform mat4 matrix4f;"
			+ "void main() {"
			+ "gl_Position = matrix4f * vec4(vertexPos, 1);"
			+ "texCoord = textureCoord;"
			+ "}";

	public static TexturedTransformationVertexShader createTexturedTransformationVertexShader() {
		TexturedTransformationVertexShader shader = new TexturedTransformationVertexShader();
		shader.genId();
		shader.compile(TEXTURED_TRANSFORMATION_VERTEX_SHADER_SOURCE);
		return shader;
	}

}
