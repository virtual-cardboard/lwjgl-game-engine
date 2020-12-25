package bundle.input.eventhandler.guimousehandler;

import java.util.List;

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
		List<AbstractGUI> children = gui.getChildren();
		for (int i = gui.getNumChildren() - 1; i >= 0; i--) {
			if (handleHelper(event, children.get(i))) {
				return true;
			}
		}
		if (gui.isOn(event.getMouseX(), event.getMouseY())) {
			if (!gui.isPressed()) {
				data.getMainGUI().depressAll();
				gui.onPress();
			}
			return true;
		}
		return false;
	}

}
