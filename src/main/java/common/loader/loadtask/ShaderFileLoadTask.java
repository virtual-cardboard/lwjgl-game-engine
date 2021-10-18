package common.loader.loadtask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import common.loader.GLLoadTask;
import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.ShaderType;

public final class ShaderFileLoadTask extends GLLoadTask<ShaderProgram> {

	private ShaderType type;
	private ShaderProgram shaderProgram;
	private String sourceLocation;

	private String source;
	private Shader shader;

	public ShaderFileLoadTask(ShaderType type, ShaderProgram shaderProgram, String sourceLocation) {
		this(new CountDownLatch(4), type, shaderProgram, sourceLocation);
	}

	public ShaderFileLoadTask(CountDownLatch countDownLatch, ShaderType type, ShaderProgram shaderProgram, String sourceLocation) {
		super(countDownLatch);
		this.type = type;
		this.shaderProgram = shaderProgram;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void loadIO() throws IOException {
		File file = getFile();
		source = loadSource(file);
		shader = new Shader(type);
	}

	@Override
	public ShaderProgram loadGL() {
		shader.setId(shader.getShaderType().genId());
		shader.compile(source);
		shaderProgram.attachShader(shader);
		countDownLatch.countDown();
		if (countDownLatch.getCount() < 3) {
			shaderProgram.generateId();
			shaderProgram.link();
		}
		return shaderProgram;
	}

	private File getFile() {
		String shaderPath = Shader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		// First check relative path for built-in shaders
		File file = new File(shaderPath + sourceLocation);
		if (!file.exists()) {
			// Create file with absolute path if the relative path does not resolve
			file = new File(sourceLocation);
		}
		return file;
	}

	private String loadSource(File file) throws FileNotFoundException, IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			StringBuilder code = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				code.append(line + "\n");
			}
			return code.toString();
		}
	}

	public Shader getShader() {
		return shader;
	}

}
