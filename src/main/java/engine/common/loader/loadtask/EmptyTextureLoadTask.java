package engine.common.loader.loadtask;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
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
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.HashMap;
import java.util.Map;

import context.GLContext;
import context.visuals.lwjgl.Texture;
import engine.common.loader.GLLoadTask;

public class EmptyTextureLoadTask implements GLLoadTask<Texture> {

	private int width;
	private int height;

	private Map<Integer, Integer> textureParameters = new HashMap<>();

	public EmptyTextureLoadTask(int width, int height) {
		setTextureParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		setTextureParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		setTextureParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		setTextureParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		this.width = width;
		this.height = height;
	}

	@Override
	public Texture loadGL(GLContext glContext) {
		Texture texture = new Texture();
		texture.genID();
		texture.bind(glContext);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, NULL);
		glGenerateMipmap(GL_TEXTURE_2D);
		for (Integer key : textureParameters.keySet()) {
			glTexParameteri(GL_TEXTURE_2D, key, textureParameters.get(key));
		}
		texture.setWidth(width);
		texture.setHeight(height);
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
