package context.visuals.renderer.shader;

import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class VertexShader extends Shader {

	public VertexShader(String sourceLocation) {
		super(sourceLocation, GL_VERTEX_SHADER);
	}

}
