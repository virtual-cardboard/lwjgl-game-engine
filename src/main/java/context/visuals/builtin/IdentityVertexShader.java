package context.visuals.builtin;

import static context.visuals.lwjgl.ShaderType.VERTEX;

import context.visuals.lwjgl.Shader;

public final class IdentityVertexShader extends Shader {

	private static final String IDENTITY_VERTEX_SHADER_SOURCE = "#version 330 core\r\n"
			+ "layout (location = 0) in vec3 vertexPos;\r\n"
			+ "void main() { gl_Position = vertexPos; }";

	private IdentityVertexShader() {
		super(VERTEX);
	}

	public static IdentityVertexShader createIdentityVertexShader() {
		IdentityVertexShader shader = new IdentityVertexShader();
		shader.setId(VERTEX.genId());
		shader.compile(IDENTITY_VERTEX_SHADER_SOURCE);
		return shader;
	}

}
