package context.visuals.renderer.shader;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import common.loader.linktask.LinkShaderProgramTask;
import common.loader.linktask.LinkShaderSourceTask;
import common.loader.linktask.LinkTask;
import common.loader.loadtask.LoadShaderSourceTask;
import common.loader.loadtask.LoadTask;

public class ShaderFactory {

	private ShaderFactory() {
	}

	public static LoadShaderSourceTask loadShaderSource(String shaderSourceLocation, Queue<LoadTask> loadTasks) {
		LoadShaderSourceTask loadShaderSourceTask = new LoadShaderSourceTask(shaderSourceLocation);
		loadTasks.add(loadShaderSourceTask);
		return loadShaderSourceTask;
	}

	public static CountDownLatch compileShaders(VertexShader vertexShader, FragmentShader fragmentShader,
			String vertexSource, String fragmentSource, Queue<LinkTask> linkTasks) {
		CountDownLatch countDownLatch = new CountDownLatch(2);
		linkTasks.add(new LinkShaderSourceTask(countDownLatch, vertexShader, vertexSource));
		linkTasks.add(new LinkShaderSourceTask(countDownLatch, fragmentShader, fragmentSource));
		return countDownLatch;
	}

	public static LinkShaderProgramTask createShaderProgram(ShaderProgram shaderProgram, VertexShader vertexShader, FragmentShader fragmentShader,
			Queue<LinkTask> linkTasks) {
		LinkShaderProgramTask linkShaderProgramTask = new LinkShaderProgramTask(shaderProgram, vertexShader, fragmentShader);
		linkTasks.add(linkShaderProgramTask);
		return linkShaderProgramTask;
	}

}
