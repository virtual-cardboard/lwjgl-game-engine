package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

import context.GLContext;
import context.ResourcePack;

public class FrameBufferObject extends GLContainerObject {

	private int id;
	private Texture texture;
	private RenderBufferObject rbo;

	public void genID() {
		this.id = glGenFramebuffers();
		initialize();
	}

	public void attachTexture(Texture texture) {
		verifyInitialized();
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture.id(), 0);
		this.texture = texture;
	}

	public void attachTexture(Texture texture, int attachmentType) {
		verifyInitialized();
		glFramebufferTexture2D(GL_FRAMEBUFFER, attachmentType, GL_TEXTURE_2D, texture.id(), 0);
		this.texture = texture;
	}

	public void attachRenderBufferObject(RenderBufferObject rbo) {
		verifyInitialized();
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, rbo.formatType(), GL_RENDERBUFFER, rbo.id());
		this.rbo = rbo;
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

	public Texture texture() {
		return texture;
	}

	public RenderBufferObject renderBufferObject() {
		return rbo;
	}

	@Override
	public void putInto(String name, ResourcePack resourcePack) {
		resourcePack.putFBO(name, this);
	}

}
