package engine.common.loader.graph;

import context.GLContext;
import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.Texture;
import engine.common.loader.graph.loader.GLLoader1Arg;
import engine.common.loader.graph.loader.GLLoader2Arg;
import engine.common.loader.loadtask.ShaderProgramLoadTask;
import engine.common.loader.loadtask.TextureLoadTask;

public class Loaders {

	private Loaders() {
	}

	public static final GLLoader1Arg<Texture, String> TEXTURE = (GLContext glContext, String path) -> new TextureLoadTask(path).loadGL(glContext);

	public static final GLLoader2Arg<ShaderProgram, Shader, Shader> SHADER_PROGRAM =
			(glContext, vertexShader, fragmentShader) -> {
				ShaderProgram shaderProgram = new ShaderProgram();
				shaderProgram.addShader(vertexShader);
				shaderProgram.addShader(fragmentShader);
				return new ShaderProgramLoadTask(shaderProgram).loadGL(glContext);
			};

}
