package bundle.input.eventhandler.guimousehandler;

import bundle.data.AbstractGameData;
import bundle.data.gui.AbstractGUI;
import bundle.input.event.MouseReleasedInputEvent;
import bundle.input.eventhandler.MouseReleasedInputEventHandler;

public class GUIMouseReleasedInputEventHandler implements MouseReleasedInputEventHandler {

	private AbstractGameData data;

	public GUIMouseReleasedInputEventHandler(AbstractGameData data) {
		super();
		this.data = data;
	}

	@Override
	public boolean handle(MouseReleasedInputEvent event) {
		return handleHelper(event, data.getMainGUI());
	}

	boolean handleHelper(MouseReleasedInputEvent event, AbstractGUI gui) {
		if (gui.isOn(event.getMouseX(), event.getMouseY())) {
			if (!gui.isPressed()) {
				data.getMainGUI().depressAll();
				gui.onRelease();
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
