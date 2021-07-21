package common.loader.loadtask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import context.visuals.renderer.shader.Shader;

public class LoadShaderSourceTask extends CountDownLoadTask {

	private String sourceLocation;
	private String source;

	public LoadShaderSourceTask(String sourceLocation) {
		this(new CountDownLatch(1), sourceLocation);
	}

	public LoadShaderSourceTask(CountDownLatch countDownLatch, String sourceLocation) {
		super(countDownLatch);
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void doRun() {
		StringBuilder code = new StringBuilder();
		String shaderPath = Shader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		File file = new File(shaderPath + sourceLocation);
		if (!file.exists()) {
			file = new File(sourceLocation);
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				code.append(line + "\n");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		source = code.toString();
	}

	public String getSource() {
		return source;
	}

	public String getSourceLocation() {
		return sourceLocation;
	}

}
