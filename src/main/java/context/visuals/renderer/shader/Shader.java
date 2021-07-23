package context.visuals.renderer.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private int id;
	private ShaderType shaderType;

	public Shader(ShaderType shaderType) {
		this.shaderType = shaderType;
	}

	public void compile(String source) {
		glShaderSource(id, source);
		glCompileShader(id);
		if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(id, 500));
			throw new RuntimeException("Could not compile shader source:\n" + source);
		}
	}

	public void generateId() {
		switch (shaderType) {
			case VERTEX:
				setId(glCreateShader(GL_VERTEX_SHADER));
				break;
			case FRAGMENT:
				setId(glCreateShader(GL_FRAGMENT_SHADER));
				break;
			default:
				throw new IllegalStateException("Could not generate shader ID because of unknown shaderType");
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

	public ShaderType getShaderType() {
		return shaderType;
	}

}
