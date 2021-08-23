package context.input;

import java.util.function.Function;

import common.event.GameEvent;
import context.GameContext;
import context.input.event.FrameResizedInputEvent;
import context.visuals.gui.RootGui;

class RootGuiUpdaterFunction implements Function<FrameResizedInputEvent, GameEvent> {

	private GameContext context;

	public RootGuiUpdaterFunction(GameContext context) {
		this.context = context;
	}

	@Override
	public GameEvent apply(FrameResizedInputEvent inputEvent) {
		RootGui rootGui = context.getVisuals().getRootGui();
		rootGui.setDimensions(inputEvent.getWidth(), inputEvent.getHeight());
		return null;
	}

}