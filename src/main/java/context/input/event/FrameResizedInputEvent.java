package context.input.event;

import state.source.GameSource;
import state.user.LocalUser;

public final class FrameResizedInputEvent extends GameInputEvent {

	private final float width;
	private final float height;

	public FrameResizedInputEvent(long time, GameSource source, float width, float height) {
		super(time, source);
		this.width = width;
		this.height = height;
	}

	public FrameResizedInputEvent(float width, float height) {
		super(LocalUser.LOCAL_USER);
		this.width = width;
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

}
