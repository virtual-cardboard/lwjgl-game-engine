package bundle.input.event;

import common.source.GameSource;

public class FrameEvent extends AbstractGameInputEvent {

	public FrameEvent(long time, GameSource source) {
		super(time, source);
	}

	@Override
	public String getName() {
		return null;
	}

}
