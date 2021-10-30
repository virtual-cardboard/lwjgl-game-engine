package context.visuals.builtin;

import static context.visuals.lwjgl.ShaderType.FRAGMENT;

import context.visuals.lwjgl.Shader;

public final class ColourFragmentShader extends Shader {

	private static final String COLOUR_FRAGMENT_SHADER_SOURCE = "#version 330 core\r\n"
			+ "out vec4 fragColor;"
			+ "uniform vec4 fill;"
			+ "void main() { fragColor = fill; }";

	private ColourFragmentShader() {
		super(FRAGMENT);
	}

	public static ColourFragmentShader createColourFragmentShader() {
		ColourFragmentShader shader = new ColourFragmentShader();
		shader.genId();
		shader.compile(COLOUR_FRAGMENT_SHADER_SOURCE);
		return shader;
	}

}
