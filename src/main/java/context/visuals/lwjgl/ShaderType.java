package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

public enum ShaderType {

	VERTEX(GL_VERTEX_SHADER),

	FRAGMENT(GL_FRAGMENT_SHADER),

	GEOMETRY(GL_GEOMETRY_SHADER);

	private final int shader;

	private ShaderType(int shader) {
		this.shader = shader;
	}

	public final int genId() {
		return glCreateShader(shader);
	}

}
