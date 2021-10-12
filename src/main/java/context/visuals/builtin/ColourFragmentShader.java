package context.visuals.builtin;

import static context.visuals.lwjgl.ShaderType.FRAGMENT;

import context.visuals.lwjgl.Shader;

public final class ColourFragmentShader extends Shader {

	private static final String COLOUR_FRAGMENT_SHADER_SOURCE = "#version 330 core\r\n"
			+ "out vec4 fragColor;\r\n"
			+ "uniform vec4 colour;\r\n"
			+ "void main() { fragColor = colour; }";

	private ColourFragmentShader() {
		super(FRAGMENT);
	}

	public static ColourFragmentShader createColourFragmentShader() {
		ColourFragmentShader shader = new ColourFragmentShader();
		shader.setId(FRAGMENT.genId());
		shader.compile(COLOUR_FRAGMENT_SHADER_SOURCE);
		return shader;
	}

}
