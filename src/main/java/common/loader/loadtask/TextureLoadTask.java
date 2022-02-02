package common.loader.loadtask;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.Texture;

public class TextureLoadTask extends GLLoadTask<Texture> {

	private Texture texture;
	private String path;

	/**
	 * Creates a <code>TextureLoadTask</code> with a texture unit and a path.
	 * 
	 * @param textureUnit the texture to load data into
	 * @param path        the path to the texture's source image
	 */
	public TextureLoadTask(String path) {
		this(new Texture(), path);
	}

	/**
	 * Creates a <code>TextureLoadTask</code> with an unloaded texture and a path.
	 * 
	 * @param texture the texture to load data into
	 * @param path    the path to the texture's source image
	 */
	public TextureLoadTask(Texture texture, String path) {
		this.texture = texture;
		this.path = path;
	}

	@Override
	public Texture loadGL(GLContext glContext) {
		ByteBuffer textureData;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);
			textureData = stbi_load(path, w, h, comp, 4);
			if (textureData == null) {
				System.err.println("Failed to load texture at " + path + ".");
				throw new RuntimeException(stbi_failure_reason());
			}
			texture.setWidth(w.get());
			texture.setHeight(h.get());
		}
		texture.genID();
		texture.bind(glContext);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, texture.width(), texture.height(), 0, GL_RGBA, GL_UNSIGNED_BYTE, textureData);
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		stbi_image_free(textureData);
		return texture;
	}

}
