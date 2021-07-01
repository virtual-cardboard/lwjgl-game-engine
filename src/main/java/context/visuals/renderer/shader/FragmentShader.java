package context.visuals.renderer.shader;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;;

public class FragmentShader extends Shader {

	public FragmentShader(String sourceLocation) {
		super(sourceLocation, GL_FRAGMENT_SHADER);
	}

}
