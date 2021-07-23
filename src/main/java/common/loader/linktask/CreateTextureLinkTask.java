package common.loader.linktask;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;

import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import context.visuals.lwjgl.Texture;

public class CreateTextureLinkTask extends LinkTask {

	private Texture texture;
	private ByteBuffer textureData;

	public CreateTextureLinkTask(Texture texture, ByteBuffer textureData) {
		this(new CountDownLatch(1), texture, textureData);
	}

	public CreateTextureLinkTask(CountDownLatch countDownLatch, Texture texture, ByteBuffer textureData) {
		super(countDownLatch);
		this.texture = texture;
		this.textureData = textureData;
	}

	@Override
	public void doRun() {
		texture.setId(glGenTextures());
		texture.bind();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, texture.getWidth(), texture.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, textureData);
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		stbi_image_free(textureData);
		texture.link();
	}

	public Texture getTexture() {
		return texture;
	}

	public Future<Texture> getFutureTexture() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<Texture> futureTexture = executorService.submit(new Callable<Texture>() {
			@Override
			public Texture call() throws Exception {
				while (!texture.isLinked()) {
					Thread.sleep(20);
				}
				return texture;
			}
		});
		executorService.shutdown();
		return futureTexture;
	}

}
