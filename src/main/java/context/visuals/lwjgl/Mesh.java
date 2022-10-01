package context.visuals.lwjgl;

import context.GLContext;

/**
 * Contains a {@link VertexArrayObject} and a {@link Material}.
 */
public class Mesh {

	private final VertexArrayObject vao;
	private final Material material;

	public Mesh(VertexArrayObject vao, Material material) {
		this.vao = vao;
		this.material = material;
	}

	public VertexArrayObject vao() {
		return vao;
	}

	public Material material() {
		return material;
	}

	public void render(GLContext glContext) {
		material.shaderProgram().bind(glContext);
		// TODO set uniforms for diffuse texture, etc.
		vao.draw(glContext);
	}

}
