package context.input.eventhandler.guimousehandler;

import context.input.event.MousePressedInputEvent;
import context.input.eventhandler.MousePressedInputEventHandler;
import context.visuals.gui.RootGui;

public class GUIMousePressedInputEventHandler implements MousePressedInputEventHandler {

	private RootGui root;

	public GUIMousePressedInputEventHandler(RootGui root) {
		super();
		this.root = root;
	}

	@Override
	public boolean handle(MousePressedInputEvent event) {
		return true;
	}

}
