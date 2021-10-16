package context.visuals.renderer;

import common.math.Matrix4f;
import context.GameContextWrapper;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.Texture;
import context.visuals.lwjgl.VertexArrayObject;

/**
 * A {@link GameRenderer} that renders textures.
 * 
 * @author Jay
 *
 */
public class TextureRenderer extends GameRenderer {

	/**
	 * The {@link ShaderProgram} to use when rendering textures.
	 */
	private ShaderProgram shaderProgram;

	/**
	 * Creates a TextureRenderer with an image {@link ShaderProgram} and a
	 * rectangular {@link VertexArrayObject}.
	 * 
	 * @param textureShaderProgram the texture shader program
	 */
	public TextureRenderer(GameContextWrapper wrapper) {
		super(wrapper);
		shaderProgram = resourcePack().getShaderProgram("texture");
	}

	/**
	 * Renders a texture using a transformation matrix.
	 * 
	 * @param vao      the {@link VertexArrayObject} to use
	 * @param texture  the texture to render
	 * @param matrix4f the transformation matrix
	 */
	public void render(Texture texture, Matrix4f matrix4f) {
		shaderProgram.bind();
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", texture.getTextureUnit());
		texture.bind();
		resourcePack().rectangleVAO().display();
	}

}
