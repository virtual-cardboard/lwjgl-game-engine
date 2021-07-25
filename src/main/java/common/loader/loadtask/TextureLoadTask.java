package common.loader.loadtask;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import org.lwjgl.system.MemoryStack;

import common.loader.linktask.CreateTextureLinkTask;
import common.loader.linktask.LinkTask;
import context.visuals.lwjgl.Texture;

public class TextureLoadTask extends LoadTask {

	private CountDownLatch countDownLatch;
	private Texture texture;
	private Queue<LinkTask> linkTasks;

	/**
	 * Creates a <code>TextureLoadTask</code> with a count down latch with a
	 * starting count of 2.
	 * 
	 * @param texture   the texture to load data into
	 * @param linkTasks the {@link Queue} of {@link LinkTask}s.
	 */
	public TextureLoadTask(Texture texture, Queue<LinkTask> linkTasks) {
		this(new CountDownLatch(2), texture, linkTasks);
	}

	public TextureLoadTask(CountDownLatch countDownLatch, Texture texture, Queue<LinkTask> linkTasks) {
		this.countDownLatch = countDownLatch;
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
		linkTasks.add(new CreateTextureLinkTask(countDownLatch, texture, data));
		countDownLatch.countDown();
	}

}
