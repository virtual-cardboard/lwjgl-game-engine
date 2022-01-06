package context.visuals.renderer;

import context.GLContext;
import context.visuals.GameVisuals;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.VertexArrayObject;

/**
 * A GameRenderer uses {@link ShaderProgram}s, {@link VertexArrayObject}s, and
 * other <code>GameRenderer</code>s to render objects onto the screen. Use
 * <code>GameRenderer</code>s in {@link GameVisuals}'s
 * {@link GameVisuals#render() render()} function.
 * 
 * @author Jay
 *
 */
public abstract class GameRenderer {

	protected GLContext glContext;

	public GameRenderer() {
	}

	public GameRenderer(GLContext glContext) {
		this.glContext = glContext;
	}

	public GLContext glContext() {
		return glContext;
	}

	public void setGLContext(GLContext glContext) {
		this.glContext = glContext;
	}

	protected float width() {
		return glContext.width();
	}

	protected float height() {
		return glContext.height();
	}

}
