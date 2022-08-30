package context.visuals.renderer;

import context.visuals.lwjgl.Model;
import context.visuals.lwjgl.ShaderProgram;
import engine.common.math.Matrix4f;

public class ModelRenderer extends GameRenderer {

	private final ShaderProgram shaderProgram;

	public ModelRenderer(ShaderProgram shaderProgram) {
		this.shaderProgram = shaderProgram;
	}

	public void render(Model model, Matrix4f transformation) {
		shaderProgram.bind(glContext);
		shaderProgram.setMat4("transformation", transformation);
		model.meshes().forEach(m -> m.render(glContext));
		ShaderProgram.unbind();
	}

}
