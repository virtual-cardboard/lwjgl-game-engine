package context.visuals.lwjgl;

public final class ScreenFrameBufferObject extends FrameBufferObject {

	public ScreenFrameBufferObject() {
		verifyInitialized();
	}

	@Override
	public void genID() {
		throw new IllegalStateException("ScreenFrameBufferObject already initialized.");
	}

	@Override
	public void attachTexture(Texture texture) {
		throw new IllegalStateException("Cannot attach texture to ScreenFrameBufferObject.");
	}

	@Override
	public void attachTexture(Texture texture, int attachmentType) {
		throw new IllegalStateException("Cannot attach texture to ScreenFrameBufferObject.");
	}

	@Override
	public void attachRenderBufferObject(RenderBufferObject rbo) {
		throw new IllegalStateException("Cannot attach RenderBufferObject to ScreenFrameBufferObject.");
	}

}
