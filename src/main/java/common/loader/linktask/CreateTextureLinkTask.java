package common.loader.linktask;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

import context.visuals.lwjgl.Texture;

public class CreateTextureLinkTask extends LinkTask {

	private Texture texture;
	private ByteBuffer textureData;
	private CountDownLatch countDownLatch;

	public CreateTextureLinkTask(Texture texture, ByteBuffer textureData) {
		this(new CountDownLatch(1), texture, textureData);
	}

	public CreateTextureLinkTask(CountDownLatch countDownLatch, Texture texture, ByteBuffer textureData) {
		this.countDownLatch = countDownLatch;
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
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		stbi_image_free(textureData);
		texture.link();
		countDownLatch.countDown();
	}

	public Texture getTexture() {
		return texture;
	}

}
