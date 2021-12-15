package common.loader.loadtask;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.Texture;

public class EmptyTextureLoadTask extends GLLoadTask<Texture> {

	private int textureUnit;
	private int width;
	private int height;

	public EmptyTextureLoadTask(int textureUnit, int width, int height) {
		this.textureUnit = textureUnit;
		this.width = width;
		this.height = height;
	}

	@Override
	protected Texture loadGL(GLContext glContext) {
		Texture texture = new Texture(textureUnit);
		texture.genID();
		texture.bind(glContext);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, NULL);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		return texture;
	}

}
