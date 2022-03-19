package common.loader.loadtask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderType;

public class ShaderLoadTask implements GLLoadTask<Shader> {

	private Shader shader;
	private String sourceLocation;
	private String source;

	public ShaderLoadTask(ShaderType type, String sourceLocation) {
		this(new Shader(type), sourceLocation);
	}

	public ShaderLoadTask(Shader shader, String sourceLocation) {
		this.shader = shader;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Shader loadGL(GLContext glContext) {
		File file = getFile();
		source = loadSource(file);
		shader.genID();
		shader.compile(source);
		return shader;
	}

	private File getFile() {
		// First check absolute path
		File file = new File(sourceLocation);
		if (!file.exists()) {
			String shaderPath = Shader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			// Then check relative path for built-in shaders
			file = new File(shaderPath + sourceLocation);
		}
		return file;
	}

	private String loadSource(File file) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			StringBuilder code = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				code.append(line + "\n");
			}
			return code.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
