package context.input.eventhandler.guimousehandler;

import java.util.List;

import context.data.GameData;
import context.data.gui.Gui;
import context.input.event.MouseReleasedInputEvent;
import context.input.eventhandler.MouseReleasedInputEventHandler;

public class GUIMouseReleasedInputEventHandler implements MouseReleasedInputEventHandler {

	private GameData data;

	public GUIMouseReleasedInputEventHandler(GameData data) {
		super();
		this.data = data;
	}

	@Override
	public boolean handle(MouseReleasedInputEvent event) {
		boolean consumed = handleHelper(event, data.getMainGUI());
		data.getMainGUI().depressAll();
		return consumed;
	}

	boolean handleHelper(MouseReleasedInputEvent event, Gui gui) {
		List<Gui> children = gui.getChildren();
		for (int i = gui.getNumChildren() - 1; i >= 0; i--) {
			if (handleHelper(event, children.get(i))) {
				return true;
			}
		}
		if (gui.isOn(event.getMouseX(), event.getMouseY())) {
			gui.onRelease();
			return true;
		}
		return false;
	}
}
