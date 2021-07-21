package common.loader.linktask;

import context.visuals.renderer.shader.VertexShader;

public class GenerateVertexShaderLinkTask extends LinkTask {

	private VertexShader vertexShader;
	private String sourceLocation;

	public GenerateVertexShaderLinkTask(VertexShader vertexShader, String sourceLocation) {
		this.vertexShader = vertexShader;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void doRun() {
//		vertexShader.
	}

}
