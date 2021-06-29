package context.input.event;

import state.source.GameSource;
import state.user.LocalUser;

public final class FrameResizedInputEvent extends GameInputEvent {

	private final int width;
	private final int height;

	public FrameResizedInputEvent(long time, GameSource source, int width, int height) {
		super(time, source);
		this.width = width;
		this.height = height;
	}

	public FrameResizedInputEvent(int width, int height) {
		super(LocalUser.LOCAL_USER);
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
