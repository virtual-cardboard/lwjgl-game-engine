package context.visuals.lwjgl;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import common.loader.AbstractBuilder;
import common.loader.linktask.LinkTask;
import common.loader.loadtask.LoadTask;
import common.loader.loadtask.TextureLoadTask;

public class TextureBuilder extends AbstractBuilder<Texture> {

	private Queue<LoadTask> loadTasks;
	private Queue<LinkTask> linkTasks;

	public TextureBuilder(Queue<LoadTask> loadTasks, Queue<LinkTask> linkTasks) {
		this.loadTasks = loadTasks;
		this.linkTasks = linkTasks;
	}

	public void createTexture(int textureUnit, String imagePath) {
		Texture texture = new Texture(textureUnit, imagePath);
		TextureLoadTask textureLoadTask = new TextureLoadTask(texture, linkTasks);
		loadTasks.add(textureLoadTask);
		set(texture);
	}

	public void createTexture(CountDownLatch countDownLatch, int textureUnit, String imagePath) {
		Texture texture = new Texture(textureUnit, imagePath);
		TextureLoadTask textureLoadTask = new TextureLoadTask(countDownLatch, texture, linkTasks);
		loadTasks.add(textureLoadTask);
	}

	@Override
	public boolean isBuilt() {
		Texture texture = get();
		return texture != null && texture.isLinked();
	}

}
