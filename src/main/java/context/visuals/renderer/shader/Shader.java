package context.visuals.renderer.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class Shader {

	private int id;

	protected Shader(String sourceLocation, int shaderType) {
		this.id = glCreateShader(shaderType);
		String source = loadSource(sourceLocation);
		glShaderSource(id, source);
		glCompileShader(id);
		if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(id, 500));
			System.err.println("Could not compile shader at " + sourceLocation);
			System.exit(-1);
		}
	}

	public void delete() {
		glDeleteShader(id);
	}

	private static String loadSource(String sourceLocation) {
		StringBuilder code = new StringBuilder();
		String shaderPath = Shader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		File file = new File(shaderPath + sourceLocation);
		if (!file.exists()) {
			file = new File(sourceLocation);
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				code.append(line + '\n');
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return code.toString();
	}

	public int getId() {
		return id;
	}
}
