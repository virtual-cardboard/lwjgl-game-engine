package engine.common.loader.loadtask;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import context.GLContext;
import context.visuals.lwjgl.Texture;
import engine.common.loader.GLLoadTask;
import engine.common.loader.graph.loader.GLLoader0Arg;
import org.lwjgl.system.MemoryStack;

public class TextureLoadTask implements GLLoadTask<Texture>, GLLoader0Arg<Texture> {

	private Texture texture;
	private String path;

	private Map<Integer, Integer> textureParameters = new HashMap<>();

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
		setTextureParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		setTextureParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		setTextureParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR);
		setTextureParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
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
		for (Integer key : textureParameters.keySet()) {
			glTexParameteri(GL_TEXTURE_2D, key, textureParameters.get(key));
		}
		stbi_image_free(textureData);
		return texture;
	}

	/**
	 * Set a GL texture parameter.
	 *
	 * @param key the parameter's key, e.g. GL_TEXTURE_MIN_FILTER
	 * @param val the parameter's value, e.g. GL_LINEAR
	 */
	public void setTextureParameter(int key, int val) {
		textureParameters.put(key, val);
	}

}
