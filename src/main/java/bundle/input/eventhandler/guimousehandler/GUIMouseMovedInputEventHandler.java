package bundle.input.eventhandler.guimousehandler;

import bundle.data.AbstractGameData;
import bundle.data.gui.AbstractGUI;
import bundle.input.event.MouseMovedInputEvent;
import bundle.input.eventhandler.MouseMovedInputEventHandler;

public class GUIMouseMovedInputEventHandler implements MouseMovedInputEventHandler {

	private AbstractGameData data;

	public GUIMouseMovedInputEventHandler(AbstractGameData data) {
		super();
		this.data = data;
	}

	@Override
	public boolean handle(MouseMovedInputEvent event) {
		return handleHelper(event, data.getMainGUI());
	}

	boolean handleHelper(MouseMovedInputEvent event, AbstractGUI gui) {
		if (gui.isOn(event.getMouseX(), event.getMouseY())) {
			if (!gui.isHovered()) {
				data.getMainGUI().dehoverAll();
				gui.onHover();
			}
			return true;
		}
		for (AbstractGUI child : gui.getChildren()) {
			if (handleHelper(event, child)) {
				return true;
			}
		}
		return false;
	}

}
