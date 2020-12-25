package bundle.input.eventhandler.guimousehandler;

import java.util.List;

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
		boolean consumed = handleHelper(event, data.getMainGUI());
		data.getMainGUI().depressAll();
		return consumed;
	}

	boolean handleHelper(MouseReleasedInputEvent event, AbstractGUI gui) {
		List<AbstractGUI> children = gui.getChildren();
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
