package bundle.input.eventhandler.guimousehandler;

import bundle.data.AbstractGameData;
import bundle.data.gui.AbstractGUI;
import bundle.input.event.MousePressedInputEvent;
import bundle.input.eventhandler.MousePressedInputEventHandler;

public class GUIMousePressedInputEventHandler implements MousePressedInputEventHandler {

	private AbstractGameData data;

	public GUIMousePressedInputEventHandler(AbstractGameData data) {
		super();
		this.data = data;
	}

	@Override
	public boolean handle(MousePressedInputEvent event) {
		return handleHelper(event, data.getMainGUI());
	}

	boolean handleHelper(MousePressedInputEvent event, AbstractGUI gui) {
		if (gui.isOn(event.getMouseX(), event.getMouseY())) {
			if (!gui.isPressed()) {
				data.getMainGUI().releaseAll();
				gui.onPress();
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
