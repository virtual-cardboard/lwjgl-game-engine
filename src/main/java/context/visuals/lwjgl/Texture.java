package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.system.MemoryUtil.NULL;

import common.loader.loadtask.TextureLoadTask;
import common.math.Vector2f;
import context.GLContext;
import context.ResourcePack;

/**
 * An OpenGL object that represents a 2D image. Texture are made using
 * {@link TextureLoadTask TextureLoadTasks}.
 * 
 * @author Jay
 *
 */
public class Texture extends GLRegularObject {

	private int id;
	private int width, height;

	/**
	 * Binds the texture to texture unit 0.
	 * 
	 * @param glContext the <code>GLContext</code>
	 */
	public void bind(GLContext glContext) {
		bind(glContext, 0);
	}

	/**
	 * Binds the texture to the given texture unit.
	 * 
	 * @param glContext   the <code>GLContext</code>
	 * @param textureUnit the texture unit
	 */
	public void bind(GLContext glContext, int textureUnit) {
		verifyInitialized();
		if (glContext.textureIDs[textureUnit] == id) {
			return;
		}
		glActiveTexture(GL_TEXTURE0 + textureUnit);
		glBindTexture(GL_TEXTURE_2D, id);
		glContext.textureIDs[textureUnit] = id;
	}

	/**
	 * Deletes the texture.
	 */
	public void delete() {
		glDeleteTextures(id);
	}

	public void genID() {
		this.id = glGenTextures();
		initialize();
	}

	public void resize(GLContext glContext, int width2, int height2) {
		bind(glContext);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width2, height2, 0, GL_RGBA, GL_UNSIGNED_BYTE, NULL);
		glGenerateMipmap(GL_TEXTURE_2D);
	}

	public int width() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int height() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Vector2f dimensions() {
		return new Vector2f(width, height);
	}

	int id() {
		return id;
	}

	@Override
	public void putInto(String name, ResourcePack resourcePack) {
		resourcePack.putTexture(name, this);
	}
}
