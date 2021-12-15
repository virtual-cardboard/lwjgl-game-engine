package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.*;

import context.GLContext;

public class FrameBufferObject extends GLContainerObject {

	private int id;

	public void genID() {
		this.id = glGenFramebuffers();
		confirmInitialization();
	}

	public void attachTexture(Texture texture) {
		verifyInitialized();
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture.id(), 0);
	}

	public void attachTexture(Texture texture, int attachmentType) {
		verifyInitialized();
		glFramebufferTexture2D(GL_FRAMEBUFFER, attachmentType, GL_TEXTURE_2D, texture.id(), 0);
	}

	public void attachRenderBufferObject(RenderBufferObject rbo) {
		verifyInitialized();
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, rbo.formatType(), GL_RENDERBUFFER, rbo.id());
	}

	public void bind(GLContext glContext) {
		verifyInitialized();
		if (glContext.framebufferID == id) {
			return;
		}
		glBindFramebuffer(GL_FRAMEBUFFER, id);
		glContext.framebufferID = id;
	}

	public static void unbind(GLContext glContext) {
		if (glContext.framebufferID == 0) {
			return;
		}
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glContext.framebufferID = 0;
	}

	public void delete() {
		verifyInitialized();
		glDeleteFramebuffers(id);
	}

}
