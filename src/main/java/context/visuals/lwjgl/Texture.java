package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import common.loader.loadtask.TextureLoadTask;

/**
 * An OpenGL object that represents a 2D image. Texture are made using
 * {@link TextureBuilder}.
 * 
 * @author Jay
 *
 */
public class Texture {

	private int id;
	private int width, height;
	private int textureUnit;
	private String imagePath;
	private boolean linked;

	/**
	 * Creates a texture with the given texture unit and image path. The texture
	 * unit can be used in shaders to display multiple textures with different
	 * texture units in one <code>glDrawElements</code> call.
	 * {@link TextureLoadTask} uses the image path to load data from the image.
	 * 
	 * @param textureUnit the texture unit
	 * @param imagePath   the path to the image used in this texture
	 */
	public Texture(int textureUnit, String imagePath) {
		if (textureUnit < 0 || textureUnit > 31) {
			throw new IllegalArgumentException("Invalid texture unit: " + textureUnit);
		}
		this.textureUnit = textureUnit;
		this.imagePath = imagePath;
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
	public void bind() {
		glActiveTexture(GL_TEXTURE0 + textureUnit);
		glBindTexture(GL_TEXTURE_2D, id);
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

	public String getImagePath() {
		return imagePath;
	}

	public boolean isLinked() {
		return linked;
	}

}
