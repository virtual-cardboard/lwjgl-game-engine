package context.visuals.gui.renderer;

import java.util.List;

import context.GLContext;
import context.data.GameData;
import context.visuals.gui.Gui;
import context.visuals.gui.RootGui;
import context.visuals.renderer.GameRenderer;

public final class RootGuiRenderer extends GameRenderer {

	public void render(GLContext glContext, GameData data, RootGui root) {
		List<Gui> children = root.getChildren();
		for (int i = 0; i < children.size(); i++) {
			Gui child = children.get(i);
			if (child.isEnabled()) {
				recursiveRender(child, root, data, 0, 0, root.widthPx(), root.heightPx());
			}
		}
	}

	private void recursiveRender(Gui gui, RootGui root, GameData data, float parentX, float parentY, float parentWidth, float parentHeight) {
		float x = gui.posX().get(parentX, parentX + parentWidth);
		float y = gui.posY().get(parentY, parentY + parentHeight);
		float w = gui.width().get(parentX, parentX + parentWidth);
		float h = gui.height().get(parentY, parentY + parentHeight);
		gui.render(glContext, data, x, y, w, h);

		List<Gui> children = gui.getChildren();
		for (int i = 0; i < children.size(); i++) {
			Gui child = children.get(i);
			if (child.isEnabled()) {
				recursiveRender(child, root, data, x, y, w, h);
			}
		}
	}

}