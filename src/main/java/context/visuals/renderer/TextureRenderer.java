package context.visuals.renderer;

import common.math.Matrix4f;
import context.GLContext;
import context.GameContext;
import context.ResourcePack;
import context.visuals.builtin.RectangleVertexArrayObject;
import context.visuals.builtin.TextureShaderProgram;
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
	private RectangleVertexArrayObject vao;

	/**
	 * Creates a <code>TextureRenderer</code> using the texture shader program from
	 * a {@link GameContext}'s {@link ResourcePack}. The texture shader program
	 * should be put into the <code>ResourcePack</code> with the name
	 * <code>"texture"</code>.
	 * 
	 * @param context the <code>GameContext</code>
	 */
	public TextureRenderer(TextureShaderProgram shaderProgram, RectangleVertexArrayObject vao) {
		this.shaderProgram = shaderProgram;
		this.vao = vao;
	}

	/**
	 * Renders a texture using a transformation matrix.
	 * 
	 * @param glContext the {@link GLContext}
	 * @param vao       the {@link VertexArrayObject} to use
	 * @param texture   the texture to render
	 * @param matrix4f  the transformation matrix
	 */
	public void render(GLContext glContext, Texture texture, Matrix4f matrix4f) {
		shaderProgram.bind();
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", texture.getTextureUnit());
		texture.bind(glContext);
		vao.draw(glContext);
	}

}
