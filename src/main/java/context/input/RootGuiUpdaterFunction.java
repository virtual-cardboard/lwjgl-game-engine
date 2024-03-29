package context.input;

import java.util.function.Function;

import context.GameContext;
import context.input.event.FrameResizedInputEvent;
import context.visuals.gui.RootGui;
import engine.common.event.GameEvent;

class RootGuiUpdaterFunction implements Function<FrameResizedInputEvent, GameEvent> {

	private GameContext context;

	public RootGuiUpdaterFunction(GameContext context) {
		this.context = context;
	}

	@Override
	public GameEvent apply(FrameResizedInputEvent inputEvent) {
		RootGui rootGui = context.visuals().rootGui();
		rootGui.setDimensions(inputEvent.width(), inputEvent.height());
		return null;
	}

}
