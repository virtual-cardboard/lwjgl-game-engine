package context.visuals.lwjgl;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import common.loader.linktask.LinkTask;
import common.loader.loadtask.LoadTask;
import common.loader.loadtask.TextureLoadTask;

public class TextureBuilder {

	private static final int NUM_THREADS = 2;

	private Queue<LoadTask> loadTasks;
	private Queue<LinkTask> linkTasks;
	private ExecutorService fixedThreadPool;

	public TextureBuilder(Queue<LoadTask> loadTasks, Queue<LinkTask> linkTasks) {
		this.loadTasks = loadTasks;
		this.linkTasks = linkTasks;
		fixedThreadPool = Executors.newFixedThreadPool(NUM_THREADS,
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread t = Executors.defaultThreadFactory().newThread(r);
						t.setDaemon(true);
						return t;
					}
				});
	}

	public Future<Texture> createTexture(int textureUnit, String imagePath) {
		Texture texture = new Texture(textureUnit, imagePath);
		TextureLoadTask textureLoadTask = new TextureLoadTask(texture, linkTasks);
		loadTasks.add(textureLoadTask);
		return fixedThreadPool.submit(() -> {
			return textureLoadTask.getFutureTexture().get();
		});
	}

	public void end() {
		fixedThreadPool.shutdown();
	}

}
