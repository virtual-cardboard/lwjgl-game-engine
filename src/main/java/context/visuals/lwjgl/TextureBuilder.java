package context.visuals.lwjgl;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import common.loader.linktask.LinkTask;
import common.loader.loadtask.LoadTask;
import common.loader.loadtask.TextureLoadTask;

public class TextureBuilder {

	private Queue<LoadTask> loadTasks;
	private Queue<LinkTask> linkTasks;

	public TextureBuilder(Queue<LoadTask> loadTasks, Queue<LinkTask> linkTasks) {
		this.loadTasks = loadTasks;
		this.linkTasks = linkTasks;
	}

	public Texture createTexture(int textureUnit, String imagePath) {
		Texture texture = new Texture(textureUnit, imagePath);
		TextureLoadTask textureLoadTask = new TextureLoadTask(texture, linkTasks);
		loadTasks.add(textureLoadTask);
		return texture;
	}

	public Texture createTexture(CountDownLatch countDownLatch, int textureUnit, String imagePath) {
		Texture texture = new Texture(textureUnit, imagePath);
		TextureLoadTask textureLoadTask = new TextureLoadTask(countDownLatch, texture, linkTasks);
		loadTasks.add(textureLoadTask);
		return texture;
	}

}
