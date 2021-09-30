package context.visuals.lwjgl;

public class VertexArrayObjectBuilder {

	private ElementBufferObject ebo;
	private VertexBufferObject[] vbos;

	public VertexArrayObjectBuilder(ElementBufferObject ebo, VertexBufferObject... vbos) {
		this.ebo = ebo;
		this.vbos = vbos;
	}

	public VertexArrayObject build() {
		VertexArrayObject vao = new VertexArrayObject();
		vao.generateId();
		vao.bind();
		ebo.generateId();
		ebo.loadData();
		vao.setEbo(ebo);
		for (int i = 0, l = vbos.length; i < l; i++) {
			VertexBufferObject vbo = vbos[i];
			vbo.generateId();
			vbo.loadData();
			vao.attachVBO(vbo);
		}
		vao.enableVertexAttribPointers();
		return vao;
	}

}
