package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import common.loader.loadtask.TextureLoadTask;
import context.GLContext;

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
	private int textureUnit;
	private boolean linked;

	/**
	 * Creates a texture with the given texture unit. The texture unit can be used
	 * in shaders to display multiple textures with different texture units in one
	 * <code>glDrawElements</code> call.
	 * 
	 * @param textureUnit the texture unit
	 * 
	 */
	public Texture(int textureUnit) {
		if (textureUnit < 0 || textureUnit > 47) {
			throw new IllegalArgumentException("Invalid texture unit: " + textureUnit);
		}
		this.textureUnit = textureUnit;
	}

	/**
	 * Marks the texture as having been loaded into OpenGL.
	 */
	public void link() {
		linked = true;
	}

	/**
	 * Binds the texture.
	 */
	@Override
	public void bind(GLContext glContext) {
		if (glContext.textureIDs[textureUnit] == id) return;
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

	public void setId(int id) {
		this.id = id;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTextureUnit() {
		return textureUnit;
	}

	public void setTextureUnit(int textureUnit) {
		this.textureUnit = textureUnit;
	}

	public boolean isLinked() {
		return linked;
	}

}
