package context.input.eventhandler.guimousehandler;

import context.data.GameData;
import context.input.event.MousePressedInputEvent;
import context.input.eventhandler.MousePressedInputEventHandler;

public class GUIMousePressedInputEventHandler implements MousePressedInputEventHandler {

	private GameData data;

	public GUIMousePressedInputEventHandler(GameData data) {
		super();
		this.data = data;
	}

	@Override
	public boolean handle(MousePressedInputEvent event) {
//		return handleHelper(event, data.getMainGUI());
		return true;
	}

//	boolean handleHelper(MousePressedInputEvent event, Gui gui) {
//		List<Gui> children = gui.getChildren();
//		for (int i = gui.getNumChildren() - 1; i >= 0; i--) {
//			if (handleHelper(event, children.get(i))) {
//				return true;
//			}
//		}
//		if (gui.isOn(event.getMouseX(), event.getMouseY())) {
//			if (!gui.isPressed()) {
//				data.getMainGUI().depressAll();
//				gui.onPress();
//			}
//			return true;
//		}
//		return false;
//	}

}
