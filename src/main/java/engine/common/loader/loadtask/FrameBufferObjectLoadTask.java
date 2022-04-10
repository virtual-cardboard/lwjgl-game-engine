package engine.common.loader.loadtask;

import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;

import context.GLContext;
import context.visuals.lwjgl.FrameBufferObject;
import context.visuals.lwjgl.RenderBufferObject;
import context.visuals.lwjgl.Texture;
import engine.common.loader.GLContainerObjectLoadTask;

public class FrameBufferObjectLoadTask extends GLContainerObjectLoadTask<FrameBufferObject> {

	private Texture texture;
	private RenderBufferObject rbo;

	public FrameBufferObjectLoadTask(Texture texture, RenderBufferObject rbo) {
		this.texture = texture;
		this.rbo = rbo;
	}

	@Override
	public FrameBufferObject loadGL(GLContext glContext) {
		FrameBufferObject fbo = new FrameBufferObject();
		fbo.genID();
		fbo.bind(glContext);
		if (texture != null) {
			fbo.attachTexture(texture);
		}
		if (rbo != null) {
			fbo.attachRenderBufferObject(rbo);
		}
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			throw new RuntimeException("FBO failed to initialize properly.");
		}
		FrameBufferObject.unbind(glContext);
		return fbo;
	}

}
