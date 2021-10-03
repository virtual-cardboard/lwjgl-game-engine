package common.loader.loadtask;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.CountDownLatch;

import org.lwjgl.system.MemoryStack;

import context.visuals.lwjgl.Texture;

public class TextureLoadTask extends LoadTask {

	private CountDownLatch countDownLatch;
	private Texture texture;
	private String path;

	/**
	 * Creates a <code>TextureLoadTask</code> with a count down latch with a
	 * starting count of 2.
	 * 
	 * @param texture the texture to load data into
	 */
	public TextureLoadTask(String path) {
		this(new CountDownLatch(2), path);
	}

	public TextureLoadTask(CountDownLatch countDownLatch, String path) {
		this.countDownLatch = countDownLatch;
		this.path = path;
	}

	@Override
	public void doRun() {
		texture = new Texture(0);

		ByteBuffer data;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);
			data = stbi_load(path, w, h, comp, 4);
			if (data == null) {
				System.err.println("Failed to load texture at " + path);
				throw new RuntimeException(stbi_failure_reason());
			}
			texture.setWidth(w.get());
			texture.setHeight(h.get());
		}
//		linkTasks.add(new CreateTextureLinkTask(countDownLatch, texture, data));
		countDownLatch.countDown();
	}

	public final CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	public Texture getTexture() {
		return texture;
	}

}
