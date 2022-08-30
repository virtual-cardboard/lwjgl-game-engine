package context.visuals.lwjgl;

import java.util.List;

public class Model {

	private final List<Mesh> meshes;
	private final List<Material> materials;

	public Model(List<Mesh> meshes, List<Material> materials) {
		this.meshes = meshes;
		this.materials = materials;
	}

	public List<Mesh> meshes() {
		return meshes;
	}

	public List<Material> materials() {
		return materials;
	}

}
