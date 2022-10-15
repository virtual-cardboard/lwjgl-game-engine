package context.visuals.renderer;

import context.GLContext;
import context.visuals.builtin.EntityShaderProgram;

/**
 * A {@link GameRenderer} that renders entities.
 *
 * @author Jay
 */
public class EntityRenderer extends GameRenderer {

	private final EntityShaderProgram shaderProgram;

	public EntityRenderer(EntityShaderProgram shaderProgram, GLContext glContext) {
		super(glContext);
		this.shaderProgram = shaderProgram;
	}

}
