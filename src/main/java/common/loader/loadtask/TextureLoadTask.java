package common.loader.loadtask;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.CountDownLatch;

import org.lwjgl.system.MemoryStack;

import context.visuals.lwjgl.Texture;

public class TextureLoadTask extends OpenGLLoadTask {

	private Texture texture;
	private int textureUnit;
	private String path;
	private ByteBuffer textureData;

	/**
	 * Creates a <code>TextureLoadTask</code> with a count down latch with a
	 * starting count of 2.
	 * 
	 * @param texture the texture to load data into
	 */
	public TextureLoadTask(int textureUnit, String path) {
		this(new CountDownLatch(1), textureUnit, path);
	}

	public TextureLoadTask(CountDownLatch countDownLatch, int textureUnit, String path) {
		super(countDownLatch);
		this.textureUnit = textureUnit;
		this.path = path;
	}

	@Override
	public void loadIO() {
		texture = new Texture(textureUnit);
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);
			textureData = stbi_load(path, w, h, comp, 4);
			if (textureData == null) {
				System.err.println("Failed to load texture at " + path);
				throw new RuntimeException(stbi_failure_reason());
			}
			texture.setWidth(w.get());
			texture.setHeight(h.get());
		}
	}

	@Override
	public void loadGL() {
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
	}

	public Texture getTexture() {
		return texture;
	}

}
