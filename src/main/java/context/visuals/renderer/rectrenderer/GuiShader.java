package context.visuals.renderer.rectrenderer;

import common.math.Matrix4f;
import context.visuals.renderer.ShaderProgram;

public class GuiShader extends ShaderProgram {

	private static final String VERTEX_FILE;
	private static final String FRAGMENT_FILE;

	static {
		String guiShaderPath = GuiShader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		VERTEX_FILE = guiShaderPath + "shaders/guiVertexShader.txt";
		FRAGMENT_FILE = guiShaderPath + "shaders/guiFragmentShader.txt";
	}

	private int location_modelMatrix;

	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		getAllUniformLocations();
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelMatrix = super.getUniformLocation("modelMatrix");
	}

	public void loadModelMatrix(Matrix4f matrix) {
		super.loadMatrix(location_modelMatrix, matrix);
	}
}
