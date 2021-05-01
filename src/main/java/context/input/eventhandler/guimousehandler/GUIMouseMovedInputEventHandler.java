package context.input.eventhandler.guimousehandler;

import java.util.List;

import context.data.GameData;
import context.data.gui.GUI;
import context.input.event.MouseMovedInputEvent;
import context.input.eventhandler.MouseMovedInputEventHandler;

public class GUIMouseMovedInputEventHandler implements MouseMovedInputEventHandler {

	private GameData data;

	public GUIMouseMovedInputEventHandler(GameData data) {
		super();
		this.data = data;
	}

	@Override
	public boolean handle(MouseMovedInputEvent event) {
		return handleHelper(event, data.getMainGUI());
	}

	boolean handleHelper(MouseMovedInputEvent event, GUI gui) {
		List<GUI> children = gui.getChildren();
		for (int i = gui.getNumChildren() - 1; i >= 0; i--) {
			if (handleHelper(event, children.get(i))) {
				return true;
			}
		}
		if (gui.isOn(event.getMouseX(), event.getMouseY())) {
			if (!gui.isHovered()) {
				data.getMainGUI().dehoverAll();
				gui.onHover();
			}
			return true;
		} else if (gui.isHovered()) {
			gui.onDehover();
		}
		return false;
	}
}
