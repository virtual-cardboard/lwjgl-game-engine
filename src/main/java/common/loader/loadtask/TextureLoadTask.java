package common.loader.loadtask;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.lwjgl.system.MemoryStack;

import common.loader.linktask.CreateTextureLinkTask;
import common.loader.linktask.LinkTask;
import context.visuals.lwjgl.Texture;

public class TextureLoadTask extends LoadTask {

	private Texture texture;
	private Queue<LinkTask> linkTasks;
	private CreateTextureLinkTask createTextureLinkTask;

	public TextureLoadTask(Texture texture, Queue<LinkTask> linkTasks) {
		this(new CountDownLatch(1), texture, linkTasks);
	}

	public TextureLoadTask(CountDownLatch countDownLatch, Texture texture, Queue<LinkTask> linkTasks) {
		super(countDownLatch);
		this.texture = texture;
		this.linkTasks = linkTasks;
	}

	@Override
	public void doRun() {
		ByteBuffer data;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);
			stbi_set_flip_vertically_on_load(true);
			data = stbi_load(texture.getImagePath(), w, h, comp, 4);
			if (data == null) {
				System.err.println("Failed to load texture at " + texture.getImagePath());
				throw new RuntimeException(stbi_failure_reason());
			}
			texture.setWidth(w.get());
			texture.setHeight(h.get());
		}
		createTextureLinkTask = new CreateTextureLinkTask(texture, data);
		linkTasks.add(createTextureLinkTask);
	}

	public Future<Texture> getFutureTexture() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<Texture> futureTexture = executorService.submit(new Callable<Texture>() {
			@Override
			public Texture call() throws Exception {
				while (createTextureLinkTask == null) {
					Thread.sleep(20);
				}
				return createTextureLinkTask.getFutureTexture().get();
			}
		});
		executorService.shutdown();
		return futureTexture;
	}

}
