package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private int id;
	private ShaderType shaderType;

	public Shader(ShaderType shaderType) {
		this.shaderType = shaderType;
	}

	public Shader verifyShaderType(ShaderType type) {
		if (shaderType != type) {
			throw new RuntimeException("Expected shader type: " + type + " actual shader type: " + shaderType);
		}
		return this;
	}

	public void compile(String source) {
		glShaderSource(id, source);
		glCompileShader(id);
		if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(id, 500));
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

	public ShaderType getShaderType() {
		return shaderType;
	}

}
