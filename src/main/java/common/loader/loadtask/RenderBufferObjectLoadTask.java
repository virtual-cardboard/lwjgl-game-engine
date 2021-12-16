package common.loader.loadtask;

import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.RenderBufferObject;

public class RenderBufferObjectLoadTask extends GLLoadTask<RenderBufferObject> {

	private int width;
	private int height;
	private RenderBufferObject rbo;

	public RenderBufferObjectLoadTask(int format, int formatType, int width, int height) {
		this(new RenderBufferObject(format, formatType), width, height);
	}

	public RenderBufferObjectLoadTask(RenderBufferObject rbo, int width, int height) {
		this.rbo = rbo;
		this.width = width;
		this.height = height;
	}

	@Override
	protected RenderBufferObject loadGL(GLContext glContext) {
		rbo.genID();
		rbo.bind(glContext);
		glRenderbufferStorage(GL_RENDERBUFFER, rbo.format(), width, height);
		RenderBufferObject.unbind(glContext);
		return rbo;
	}

}