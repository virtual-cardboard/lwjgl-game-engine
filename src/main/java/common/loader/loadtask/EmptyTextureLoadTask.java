package common.loader.loadtask;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.system.MemoryUtil.NULL;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.Texture;

public class EmptyTextureLoadTask extends GLLoadTask<Texture> {

	private int width;
	private int height;

	public EmptyTextureLoadTask(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	protected Texture loadGL(GLContext glContext) {
		Texture texture = new Texture();
		texture.genID();
		texture.bind(glContext);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, NULL);
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		texture.setWidth(width);
		texture.setHeight(height);
		return texture;
	}

}
