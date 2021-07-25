package context.visuals.renderer;

import java.util.Objects;

import common.math.Matrix4f;
import context.visuals.lwjgl.Texture;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.renderer.shader.ShaderProgram;

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
	 * The {@link VertexArrayObject} to use when rendering textures.
	 */
	private VertexArrayObject vao;

	/**
	 * Creates a TextureRenderer with an image {@link ShaderProgram} and a
	 * rectangular {@link VertexArrayObject}.
	 * 
	 * @param textureShaderProgram the texture shader program
	 * @param rectangleVao         the rectangle vertex array object
	 */
	public TextureRenderer(ShaderProgram textureShaderProgram, VertexArrayObject rectangleVao) {
		Objects.requireNonNull(textureShaderProgram);
		shaderProgram = textureShaderProgram;
		this.vao = rectangleVao;
	}

	/**
	 * Renders a texture using a transformation matrix.
	 * 
	 * @param texture  the texture to render
	 * @param tranform the transformation matrix
	 */
	public void render(Texture texture, Matrix4f tranform) {
		shaderProgram.bind();
		shaderProgram.setMat4("transform", tranform);
		shaderProgram.setInt("textureSampler", texture.getTextureUnit());
		texture.bind();
		vao.display();
	}

}
