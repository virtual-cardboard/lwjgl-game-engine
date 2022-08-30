package context.visuals.lwjgl;

import context.GLContext;

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
		// TODO: Use the material
		vao.draw(glContext);
	}

}
