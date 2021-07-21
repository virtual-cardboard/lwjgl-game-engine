package context.visuals.renderer.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {

	private int id;

	public void compile(String source) {
		glShaderSource(id, source);
		glCompileShader(id);
		if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(id, 500));
			throw new RuntimeException("Could not compile shader source:\n" + source);
		}
	}

	public void delete() {
		glDeleteShader(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
