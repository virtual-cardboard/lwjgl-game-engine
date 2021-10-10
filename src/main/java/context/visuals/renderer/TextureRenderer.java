package context.visuals.renderer;

import static context.visuals.defaultvao.RectangleVertexArrayObject.rectangleVAO;

import java.util.Objects;

import common.math.Matrix4f;
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
	public TextureRenderer(ShaderProgram textureShaderProgram) {
		Objects.requireNonNull(textureShaderProgram);
		shaderProgram = textureShaderProgram;
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
		rectangleVAO().display();
	}

}
